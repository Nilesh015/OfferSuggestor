package basics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@RestController
public class OfferController {

    private static final ConcurrentHashMap<String, ArrayList<ArrayList<String>>> merchantCache = new ConcurrentHashMap<>();
    private ThreadPoolExecutor executorService;

    @Autowired
    private OffersDataApiCall offersDataApiCall;

    @Autowired
    private MerchantSearchCall merchantSearchCall;

    @Autowired
    private MerchantLocatorCall merchantLocatorCall;

    @Autowired
    private MerchantMeasurementCall merchantMeasurementCall;

    @CrossOrigin
    @PostMapping("/filter")
    public List<OfferItem> filterOffers(@RequestBody OfferFilterDTO offerFilterDTO) {
        OfferSuggestorResponse response = offersDataApiCall.getRetrieveOffersByFilterHandler(offerFilterDTO);
        final List<Integer> offerIDs = offerFilterDTO.getOfferIDs();
        System.out.println(response);
        return response.getOfferList()
                .stream()
                .filter(offerItem -> offerIDs.contains(Integer.parseInt(offerItem.getOfferId())))
                .collect(Collectors.toList());
    }

    @CrossOrigin
    @GetMapping("/search")
    public OfferSuggestorResponse findNearbyMerchants(@RequestParam("storeID") String storeID) throws IOException {
        //String storeID = "165833808";

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("merchant_search");
        ArrayList<String> attributes  = merchantSearchCall.postMerchantSearchHandler(storeID);
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskInfo());

        String postalCode = (attributes.get(0));
        if(postalCode.indexOf('-') != -1)
        postalCode = postalCode.substring(0, postalCode.indexOf('-'));
        String mCC = attributes.get(1);
        String city = attributes.get(2);

        //System.out.println("Merchant Details: ");
        //System.out.println("PostalCode: " + postalCode + ", " + "Category Code: " + mCC + ", " + "City: " + city);
        stopWatch.start("postal_codes_merchant_ids");
        ArrayList<ArrayList<String>> PostalCodesMerchantIDs = getMerchantIDAndPostalCodeList(postalCode,mCC);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

        ArrayList<String> uniquePostalCodes = new ArrayList<>();
        for(int i = 0;i < PostalCodesMerchantIDs.get(0).size();i++){
            if(!uniquePostalCodes.contains(PostalCodesMerchantIDs.get(0).get(i)))
                uniquePostalCodes.add(PostalCodesMerchantIDs.get(0).get(i));
        }
//        ArrayList<Double> MerchantPercentages = callMerchantMeasurement(postalCode,mCC,uniquePostalCodes,city);
        ArrayList<Double> MerchantPercentages = getRandomMeasurement();
        int key = 1;
        stopWatch.start("offer_suggestor_response");
        OfferSuggestorResponse bestOfferParameters = offersDataApiCall.getBestOfferParameters(key, PostalCodesMerchantIDs, MerchantPercentages, postalCode, city, mCC);
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskInfo());
        System.out.println(stopWatch.prettyPrint());
        return bestOfferParameters;
    }

    @CrossOrigin
    @GetMapping("/customer_search")
    public OfferSuggestorResponse GetBestParametersForCustomer(@RequestParam("postalCode") String postalCode, @RequestParam("mCC") String mCC) throws IOException{
        //String postalCode = "94102";
        //String mCC = "5814";
        String city = "";
        ArrayList<ArrayList<String>> PostalCodesMerchantIDs = getMerchantIDAndPostalCodeList(postalCode,mCC);
        city = PostalCodesMerchantIDs.get(2).get(0);
        //System.out.println(city);
        //System.out.println(PostalCodesMerchantIDs);
        ArrayList<String> uniquePostalCodes = new ArrayList<String>();
        for(int i = 0;i < PostalCodesMerchantIDs.get(0).size();i++){
            if(!uniquePostalCodes.contains(PostalCodesMerchantIDs.get(0).get(i)))
                uniquePostalCodes.add(PostalCodesMerchantIDs.get(0).get(i));
        }
        //ArrayList<Double> MerchantPercentages = callMerchantMeasurement(postalCode,mCC,uniquePostalCodes,city);
        ArrayList<Double> MerchantPercentages = getRandomMeasurement();
        int key = 2;
        return offersDataApiCall.getBestOfferParameters(key,PostalCodesMerchantIDs,MerchantPercentages,postalCode,city,mCC);

    }
    public ArrayList<ArrayList<String>> getMerchantIDAndPostalCodeList(String postalCode,String mCC) {
        String cacheKey = postalCode + "_" + mCC;
        if(merchantCache.containsKey(cacheKey)){
            return merchantCache.get(cacheKey);
        }
        ArrayList<ArrayList<String>> PostalCodesMerchantIDs = new ArrayList<>();
        ArrayList<String> PostalCodes = new ArrayList<>();
        ArrayList<String> MerchantIDs = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();

        Map<Integer, Future<ArrayList<ArrayList<String>>>> futureMap = new TreeMap<>();
        for(int i = 1;i < 8;i++) {
            int count = i;

            Future<ArrayList<ArrayList<String>>> future = getExecutorService().submit(() -> merchantLocatorCall.postMerchantLocatorHandler(count, postalCode, mCC));
            futureMap.put(count, future);
        }

        futureMap.forEach((integer, arrayListFuture) -> {
            try {
                for (ArrayList<String> merchant : arrayListFuture.get()) {
                    String s = merchant.get(1);
                    String s1 = merchant.get(0);
                    String s2 = merchant.get(2);
                    cities.add(s2);
                    MerchantIDs.add(s1);
                    int k = s.indexOf('-');
                    if(k != -1)
                        s = s.substring(0, k);
                    PostalCodes.add(s);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        PostalCodesMerchantIDs.add(PostalCodes);
        PostalCodesMerchantIDs.add(MerchantIDs);
        PostalCodesMerchantIDs.add(cities);

        merchantCache.put(cacheKey,PostalCodesMerchantIDs);
        return PostalCodesMerchantIDs;
    }

    public ArrayList<Double> getRandomMeasurement() {
        Random rand = new Random();
        ArrayList<Double> Percentages = new ArrayList<>();
        /*
         * Since Merchant Measurement doesn't give values for all
         * postal code city and country levels in Sandbox,
         * we assign random percentages for these
         */
        Percentages.add(-0.6);
        Percentages.add(-0.6);
        Percentages.add(-0.3);
        Percentages.add(-0.1);
        return Percentages;
    }

    public ArrayList<Double> callMerchantMeasurement(String postalCode, String mCC, ArrayList<String> PostalCodes, String city) throws IOException {
        ArrayList<Double> percentages = new ArrayList<>();
        int i;
        for(i = 1; i <= 4;i++){
            double j = merchantMeasurementCall.postMerchantBenchmarkHandler(i,postalCode,mCC,PostalCodes,city);//for postal code
            percentages.add(j);
        }
        //System.out.println(mCC);
        //System.out.println(percentages);

        return percentages;
    }

    private ThreadPoolExecutor getExecutorService(){
        if(executorService == null){
            synchronized (OfferController.class) {
                if(executorService == null) {
                    System.out.println("initialized threadpool.");
                    executorService = new ThreadPoolExecutor(8, 2147483647, 10L, TimeUnit.SECONDS, new SynchronousQueue());
                    executorService.allowCoreThreadTimeOut(true);
                }
            }
        }
        return executorService;
    }
}
