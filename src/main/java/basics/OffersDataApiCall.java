package basics;

import com.visa.developer.sample.offers_data_api.ApiClient;
import com.visa.developer.sample.offers_data_api.api.OffersDataApiApi;
import com.visa.developer.sample.offers_data_api.model.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class OffersDataApiCall {
    private final OffersDataApiApi api;

    public OffersDataApiCall(){
        //System.out.println("\nProduct Name: Visa Merchant Offers Resource Center\nApi Name: Offers Data API");
        ApiClient apiClient = new ApiClient();
        // Configure HTTP basic authorization: basicAuth
        apiClient.setUsername("YOUR USERNAME");
        apiClient.setPassword("YOUR PASSWORD");
        apiClient.setKeystorePath("YOUR KEYSTORE PATH");
        apiClient.setKeystorePassword("YOUR KEYSTORE PASSWORD");
        apiClient.setPrivateKeyPassword("YOUR PRIVATEKEY PASSWORD");

        // To set proxy uncomment the below lines
        // apiClient.setProxyHostName("proxy.address@example.com");
        // apiClient.setProxyPortNumber(0000);
        api = new OffersDataApiApi(apiClient);
    }


    /**
     *
     *
     * Request for all offers
     *
     *          if the Api call fails
     */
    public OfferSuggestorResponse getBestOfferParameters(int keyVal, ArrayList<ArrayList<String>> PostalCodesMerchantIDs, ArrayList<Double> MerchantPercentages, String searchPCode, String searchCity) throws IOException {
        /*
        * Since we have dummy offers with dummy merchant ids,
        * we map each dummy merchant to a real merchant id obtained from Merchant Locator
        */
        HashMap<Integer,Integer> ID = new HashMap<>();
        ID.put(80310667, 101456);
        ID.put(29992901, 101457);
        ID.put(25837910, 101458);
        ID.put(14220138, 101459);
        ID.put(14786696, 101460);

        for(int i = 0; i < (PostalCodesMerchantIDs.get(0)).size();i++){
            int key = Integer.parseInt(PostalCodesMerchantIDs.get(1).get(i));
            //System.out.println(key);
            if(ID.containsKey(key)){
                PostalCodesMerchantIDs.get(1).set(i,"" + ID.get(key));
            }
        }

        ArrayList<String> merchantIDs = PostalCodesMerchantIDs.get(1);

        //System.out.println(merchantIDs);

        StringBuilder merchantIDQueryBuilder = new StringBuilder();
        for(int i = 0; i < merchantIDs.size(); i++)
        {
            if(i == merchantIDs.size() - 1)
                merchantIDQueryBuilder.append(merchantIDs.get(i));
            else
                merchantIDQueryBuilder.append(merchantIDs.get(i)).append(",");
        }
        String merchantIDQuery = merchantIDQueryBuilder.toString();

        //System.out.println(merchantIDQuery);

        //Initial Response filtered by nearby merchants only.
        RetrieveOffersByFiltergetResponse response = api.getretrieveOffersByFilter( null,  null,  null,  null,  null,  merchantIDQuery,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null);

        //Storing offerID list of nearby offers
        ArrayList<Integer> offerIdList = new ArrayList<>();

        HashMap<Integer,ArrayList<String>> mapToDesc = new HashMap<>();

        int numOffers = response.getTotalFoundResults();
        //System.out.println(numOffers);
        int offerId;
        for(int i = 0; i < numOffers;i++) {
            offerId = response.getOffers().get(i).getOfferId();

            String desc = response.getOffers().get(i).getOfferShortDescription().getText();
            String title = response.getOffers().get(i).getOfferTitle();
            String validityFrom = response.getOffers().get(i).getValidityFromDate();
            String validityTo = response.getOffers().get(i).getValidityToDate();

            ArrayList<String> offerDetails = new ArrayList<>();
            offerDetails.add(title);
            offerDetails.add(desc);
            offerDetails.add(validityFrom);
            offerDetails.add(validityTo);

            offerIdList.add(offerId);
            mapToDesc.put(offerId,offerDetails);
        }

        //Passing this offer id list for next call, to get list of all merchants, near and far, and their counts

        StringBuilder offerQuery = new StringBuilder();
        for(int i = 0; i < offerIdList.size(); i++)
        {
            if(i == offerIdList.size() - 1)
                offerQuery.append(offerIdList.get(i));
            else
                offerQuery.append(offerIdList.get(i)).append(",");
        }
        //System.out.println(offerQuery);
        //For getting counts involving all merchants
        RetrieveOffersByOfferIdgetResponse offerBasedResponse = api.getretrieveOffersByOfferId(offerQuery.toString(),  null,  null,  null,  null);

        /*
        * The default vmorc results have only 1 merchant enrolled per offer
        * Our algorithm relies on this enrolled merchant count in each offer
        * So, we add some extra merchants to the existing offers returned by querying vmorc
         */
        HashMap<Integer,ArrayList<String>> additionalMerchants = populate(offerIdList,numOffers);

        HashMap<Integer, ArrayList<Integer>> merchantCounts = getMerchantCount(offerBasedResponse,offerIdList,PostalCodesMerchantIDs,additionalMerchants,searchPCode,searchCity,numOffers);

        ArrayList<Double> scorePoints = calculateScorePoints(MerchantPercentages,merchantCounts,offerIdList);
        ArrayList<Integer> sortedOfferIdList = calculateOfferPoints(offerIdList,scorePoints);

        double sum=0;
        for (Double scorePoint : scorePoints) {
            sum += scorePoint;
        }
        for(int i=0;i<scorePoints.size();i++){ //normalized
            scorePoints.set(i,scorePoints.get(i)/sum);
        }

        OfferSuggestorResponse new_res = new OfferSuggestorResponse();

        //Return top 3 offer details
        List<OfferItem> oList = new ArrayList<>();
        for(int i = 0; i < sortedOfferIdList.size(); i++){
            ArrayList<String> responseOfferDetails = mapToDesc.get(sortedOfferIdList.get(i));
            OfferItem offerItem = new OfferItem();
            offerItem.setOfferId("" + sortedOfferIdList.get(i));
            offerItem.setOfferTitle(responseOfferDetails.get(0));
            offerItem.setOfferDesc(responseOfferDetails.get(1));
            offerItem.setValidFrom(responseOfferDetails.get(2));
            offerItem.setValidTo(responseOfferDetails.get(3));

            oList.add(offerItem);
            if(i == 2)
                break;
        }

        new_res.setOfferList(oList);

        if(keyVal == 2)
            return new_res;

        //Get best offer parameters
        String bestPromotionChannel = getBestPromotionChannel(response,numOffers,scorePoints);
        String bestCardProduct = getBestCardProduct(response,numOffers,scorePoints);
        String bestOfferType = getBestOfferType(response,numOffers,scorePoints);

        new_res.setBestPromotionChannel(bestPromotionChannel);
        new_res.setBestCardProduct(bestCardProduct);
        new_res.setBestOfferType(bestOfferType);

        return new_res;
    }

    public ArrayList<Integer> calculateOfferPoints(ArrayList<Integer> offerIdList, ArrayList<Double> scorePoints){
        /*
        * Assigned Priority Points for our algorithm
        * Priority for:
        * Same Postal Code - 0.9
        * Nearby Postal Code - 0.7
        * Same City - 0.5
        * Same Country - 0.2
        */
        HashMap<Integer,Double> H = new HashMap<>();
        for(int i = 0; i < offerIdList.size();i++){
            H.put(offerIdList.get(i),scorePoints.get(i));
        }

        H = sortByValue(H);
        //System.out.println(H);
        ArrayList<Integer> sortedOfferIdList = new ArrayList<>();
        for (Map.Entry<Integer,Double> entry : H.entrySet()){
            sortedOfferIdList.add(entry.getKey());
        }
        return sortedOfferIdList;
    }

    public ArrayList<Double> calculateScorePoints(ArrayList<Double> MerchantPercentages, HashMap<Integer, ArrayList<Integer>> merchantCounts, ArrayList<Integer> offerIdList){
        ArrayList<Double> ScorePoints = new ArrayList<>();
        /*
         * Assigned Priority Points for our algorithm
         * Priority for:
         * Same Postal Code - 0.9
         * Nearby Postal Code - 0.7
         * Same City - 0.5
         * Same Country - 0.2
         */
        for(int i=0;i< offerIdList.size();i++) {
            ScorePoints.add(0.0);
        }
        double[] PriorityPoints = {0.9,0.7,0.5,0.2};
        for(int i=0;i< offerIdList.size();i++){
            for(int j=0;j<4;j++){
                ScorePoints.set(i, ScorePoints.get(i) + merchantCounts.get(offerIdList.get(i)).get(j) * PriorityPoints[j] * (1 + (MerchantPercentages.get(j)/25)));
            }
        }
        return ScorePoints;
    }

    public HashMap<Integer, ArrayList<Integer>> getMerchantCount(RetrieveOffersByOfferIdgetResponse response, ArrayList<Integer> offerIdList, ArrayList<ArrayList<String>> PostalCodesMerchantIDs, HashMap<Integer,ArrayList<String>> additionalMerchants, String postalCode, String city, int numOffers) throws IOException{
        HashMap<String,String> dataList = new HashMap<>();
        for(int i = 0; i < PostalCodesMerchantIDs.get(0).size();i++){
            String mID = PostalCodesMerchantIDs.get(1).get(i);
            String pCode = PostalCodesMerchantIDs.get(0).get(i);

            if(!dataList.containsKey(mID))
                dataList.put(mID,pCode);
        }
        //System.out.println(response);
        HashMap<Integer,ArrayList<Integer>> merchantListCount= new HashMap<>();
        HashMap<String,Integer> addMerchData = new HashMap<>();
        for(int i = 0; i < numOffers;i++){
            MerchantList mList = response.getOffers().get(i).getMerchantList();
            int key = offerIdList.get(i);
            ArrayList<Integer> counts = new ArrayList<>();
            int pCodeCount = 0,pListCount = 0,cityCount = 0,countryCount = mList.size();
            for (MerchantListInner merchantListInner : mList) {
                String mID = "" + merchantListInner.getMerchantId();
                if (dataList.containsKey(mID)) {
                    String pCode = dataList.get(mID);
                    if (pCode.equals(postalCode))
                        pCodeCount++;
                    if (PostalCodesMerchantIDs.get(0).contains(pCode))
                        pListCount++;
                    cityCount++;
                }
            }

            //Call merchant search for additional merchants
            ArrayList<String> addList = additionalMerchants.get(offerIdList.get(i));
            for (String s : addList) {
                int result;
                if (addMerchData.containsKey(s))
                    result = addMerchData.get(s);
                else {
                    MerchantSearchCall mCall = new MerchantSearchCall();
                    result = mCall.checkCity(s, city, postalCode, PostalCodesMerchantIDs.get(0));
                    addMerchData.put(s, result);
                }
                if (result == 1) {
                    pCodeCount++;
                    pListCount++;
                    cityCount++;
                } else if (result == 2) {
                    pListCount++;
                    cityCount++;
                } else if (result == 3) {
                    cityCount++;
                }
                countryCount++;
            }

            counts.add(pCodeCount);
            counts.add(pListCount);
            counts.add(cityCount);
            counts.add(countryCount);

            merchantListCount.put(key,counts);
        }
        // System.out.println(merchantListCount);
        return merchantListCount;
    }
    public String getBestPromotionChannel(RetrieveOffersByFiltergetResponse response,int numOffers,ArrayList<Double> ScorePoints) {
        HashMap<String,Double> promotionChannelCount= new HashMap<>();
        HashMap<String, Integer> promotionChannelCount1= new HashMap<>();
        ArrayList<String> Flag = new ArrayList<>();
        double max_value = 0 , value;
        int count,sum=0;
        String best_key = "";
        for(int i = 0; i < numOffers;i++){
            PromotionChannelList pList = response.getOffers().get(i).getPromotionChannelList();
            sum = sum + pList.size();
            for (SubcategoriesInner subcategoriesInner : pList) {
                String key = subcategoriesInner.getValue();
                if (!Flag.contains(key)) Flag.add(key);
                if (promotionChannelCount.containsKey(key)) {
                    count = promotionChannelCount1.get(key);
                    value = promotionChannelCount.get(key);
                    promotionChannelCount.put(key, value + ScorePoints.get(i) * 0.6);
                    promotionChannelCount1.put(key, count + 1);
                    if (promotionChannelCount.get(key) > max_value) {
                        max_value = promotionChannelCount.get(key);
                        best_key = key;
                    }
                } else {
                    promotionChannelCount.put(key, ScorePoints.get(i) * 0.6);
                    promotionChannelCount1.put(key, 1);
                    if (max_value == 0) {
                        max_value = ScorePoints.get(i) * 0.6;
                        best_key = key;
                    }
                }
            }
        }
        for (String key : Flag) {
            value = promotionChannelCount.get(key);
            promotionChannelCount.put(key, value + ((promotionChannelCount1.get(key) * 0.4) / sum));
            if (promotionChannelCount.get(key) > max_value) {
                max_value = promotionChannelCount.get(key);
                best_key = key;
            }
        }

       // System.out.println(promotionChannelCount);
        return best_key;
    }

    public String getBestCardProduct(RetrieveOffersByFiltergetResponse response,int numOffers,ArrayList<Double> ScorePoints) {
        HashMap<String,Double> cardProductCount= new HashMap<>();
        HashMap<String,Integer> cardProductCount1 = new HashMap<>();
        ArrayList<String> Flag = new ArrayList<>();
        double max_value =0 , value;
        int count,sum = 0;
        String best_key = "";
        for(int i = 0; i < numOffers;i++){
            CardProductList cList = response.getOffers().get(i).getCardProductList();
            sum = sum + cList.size();
            for (SubcategoriesInner subcategoriesInner : cList) {
                String key = subcategoriesInner.getValue();
                if (!Flag.contains(key)) Flag.add(key);
                if (cardProductCount.containsKey(key)) {
                    value = cardProductCount.get(key);
                    count = cardProductCount1.get(key);
                    cardProductCount.put(key, value + ScorePoints.get(i) * 0.6);
                    cardProductCount1.put(key, count + 1);
                    if (cardProductCount.get(key) > max_value) {
                        max_value = cardProductCount.get(key);
                        best_key = key;
                    }
                } else {
                    cardProductCount.put(key, ScorePoints.get(i) * 0.6);
                    cardProductCount1.put(key, 1);
                    if (max_value == 0) {
                        max_value = ScorePoints.get(i) * 0.6;
                        best_key = key;
                    }
                }
            }
        }
        for (String key : Flag) {
            value = cardProductCount.get(key);
            cardProductCount.put(key, value + (cardProductCount1.get(key) / sum) * 0.4);
            if (cardProductCount.get(key) > max_value) {
                max_value = cardProductCount.get(key);
                best_key = key;
            }
        }
       // System.out.println(cardProductCount);
        return best_key;
    }

    public String getBestOfferType(RetrieveOffersByFiltergetResponse response, int numOffers, ArrayList<Double> ScorePoints) {
        HashMap<String,Double> offerTypeCount= new HashMap<>();
        HashMap<String,Integer> offerTypeCount1 = new HashMap<>();
        ArrayList<String> Flag = new ArrayList<>();
        double max_value = 0 , value;
        int count, sum =0;
        String best_key = "";
        for(int i = 0; i < numOffers;i++){
            OfferType oList= response.getOffers().get(i).getOfferType();
            sum = sum + oList.size();
            for (SubcategoriesInner subcategoriesInner : oList) {
                String key = subcategoriesInner.getValue();
                if (!Flag.contains(key)) Flag.add(key);
                if (offerTypeCount.containsKey(key)) {
                    count = offerTypeCount1.get(key);
                    value = offerTypeCount.get(key);
                    offerTypeCount.put(key, value + ScorePoints.get(i) * 0.6);
                    offerTypeCount1.put(key, count + 1);
                    if (offerTypeCount.get(key) > max_value) {
                        max_value = offerTypeCount.get(key);
                        best_key = key;
                    }
                } else {
                    offerTypeCount.put(key, ScorePoints.get(i) * 0.6);
                    offerTypeCount1.put(key, 1);
                    if (max_value == 0) {
                        max_value = ScorePoints.get(i) * 0.6;
                        best_key = key;
                    }
                }
            }
        }
        for (String key : Flag) {
            value = offerTypeCount.get(key);
            offerTypeCount.put(key, value + (offerTypeCount1.get(key) / sum) * 0.4);
            if (offerTypeCount.get(key) > max_value) {
                max_value = offerTypeCount.get(key);
                best_key = key;
            }
        }
       // System.out.println(offerTypeCount);
        return best_key;
    }

    HashMap<Integer,ArrayList<String>>  populate(ArrayList<Integer> offerIDList,int numOffers){
        HashMap<Integer,ArrayList<String>> additionalMerchants = new HashMap<>();
        ArrayList<String> newMerchants = new ArrayList<>();
        newMerchants.add("28495735");
        newMerchants.add("26410561");
        newMerchants.add("11455106");
        //newMerchants.add("23667699");
        newMerchants.add("32732413");
        newMerchants.add("16919626");

        ArrayList<ArrayList<String>> mList = new ArrayList<>();

        for(int k = 0; k < numOffers;k++){
            Random rand = new Random();
            int num = rand.nextInt(newMerchants.size());
            if(num == 0)
                num = 2;
            ArrayList<String> M = new ArrayList<>();
            for(int i = 0; i < num; i++){
                int num2 = rand.nextInt(newMerchants.size());
                if(num2 == newMerchants.size())
                    num2 = newMerchants.size() - 1;
                if(!M.contains(newMerchants.get(num2)))
                    M.add(newMerchants.get(num2));
            }
            mList.add(M);
        }

        for(int i = 0; i < numOffers; i++){
            additionalMerchants.put(offerIDList.get(i),mList.get(i));
        }
       // System.out.println(additionalMerchants);
        return additionalMerchants;
    }

    public HashMap<Integer, Double> sortByValue(HashMap<Integer, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Double> > list =
                new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double> >() {
            public int compare(Map.Entry<Integer, Double> o1,
                               Map.Entry<Integer, Double> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Integer, Double> temp = new LinkedHashMap<>();
        for (Map.Entry<Integer, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    /**
     *
     *
     * Request for offers by content id
     *
     *          if the Api call fails
     */
    /*
    public String getRetrieveOffersByContentIdHandler() throws IOException {
        String contentid = Arrays.asList("contentid_example").get(0);
        ObjectMapper mapper = new ObjectMapper();
        RetrieveOffersByContentIdgetResponse response = api.getretrieveOffersByContentId(contentid,  null,  null,  null,  null);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        return json;
    }
    */
    public OfferSuggestorResponse getRetrieveOffersByFilterHandler(OfferFilterDTO offerFilterDTO) {
        //System.out.println(response);

        RetrieveOffersByFiltergetResponse retrieveOffersByFiltergetResponse = api.getretrieveOffersByFilter(offerFilterDTO.getBusinessSegment(), offerFilterDTO.getCardPayment(), offerFilterDTO.getCardProduct(), null, null, null, null, offerFilterDTO.getPromotionalChannel(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

        OfferSuggestorResponse new_res = new OfferSuggestorResponse();

        int numOffers = retrieveOffersByFiltergetResponse.getReturnedResults();
        List<OfferItem> oList = new ArrayList<>();

        for(int i = 0; i < numOffers; i++){
            OfferItem offerItem = new OfferItem();
            OffersInner offersInner = retrieveOffersByFiltergetResponse.getOffers().get(i);
            offerItem.setOfferId(String.valueOf(offersInner.getOfferId()));
            offerItem.setOfferTitle(offersInner.getOfferTitle());
            offerItem.setOfferDesc(offersInner.getOfferShortDescription().getText());
            offerItem.setValidFrom(offersInner.getValidityFromDate());
            offerItem.setValidTo(offersInner.getValidityToDate());

            oList.add(offerItem);
        }

        new_res.setOfferList(oList);

        return new_res;
    }

    /*
    public void getretrieveOffersByOfferIdTest() throws IOException {
        String offerid = Arrays.asList("102355").get(0);

        ObjectMapper mapper = new ObjectMapper();
        RetrieveOffersByOfferIdgetResponse response = api.getretrieveOffersByOfferId(offerid,  null,  null,  null,  null);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        System.out.println(json);
    }
    */
}
