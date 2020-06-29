package basics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visa.developer.sample.merchant_benchmark_api.ApiClient;
import com.visa.developer.sample.merchant_benchmark_api.api.MerchantBenchmarkApi;
import com.visa.developer.sample.merchant_benchmark_api.model.MerchantBenchmarkpostPayload;
import com.visa.developer.sample.merchant_benchmark_api.model.MerchantBenchmarkpostResponse;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class MerchantMeasurementCall {
    private final MerchantBenchmarkApi api;

    public MerchantMeasurementCall(){
        System.out.println("\nProduct Name: Merchant Measurement\nApi Name: Merchant Benchmark API");
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
        api = new MerchantBenchmarkApi(apiClient);
    }


    /**
     *
     *
     * Merchant Benchmark
     *
     *          if the Api call fails
     */
    public double  postMerchantBenchmarkHandler(int a, String postalCode, String mCC, ArrayList<String> PostalCodes, String city) throws IOException {
        int k2= postalCode.indexOf('-');
        postalCode = postalCode.substring(0,k2);
        System.out.println(postalCode);
        System.out.println(mCC);
        System.out.println(PostalCodes);
        System.out.println(city);
        if(a==1) {
            String jsonPayload = "{\"requestHeader\":{\"messageDateTime\":\"2020-06-25T15:38:31.327Z\",\"requestMessageId\":\"6da60e1b8b024532a2e0eacb1af58581\"},\"requestData\":{\"naicsCodeList\":[\"\"],\"merchantCategoryCodeList\":[\""+mCC+"\"],\"merchantCategoryGroupsCodeList\":[\"\"],\"postalCodeList\":[\""+postalCode+"\"],\"msaList\":[\"\"],\"countrySubdivisionList\":[\"\"],\"merchantCountry\":\"840\",\"monthList\":[\"201706\"],\"accountFundingSourceList\":[\"ALl\"],\"eciIndicatorList\":[\"All\"],\"platformIDList\":[\"All\"],\"posEntryModeList\":[\"All\"],\"cardPresentIndicator\":\"CARDPRESENT\",\"groupList\":[\"STANDARD\"]}}";
            ObjectMapper mapper = new ObjectMapper();
            MerchantBenchmarkpostPayload body = mapper.readValue(jsonPayload, MerchantBenchmarkpostPayload.class);

            MerchantBenchmarkpostResponse response = api.postmerchantBenchmark(body);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
            System.out.println(response.getStatus().getStatus());
            if(response.getStatus().getStatus().equals("CDI000")) { //success
                //System.out.println("Inside success");
                String k = response.getResponse().getResponseData().get(0).getSalesVolumeGrowthMoM();
                if(k=="") return -3; //default for no percentage data
                double k1 = Double.parseDouble(k);
                return k1;
            }
            else if(response.getStatus().getStatus().equals("CDI132")){ //data not present
                return -6; //default
            }
            else{ //some other error
                return -2;
            }
        }
        else if(a==2) {
            String s = "";
            for (int i = 0; i < PostalCodes.size()-1; i++) {
                if (i > 0) {
                    s = s + "," + "\"" + PostalCodes.get(i) + "\"";
                } else {
                    s = s + "\"" + PostalCodes.get(i) + "\"";
                }
            }
            String jsonPayload1 = "{\"requestHeader\":{\"messageDateTime\":\"2020-06-25T15:38:31.327Z\",\"requestMessageId\":\"6da60e1b8b024532a2e0eacb1af58581\"},\"requestData\":{\"naicsCodeList\":[\"\"],\"merchantCategoryCodeList\":[\"" + mCC + "\"],\"merchantCategoryGroupsCodeList\":[\"\"],\"postalCodeList\":[" + s + "],\"msaList\":[\"\"],\"countrySubdivisionList\":[\"\"],\"merchantCountry\":\"840\",\"monthList\":[\"201706\"],\"accountFundingSourceList\":[\"ALl\"],\"eciIndicatorList\":[\"All\"],\"platformIDList\":[\"All\"],\"posEntryModeList\":[\"All\"],\"cardPresentIndicator\":\"CARDPRESENT\",\"groupList\":[\"STANDARD\"]}}";
            ObjectMapper mapper = new ObjectMapper();
            MerchantBenchmarkpostPayload body = mapper.readValue(jsonPayload1, MerchantBenchmarkpostPayload.class);

            MerchantBenchmarkpostResponse response = api.postmerchantBenchmark(body);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
            System.out.println(response.getStatus().getStatus());
            if(response.getStatus().getStatus().equals("CDI000")) { //success
                String k = response.getResponse().getResponseData().get(0).getSalesVolumeGrowthMoM();
                if(k=="") return -3; //default for no percentage data
                double k1 = Double.parseDouble(k);
                return k1;
            }
            else if(response.getStatus().getStatus().equals("CDI132")){ //data not present
                return -6; //default
            }
            else{ //some other error
                return -2;
            }
        }
        else if(a==3) {
            String code = "7362"; //defalut
            if(city.equals("SAN FRANCISCO")){ //later we can keep 2-3 cities like this in if statements or create a table for this
                code="7362";
            }
            String jsonPayload2 = "{\"requestHeader\":{\"messageDateTime\":\"2020-06-25T15:38:31.327Z\",\"requestMessageId\":\"6da60e1b8b024532a2e0eacb1af58581\"},\"requestData\":{\"naicsCodeList\":[\"\"],\"merchantCategoryCodeList\":[\"" + mCC + "\"],\"merchantCategoryGroupsCodeList\":[\"\"],\"postalCodeList\":[\"\"],\"msaList\":[\""+code+"\"],\"countrySubdivisionList\":[\"\"],\"merchantCountry\":\"840\",\"monthList\":[\"201706\"],\"accountFundingSourceList\":[\"ALl\"],\"eciIndicatorList\":[\"All\"],\"platformIDList\":[\"All\"],\"posEntryModeList\":[\"All\"],\"cardPresentIndicator\":\"CARDPRESENT\",\"groupList\":[\"STANDARD\"]}}";
            ObjectMapper mapper = new ObjectMapper();
            MerchantBenchmarkpostPayload body = mapper.readValue(jsonPayload2, MerchantBenchmarkpostPayload.class);

            MerchantBenchmarkpostResponse response = api.postmerchantBenchmark(body);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
            System.out.println(response.getStatus().getStatus());
            if(response.getStatus().getStatus().equals("CDI000")) { //success
                String k = response.getResponse().getResponseData().get(0).getSalesVolumeGrowthMoM();
                if(k=="") return -3; //default for no percentage data
                double k1 = Double.parseDouble(k);
                return k1;
            }
            else if(response.getStatus().getStatus().equals("CDI132")){ //data not present
                return -6; //default
            }
            else{ //some other error
                return -2;
            }
        }
        else if(a==4) {
            String jsonPayload3 = "{\"requestHeader\":{\"messageDateTime\":\"2020-06-25T15:38:31.327Z\",\"requestMessageId\":\"6da60e1b8b024532a2e0eacb1af58581\"},\"requestData\":{\"naicsCodeList\":[\"\"],\"merchantCategoryCodeList\":[\"" + mCC + "\"],\"merchantCategoryGroupsCodeList\":[\"\"],\"postalCodeList\":[\"\"],\"msaList\":[\"\"],\"countrySubdivisionList\":[\"\"],\"merchantCountry\":\"840\",\"monthList\":[\"201706\"],\"accountFundingSourceList\":[\"ALl\"],\"eciIndicatorList\":[\"All\"],\"platformIDList\":[\"All\"],\"posEntryModeList\":[\"All\"],\"cardPresentIndicator\":\"CARDPRESENT\",\"groupList\":[\"STANDARD\"]}}";
            ObjectMapper mapper = new ObjectMapper();
            MerchantBenchmarkpostPayload body = mapper.readValue(jsonPayload3, MerchantBenchmarkpostPayload.class);

            MerchantBenchmarkpostResponse response = api.postmerchantBenchmark(body);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
            System.out.println(response.getStatus().getStatus());
            if(response.getStatus().getStatus().equals("CDI000")) { //success
                String k = response.getResponse().getResponseData().get(0).getSalesVolumeGrowthMoM();
                if(k=="") return -3; //default for no percentage data
                double k1 = Double.parseDouble(k);
                return k1;
            }
            else if(response.getStatus().getStatus().equals("CDI132")){ //data not present
                return -6; //default
            }
            else{ //some other error
                return -2;
            }
        }
     /*   ObjectMapper mapper = new ObjectMapper();
        MerchantBenchmarkpostPayload body = mapper.readValue(jsonPayload, MerchantBenchmarkpostPayload.class);

        MerchantBenchmarkpostResponse response = api.postmerchantBenchmark(body);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        String k  = response.getResponse().getResponseData().get(0).getSalesVolumeGrowthMoM();
        double k1 = Double.parseDouble(k);*/
        //return k;
        //String json ="";
        //return json;
        return 5;

    }
}
