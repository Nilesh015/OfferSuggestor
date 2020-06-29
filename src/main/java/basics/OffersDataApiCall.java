package basics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visa.developer.sample.offers_data_api.ApiClient;
import com.visa.developer.sample.offers_data_api.api.OffersDataApiApi;
import com.visa.developer.sample.offers_data_api.model.*;

import java.io.IOException;
import java.util.*;

public class OffersDataApiCall {
    private final OffersDataApiApi api;

    public OffersDataApiCall(){
        System.out.println("\nProduct Name: Visa Merchant Offers Resource Center\nApi Name: Offers Data API");
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
    public String getBestOfferParameters() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        RetrieveAllOffersgetResponse response = api.getretrieveAllOffers( null,  null);

        ArrayList<Integer> offerIdList = new ArrayList<Integer>();
        //ArrayList<ArrayList<Integer>> merchantIdList = new ArrayList<ArrayList<Integer>>();

        int numOffers = response.getTotalFoundResults();
        int offerId;
        for(int i = 0; i < numOffers;i++) {
            offerId = response.getOffers().get(i).getOfferId();
            offerIdList.add(offerId);
        }

        String bestPromotionChannel = getBestPromotionChannel(response,numOffers);
        String bestCardProduct = getBestCardProduct(response,numOffers);
        String bestOfferType = getBestOfferType(response,numOffers);

        OfferSuggestorResponse new_res = new OfferSuggestorResponse();
        new_res.setBestPromotionChannel(bestPromotionChannel);
        new_res.setBestCardProduct(bestCardProduct);
        new_res.setBestOfferType(bestOfferType);

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(new_res);
        return json;
    }

    public HashMap<Integer, Integer> getMerchantCount(RetrieveAllOffersgetResponse response, ArrayList<Integer> offerIdList) throws IOException{
        HashMap<Integer,Integer> merchantListCount= new HashMap<Integer,Integer>();
        int numOffers = offerIdList.size();
        int count = 0;
        for(int i = 0; i < numOffers;i++){
            MerchantList mList = response.getOffers().get(i).getMerchantList();
            int key = offerIdList.get(i);
            merchantListCount.put(key,mList.size());
        }
        return merchantListCount;
    }
    public String getBestPromotionChannel(RetrieveAllOffersgetResponse response,int numOffers) throws IOException{
        HashMap<String,Integer> promotionChannelCount= new HashMap<String,Integer>();
        int max_count = 0,count = 0;
        String best_key = "";
        for(int i = 0; i < numOffers;i++){
            PromotionChannelList pList = response.getOffers().get(i).getPromotionChannelList();
            for(int j = 0; j < pList.size();j++){
                String key = pList.get(j).getValue();
                if(promotionChannelCount.containsKey(key)){
                    count = promotionChannelCount.get(key);
                    promotionChannelCount.put(key,count + 1);
                    if(count > max_count)
                    {
                        max_count = count;
                        best_key = key;
                    }
                }
                else {
                    promotionChannelCount.put(key, 1);
                    if(max_count == 0)
                    {
                        max_count = 1;
                        best_key = key;
                    }
                }
            }
        }
        System.out.println(promotionChannelCount);
        return best_key;
    }

    public String getBestCardProduct(RetrieveAllOffersgetResponse response,int numOffers) throws IOException{
        HashMap<String,Integer> cardProductCount= new HashMap<String,Integer>();
        int max_count = 0,count = 0;
        String best_key = "";
        for(int i = 0; i < numOffers;i++){
            CardProductList cList = response.getOffers().get(i).getCardProductList();
            for(int j = 0; j < cList.size();j++){
                String key = cList.get(j).getValue();
                if(cardProductCount.containsKey(key)){
                    count = cardProductCount.get(key);
                    cardProductCount.put(key,count + 1);
                    if(count > max_count)
                    {
                        max_count = count;
                        best_key = key;
                    }
                }
                else {
                    cardProductCount.put(key, 1);
                    if(max_count == 0)
                    {
                        max_count = 1;
                        best_key = key;
                    }
                }
            }
        }
        System.out.println(cardProductCount);
        return best_key;
    }

    public String getBestOfferType(RetrieveAllOffersgetResponse response, int numOffers) throws IOException{
        HashMap<String,Integer> offerTypeCount= new HashMap<String,Integer>();
        int max_count = 0,count = 0;
        String best_key = "";
        for(int i = 0; i < numOffers;i++){
            OfferType oList= response.getOffers().get(i).getOfferType();
            for(int j = 0; j < oList.size();j++){
                String key = oList.get(j).getValue();
                if(offerTypeCount.containsKey(key)){
                    count = offerTypeCount.get(key);
                    offerTypeCount.put(key,count + 1);
                    if(count > max_count)
                    {
                        max_count = count;
                        best_key = key;
                    }
                }
                else {
                    offerTypeCount.put(key, 1);
                    if(max_count == 0)
                    {
                        max_count = 1;
                        best_key = key;
                    }
                }
            }
        }
        System.out.println(offerTypeCount);
        return best_key;
    }

    /**
     *
     *
     * Request for offers by content id
     *
     *          if the Api call fails
     */
    public String getRetrieveOffersByContentIdHandler() throws IOException {
        String contentid = Arrays.asList("contentid_example").get(0);
        ObjectMapper mapper = new ObjectMapper();
        RetrieveOffersByContentIdgetResponse response = api.getretrieveOffersByContentId(contentid,  null,  null,  null,  null);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        return json;
    }

    /**
     *
     *
     * Request for offers by filter
     *
     *          if the Api call fails
     */
    public String getRetrieveOffersByFilterHandler() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RetrieveOffersByFiltergetResponse response = api.getretrieveOffersByFilter( null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  3,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null,  null);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        return json;
    }

    /**
     *
     *
     * Request for offers by an offer id
     *
     *          if the Api call fails
     */
    public void getretrieveOffersByOfferIdTest() throws IOException {
        String offerid = Arrays.asList("102355").get(0);

        ObjectMapper mapper = new ObjectMapper();
        RetrieveOffersByOfferIdgetResponse response = api.getretrieveOffersByOfferId(offerid,  null,  null,  null,  null);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        System.out.println(json);
    }
}
