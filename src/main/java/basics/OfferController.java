package basics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

@RestController
public class OfferController {

    @Autowired
    private OffersDataApiCall offersDataApiCall;

    @Autowired
    private MerchantSearchCall merchantSearchCall;

    @CrossOrigin
    @PostMapping("/filter")
    public ArrayList<Integer> filterOffers(OfferFilterDTO offerFilterDTO) {
        return offersDataApiCall.getRetrieveOffersByFilterHandler(offerFilterDTO);
    }

    @CrossOrigin
    @GetMapping("/search")
    public OfferSuggestorResponse findNearbyMerchants(@RequestParam("storeID") String storeID) throws IOException {
        //String storeID = "165833808";

        ArrayList<String> attributes  = merchantSearchCall.postMerchantSearchHandler(storeID);

        String postalCode = (attributes.get(0)).substring(0, attributes.get(0).indexOf('-'));
        String mCC = attributes.get(1);
        String city = attributes.get(2);

        System.out.println("Merchant Details: ");
        System.out.println("PostalCode: " + postalCode + ", " + "Category Code: " + mCC + ", " + "City: " + city);
        ArrayList<ArrayList<String>> PostalCodesMerchantIDs = getMerchantIDAndPostalCodeList(postalCode,mCC);

        ArrayList<String> uniquePostalCodes = new ArrayList<>();
        for(int i = 0;i < PostalCodesMerchantIDs.get(0).size();i++){
            if(!uniquePostalCodes.contains(PostalCodesMerchantIDs.get(0).get(i)))
                uniquePostalCodes.add(PostalCodesMerchantIDs.get(0).get(i));
        }
//        ArrayList<Double> MerchantPercentages = callMerchantMeasurement(postalCode,mCC,uniquePostalCodes,city);
        ArrayList<Double> MerchantPercentages = getRandomMeasurement();

        return offersDataApiCall.getBestOfferParameters(PostalCodesMerchantIDs,MerchantPercentages,postalCode,city);
    }

    public ArrayList<ArrayList<String>> getMerchantIDAndPostalCodeList(String postalCode,String mCC) throws IOException {
        MerchantLocatorCall newCall = new MerchantLocatorCall();

        ArrayList<ArrayList<String>> merchants;
        ArrayList<ArrayList<String>> PostalCodesMerchantIDs = new ArrayList<>();
        ArrayList<String> PostalCodes = new ArrayList<>();
        ArrayList<String> MerchantIDs = new ArrayList<>();
        System.out.println(mCC);
        for(int i = 1;i < 8;i++) {
            merchants = newCall.postMerchantLocatorHandler(i,postalCode,mCC);
            System.out.println(merchants);
            for (ArrayList<String> merchant : merchants) {
                String s = merchant.get(1);
                String s1 = merchant.get(0);
                MerchantIDs.add(s1);
                int k = s.indexOf('-');
                s = s.substring(0, k);
                PostalCodes.add(s);
            }
        }
        PostalCodesMerchantIDs.add(PostalCodes);
        PostalCodesMerchantIDs.add(MerchantIDs);

        System.out.println(PostalCodesMerchantIDs);
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
        for(int i=0;i<4;i++){
            Percentages.add(-30.0 + (60.0)*rand.nextDouble());
        }
        return Percentages;
    }

    public ArrayList<Double> callMerchantMeasurement(String postalCode, String mCC, ArrayList<String> PostalCodes, String city) throws IOException {
        MerchantMeasurementCall newCall = new MerchantMeasurementCall();
        ArrayList<Double> percentages = new ArrayList<>();
        int i;
        for(i = 1; i <= 4;i++){
            double j = newCall.postMerchantBenchmarkHandler(i,postalCode,mCC,PostalCodes,city);//for postal code
            percentages.add(j);
        }
        System.out.println(mCC);
        System.out.println(percentages);

        return percentages;
    }
}
