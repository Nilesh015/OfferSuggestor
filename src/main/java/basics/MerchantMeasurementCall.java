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
    public String postMerchantBenchmarkHandler() throws IOException {

        String jsonPayload = "{\"requestHeader\":{\"messageDateTime\":\"2020-06-25T15:38:31.327Z\",\"requestMessageId\":\"6da60e1b8b024532a2e0eacb1af58581\"},\"requestData\":{\"naicsCodeList\":[\"\"],\"merchantCategoryCodeList\":[\"5812\"],\"merchantCategoryGroupsCodeList\":[\"\"],\"postalCodeList\":[\"\"],\"msaList\":[\"7362\"],\"countrySubdivisionList\":[\"\"],\"merchantCountry\":\"840\",\"monthList\":[\"201706\"],\"accountFundingSourceList\":[\"ALl\"],\"eciIndicatorList\":[\"All\"],\"platformIDList\":[\"All\"],\"posEntryModeList\":[\"All\"],\"cardPresentIndicator\":\"CARDPRESENT\",\"groupList\":[\"STANDARD\"]}}";
        ObjectMapper mapper = new ObjectMapper();
        MerchantBenchmarkpostPayload body = mapper.readValue(jsonPayload, MerchantBenchmarkpostPayload.class);

        MerchantBenchmarkpostResponse response = api.postmerchantBenchmark(body);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        return json;
    }
}
