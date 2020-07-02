package basics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visa.developer.sample.merchant_search_api.ApiClient;
import com.visa.developer.sample.merchant_search_api.api.MerchantSearchApi;
import com.visa.developer.sample.merchant_search_api.model.MerchantSearchpostPayload;
import com.visa.developer.sample.merchant_search_api.model.ModelResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class MerchantSearchCall {
    private final MerchantSearchApi api;

    public MerchantSearchCall(){
        //System.out.println("\nProduct Name: Merchant Search\nApi Name: Merchant Search API");
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

    public int checkCity(String merchantID,String city,String postalCode,ArrayList<String> pCodeList) throws IOException {
        String jsonPayload = "{\"searchAttrList\":{\"visaMerchantId\":\""+ Integer.parseInt(merchantID) +"\",\"merchantCity\":\""+ city +"\",\"merchantCountryCode\":840},\"responseAttrList\":[\"GNSTANDARD\"],\"searchOptions\":{\"wildCard\":[\"merchantName\"],\"maxRecords\":\"5\",\"matchIndicators\":\"true\",\"matchScore\":\"true\",\"proximity\":[\"merchantName\"]},\"header\":{\"requestMessageId\":\"Request_001\",\"startIndex\":\"0\",\"messageDateTime\":\"2020-06-19T17:05:32.903\"}}";
        ObjectMapper mapper = new ObjectMapper();
        MerchantSearchpostPayload body = mapper.readValue(jsonPayload, MerchantSearchpostPayload.class);

        //System.out.println(merchantID + "," + city + "," + postalCode);
        ModelResponse response = api.postmerchantSearch(body);
        String pVal = response.getMerchantSearchpostResponse().getResponse().get(0).getResponseValues().getMerchantPostalCode();
        if(pVal.indexOf('-') != -1)
            pVal = pVal.substring(0,pVal.indexOf('-'));
        String cVal = response.getMerchantSearchpostResponse().getResponse().get(0).getMatchIndicators().getMerchantCity();
        String mVal = response.getMerchantSearchpostResponse().getResponse().get(0).getMatchIndicators().getVisaMerchantId();
        //System.out.println(mVal + "," + cVal + "," + pVal);
        if(mVal.equals("N"))//Same Country
            return 4;
        //mVal is Y
        else if(cVal.equals("N"))//Same Country
            return 4;
        //cVal is Y
        else if(!(pCodeList.contains(pVal)))//Same city + country
            return 3;
        //pVal in postal code list
        else if(!pVal.equals(postalCode))//Same pList + city + country
            return 2;
        return 1;//Same pCode + pList + city + country
    }
}
