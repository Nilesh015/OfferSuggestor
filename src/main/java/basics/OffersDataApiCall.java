package basics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visa.developer.sample.offers_data_api.ApiClient;
import com.visa.developer.sample.offers_data_api.api.OffersDataApiApi;
import com.visa.developer.sample.offers_data_api.model.RetrieveAllOffersgetResponse;
import com.visa.developer.sample.offers_data_api.model.RetrieveOffersByContentIdgetResponse;
import com.visa.developer.sample.offers_data_api.model.RetrieveOffersByFiltergetResponse;
import com.visa.developer.sample.offers_data_api.model.RetrieveOffersByOfferIdgetResponse;

import java.io.IOException;
import java.util.Arrays;

public class OffersDataApiCall {
    private final OffersDataApiApi api;

    public OffersDataApiCall(){
        System.out.println("\nProduct Name: Visa Merchant Offers Resource Center\nApi Name: Offers Data API");
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
        api = new OffersDataApiApi(apiClient);
    }


    /**
     *
     *
     * Request for all offers
     *
     *          if the Api call fails
     */
    public String getRetrieveAllOffersHandler() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        RetrieveAllOffersgetResponse response = api.getretrieveAllOffers( null,  1);

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        return json;
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
    public String getRetrieveOffersByOfferIdHandler() throws IOException {


        String offerid = Arrays.asList("102355").get(0);

        ObjectMapper mapper = new ObjectMapper();
        RetrieveOffersByOfferIdgetResponse response = api.getretrieveOffersByOfferId(offerid,  null,  null,  null,  null);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        return json;
    }
}
