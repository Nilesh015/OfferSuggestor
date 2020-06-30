package basics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visa.developer.sample.merchant_search_api.ApiClient;
import com.visa.developer.sample.merchant_search_api.api.MerchantSearchApi;
import com.visa.developer.sample.merchant_search_api.model.MerchantSearchpostPayload;
import com.visa.developer.sample.merchant_search_api.model.ModelResponse;

import java.io.IOException;
import java.util.ArrayList;

public class MerchantSearchCall {
    private final MerchantSearchApi api;

    public MerchantSearchCall(){
        System.out.println("\nProduct Name: Merchant Search\nApi Name: Merchant Search API");
        ApiClient apiClient = new ApiClient();
        // Configure HTTP basic authorization: basicAuth
        apiClient.setUsername("NZHWN23TKZXB409MC28821sjIJv04D5KlgsPUjJ6fWlsadQBw");
        apiClient.setPassword("j0I30I75zt4LnoLDct4Z5IEBBUPi1tSj3Yhh8D");
        apiClient.setKeystorePath("C:/Visa/Security/myProject_keyAndCertBundle.jks");
        apiClient.setKeystorePassword("password");
        apiClient.setPrivateKeyPassword("password");

        // To set proxy uncomment the below lines
        // apiClient.setProxyHostName("proxy.address@example.com");
        // apiClient.setProxyPortNumber(0000);
        api = new MerchantSearchApi(apiClient);
    }


    /**
     *
     *
     *
     *
     *          if the Api call fails
     */
    public ArrayList<String> postMerchantSearchHandler(String storeID) throws IOException {

        String jsonPayload = "{\"searchAttrList\":{\"visaStoreId\":\""+ storeID + "\"},\"responseAttrList\":[\"GNSTANDARD\"],\"searchOptions\":{\"wildCard\":[\"merchantName\"],\"maxRecords\":\"5\",\"matchIndicators\":\"true\",\"matchScore\":\"true\",\"proximity\":[\"merchantName\"]},\"header\":{\"requestMessageId\":\"Request_001\",\"startIndex\":\"0\",\"messageDateTime\":\"2020-06-19T17:05:32.903\"}}";
        ObjectMapper mapper = new ObjectMapper();
        MerchantSearchpostPayload body = mapper.readValue(jsonPayload, MerchantSearchpostPayload.class);

        ModelResponse response = api.postmerchantSearch(body);
        ArrayList<String> attributes= new ArrayList<String>();
        String postalCode = response.getMerchantSearchpostResponse().getResponse().get(0).getResponseValues().getMerchantPostalCode();
        String mCC = response.getMerchantSearchpostResponse().getResponse().get(0).getResponseValues().getMerchantCategoryCode().get(0);
        String city = response.getMerchantSearchpostResponse().getResponse().get(0).getResponseValues().getMerchantCity();
        attributes.add(postalCode);
        attributes.add(mCC);
        attributes.add(city);

        //String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        return attributes;
    }


}
