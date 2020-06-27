package basics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visa.developer.sample.merchant_locator_api.ApiClient;
import com.visa.developer.sample.merchant_locator_api.api.MerchantLocatorApi;
import com.visa.developer.sample.merchant_locator_api.model.MerchantLocatorpostPayload;
import com.visa.developer.sample.merchant_locator_api.model.ModelResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MerchantLocatorCall {
    private final MerchantLocatorApi api;

    public MerchantLocatorCall(){
        System.out.println("\nProduct Name: Merchant Locator\nApi Name: Merchant Locator API");
        ApiClient apiClient = new ApiClient();
        // Configure HTTP basic authorization: basicAuth
        apiClient.setUsername("LLT3CK0NZYQT1M9DIOBJ21o-cByoaU-GqBdx2aQm_RFXrBNj0");
        apiClient.setPassword("o2iEIyXC6op8tB7B");
        apiClient.setKeystorePath("/home/nilesh015/Desktop/VDP/security/myProject_keyAndCertBundle.jks");
        apiClient.setKeystorePassword("password");
        apiClient.setPrivateKeyPassword("password");

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

        for(int i = 0;i < size; i++){
            merchantID = "" + response.getMerchantLocatorpostResponse().getResponse().get(i).getResponseValues().getVisaMerchantId();
            pCode = response.getMerchantLocatorpostResponse().getResponse().get(i).getResponseValues().getMerchantPostalCode();
            ArrayList<String> arr = new ArrayList<String>();
            arr.add(merchantID);
            arr.add(pCode);
            merchants.add(arr);
        }
        return merchants;
    }
}
