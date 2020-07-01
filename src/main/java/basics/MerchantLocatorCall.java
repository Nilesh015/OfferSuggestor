package basics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visa.developer.sample.merchant_locator_api.ApiClient;
import com.visa.developer.sample.merchant_locator_api.api.MerchantLocatorApi;
import com.visa.developer.sample.merchant_locator_api.model.MerchantLocatorpostPayload;
import com.visa.developer.sample.merchant_locator_api.model.ModelResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class MerchantLocatorCall {
    private final MerchantLocatorApi api;

    public MerchantLocatorCall(){
        //System.out.println("\nProduct Name: Merchant Locator\nApi Name: Merchant Locator API");
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
        api = new MerchantLocatorApi(apiClient);
    }


    /**
     *
     *
     * TBD
     *
     *          if the Api call fails
     */
    public ArrayList<ArrayList<String>> postMerchantLocatorHandler(int startIndex,String postalCode,String mCC) throws IOException {

        String jsonPayload = "{\"header\":{\"messageDateTime\":\"2020-06-19T14:59:24.903\",\"requestMessageId\":\"Request_001\",\"startIndex\":\""+ startIndex +"\"},\"searchAttrList\":{\"merchantCategoryCode\":[\""+ mCC +"\"],\"merchantCountryCode\":\"840\",\"merchantPostalCode\":\""+ postalCode +"\",\"distance\":\"2\",\"distanceUnit\":\"KM\"},\"responseAttrList\":[\"GNLOCATOR\"],\"searchOptions\":{\"maxRecords\":\"15\",\"matchIndicators\":\"true\",\"matchScore\":\"true\"}}";
        ObjectMapper mapper = new ObjectMapper();
        MerchantLocatorpostPayload body = mapper.readValue(jsonPayload, MerchantLocatorpostPayload.class);

        ModelResponse response = api.postmerchantLocator(body);

        int size = response.getMerchantLocatorpostResponse().getResponse().size();
        ArrayList<ArrayList<String>> merchants = new ArrayList<ArrayList<String>>();
        String merchantID = "";
        String pCode = "";
        String city = "";
        for(int i = 0;i < size; i++){
            merchantID = "" + response.getMerchantLocatorpostResponse().getResponse().get(i).getResponseValues().getVisaMerchantId();
            pCode = response.getMerchantLocatorpostResponse().getResponse().get(i).getResponseValues().getMerchantPostalCode();
            city = response.getMerchantLocatorpostResponse().getResponse().get(i).getResponseValues().getMerchantCity();
            ArrayList<String> arr = new ArrayList<String>();
            arr.add(merchantID);
            arr.add(pCode);
            arr.add(city);
            merchants.add(arr);
        }
        return merchants;
    }
}
