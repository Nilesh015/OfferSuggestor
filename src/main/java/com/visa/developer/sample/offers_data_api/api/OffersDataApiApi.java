/* ----------------------------------------------------------------------------------------------------------------------
* © Copyright 2018 Visa. All Rights Reserved.
*
* NOTICE: The software and accompanying information and documentation (together, the “Software”) remain the property of
* and are proprietary to Visa and its suppliers and affiliates. The Software remains protected by intellectual property
* rights and may be covered by U.S. and foreign patents or patent applications. The Software is licensed and not sold.
*
* By accessing the Software you are agreeing to Visa's terms of use (developer.visa.com/terms) and privacy policy
* (developer.visa.com/privacy). In addition, all permissible uses of the Software must be in support of Visa products,
* programs and services provided through the Visa Developer Program (VDP) platform only (developer.visa.com).
* THE SOFTWARE AND ANY ASSOCIATED INFORMATION OR DOCUMENTATION IS PROVIDED ON AN “AS IS,” “AS AVAILABLE,” “WITH ALL
* FAULTS” BASIS WITHOUT WARRANTY OR CONDITION OF ANY KIND. YOUR USE IS AT YOUR OWN RISK.
---------------------------------------------------------------------------------------------------------------------- */

package com.visa.developer.sample.offers_data_api.api;

import com.visa.developer.sample.offers_data_api.ApiClient;
import com.visa.developer.sample.offers_data_api.model.RetrieveAllOffersgetResponse;
import com.visa.developer.sample.offers_data_api.model.RetrieveOffersByContentIdgetResponse;
import com.visa.developer.sample.offers_data_api.model.RetrieveOffersByFiltergetResponse;
import com.visa.developer.sample.offers_data_api.model.RetrieveOffersByOfferIdgetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.java.JavaClientCodegen", date = "2018-12-19T10:49:33.222+05:30[Asia/Kolkata]")
@Component("com.visa.developer.sample.offers_data_api.api.OffersDataApiApi")

public class OffersDataApiApi {
    private ApiClient apiClient;

    public OffersDataApiApi() {
        this(new ApiClient());
    }

    @Autowired
    public OffersDataApiApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    
    /**
     * 
     * Request for all offers
     * <p><b>2XX</b> - Successful response object.
     * @param startIndex A maximum of 500 offer results may be returned in the response. The parameter specifies the index of the total available offer results to start returning in the response. 
     * @param maxOffers Optional. A maximum of 500 offer results are returned in the offer response. The index indicates the maximum number of offers to return in the response. Accepts an integer (greater than 0; less than or equal to 500). Default set to 500
     * @return RetrieveAllOffersgetResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public RetrieveAllOffersgetResponse getretrieveAllOffers(Integer startIndex, Integer maxOffers) throws RestClientException {
        Object postBody = null;
        
        String path = UriComponentsBuilder.fromPath("/vmorc/offers/v1/all").build().toUriString();
        String resourcePath = UriComponentsBuilder.fromPath("all").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "start_index", startIndex));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "max_offers", maxOffers));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "basicAuth" };

        ParameterizedTypeReference<RetrieveAllOffersgetResponse> returnType = new ParameterizedTypeReference<RetrieveAllOffersgetResponse>() {};
        return apiClient.invokeAPI(path, resourcePath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType, RetrieveAllOffersgetResponse.class);
    }
    
    /**
     * 
     * Request for offers by content id
     * <p><b>2XX</b> - Successful response object.
     * @param contentid Retrieve offers by their content ids. Provide an content id integer or a comma-delimited string of content id integers
     * @param updatefrom Request for offers that are updated after a specified date (in GMT). Accepts a date formatted by: yyyyMMdd
     * @param updateto Request for offers that are updated before a specified date (in GMT). Accepts a date formatted by: yyyyMMdd
     * @param startIndex Optional. A maximum of 500 offer results are returned in the offer response. The index indicates which offer within the sorted offer results to start returning in the offer response. Accepts an integer. Default set to 1
     * @param maxOffers Optional. A maximum of 500 offer results are returned in the offer response. The index indicates the maximum number of offers to return in the response. Accepts an integer (greater than 0; less than or equal to 500). Default set to 500
     * @return RetrieveOffersByContentIdgetResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public RetrieveOffersByContentIdgetResponse getretrieveOffersByContentId(String contentid, String updatefrom, String updateto, Integer startIndex, Integer maxOffers) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'contentid' is set
        if (contentid == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'contentid' when calling getretrieveOffersByContentId");
        }
        
        String path = UriComponentsBuilder.fromPath("/vmorc/offers/v1/bycontentid").build().toUriString();
        String resourcePath = UriComponentsBuilder.fromPath("bycontentid").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "contentid", contentid));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "updatefrom", updatefrom));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "updateto", updateto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "start_index", startIndex));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "max_offers", maxOffers));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "basicAuth" };

        ParameterizedTypeReference<RetrieveOffersByContentIdgetResponse> returnType = new ParameterizedTypeReference<RetrieveOffersByContentIdgetResponse>() {};
        return apiClient.invokeAPI(path, resourcePath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType, RetrieveOffersByContentIdgetResponse.class);
    }
    
    /**
     * 
     * Request for offers by filter
     * <p><b>2XX</b> - Successful response object.
     * @param businessSegment Filter offers by business segments. Provide one \&quot;key\&quot; integer or a comma-delimited string of \&quot;key\&quot; integers  Refer to &lt;a href&#x3D;\&quot;/guides/request_response_codes#business_segments\&quot;&gt;business_segments&lt;/a&gt; for sample values and keys.
     * @param cardPaymentType Filter offers by card payment types. Provide one \&quot;key\&quot; integer or a comma-delimited string of \&quot;key\&quot; integers  Refer to &lt;a href&#x3D;\&quot;/guides/request_response_codes#card_payment_types\&quot;&gt;card_payment_types&lt;/a&gt; for sample values and keys. 
     * @param cardProduct Filter offers by card products. Provide one \&quot;key\&quot; integer or a comma-delimited string of \&quot;key\&quot; integers  Refer to &lt;a href&#x3D;\&quot;/guides/request_response_codes#card_product\&quot;&gt;card_product&lt;/a&gt; for sample values and keys.
     * @param category Filter offers by offer categories. Provide one \&quot;key\&quot; integer or a comma-delimited string of \&quot;key\&quot; integers  Refer to &lt;a href&#x3D;\&quot;/guides/request_response_codes#category_and_subcategory\&quot;&gt;category_subcategory&lt;/a&gt; for sample values and keys. 
     * @param subcategory Filter offers by offer subcategories. Provide one \&quot;key\&quot; integer or a comma-delimited string of \&quot;key\&quot; integers  Refer to &lt;a href&#x3D;\&quot;/guides/request_response_codes#category_and_subcategory\&quot;&gt;category_subcategory&lt;/a&gt; for sample values and keys. 
     * @param merchant Filter offers by merchants. Provide one \&quot;key\&quot; integer or a comma-delimited string of \&quot;key\&quot; integers   Refer to &lt;a href&#x3D;\&quot;/guides/request_response_codes#merchant\&quot;&gt;merchant&lt;/a&gt; for sample values and keys.
     * @param program Filter offers by programs. Provide one \&quot;key\&quot; integer or a comma-delimited string of \&quot;key\&quot; integers  Refer to &lt;a href&#x3D;\&quot;/guides/request_response_codes#program\&quot;&gt;program&lt;/a&gt; for sample values and keys.
     * @param promotionChannel Filter offers by promotion channels. Provide one \&quot;key\&quot; integer or a comma-delimited string of \&quot;key\&quot; integers   Refer to &lt;a href&#x3D;\&quot;/guides/request_response_codes#promotion_channel\&quot;&gt;promotion_channel&lt;/a&gt; for sample values and keys.
     * @param promotingRegion Filter offers by promoting regions. Provide one \&quot;key\&quot; integer or a comma-delimited string of \&quot;key\&quot; integers  Refer to &lt;a href&#x3D;\&quot;/guides/request_response_codes#region\&quot;&gt;region&lt;/a&gt; for sample values and keys.
     * @param promotingCountry Filter offers by promoting countries. Provide one \&quot;key\&quot; integer or a comma-delimited string of \&quot;key\&quot; integers    Refer to &lt;a href&#x3D;\&quot;/guides/request_response_codes#country\&quot;&gt;country&lt;/a&gt; for sample values and keys.
     * @param redemptionRegion Filter offers by redemption regions. Provide one \&quot;key\&quot; integer or a comma-delimited string of \&quot;key\&quot; integers    Refer to &lt;a href&#x3D;\&quot;/guides/request_response_codes#region\&quot;&gt;region&lt;/a&gt; for sample values and keys.
     * @param redemptionCountry Filter offers by redemption countries. Provide one \&quot;key\&quot; integer or a comma-delimited string of \&quot;key\&quot; integers  Refer to &lt;a href&#x3D;\&quot;/guides/request_response_codes#country\&quot;&gt;country&lt;/a&gt; for sample values and keys.
     * @param merchantRegion Filter for offers that have been assigned a merchant address in at least one merchant address region parameter   Refer to &lt;a href&#x3D;\&quot;/guides/request_response_codes#region\&quot;&gt;region&lt;/a&gt; for sample values and keys.
     * @param merchantCounty Filter for offers that have been assigned a merchant address in at least one merchant address country parameter    Refer to &lt;a href&#x3D;\&quot;/guides/request_response_codes#country\&quot;&gt;country&lt;/a&gt; for sample values and keys.
     * @param language Filter offers by offer languages. Provide one \&quot;key\&quot; integer or a comma-delimited string of \&quot;key\&quot; integers
     * @param expired Request for expired offers. Provide a boolean value. Default set to value to \&quot;false\&quot;.
     * @param validfrom Request for offers where the offer&#x27;s redemption end date is on or after the provided date (in GMT). Accepts a date formatted by: yyyyMMdd e.g. 1, If validfrom&#x3D;20150101 is provided, this returns eligible offers that end on or after January 1, 2015 e.g. 2, If validfrom&#x3D;20150101&amp;validto&#x3D;20150131, this will return eligible offers that end on or after January 1, 2015 and start on or before January 31, 2015 (valid during at least one day in January 2015).
     * @param validto Request for offers where the offer’s redemption start date is before or on the provided date (in GMT). Accepts a date formatted   by: yyyyMMdd e.g. 1, If validto&#x3D;20150131 is provided, this will     return eligible offers that start before or on January 31,       2015 e.g. 2, If validfrom&#x3D;20150101&amp;validto&#x3D;20150131, this will return eligible offers that end on or after January 1, 2015 and start on or before January 31, 2015 (valid during at least one day in January 2015).
     * @param promotedfrom Request for offers where the offer’s promotion end date is on or  after the provided date (in GMT). Accepts a date formatted by: yyyyMMdd e.g. 1, If promotedfrom&#x3D;20150101 is provided, this will / return eligible offers where the promotion ends on or after January 1, 2015 e.g. 2, If promotedfrom&#x3D;20150101&amp;promotedto&#x3D;20150131, this will return eligible offers where the promotion ends on or after January 1, 2015 and start on or before January 31, 2015 (promoted at least one day in January 2015).
     * @param promotedto Request for offers where the offer’s promotion start date is before or on the provided date (in GMT). Accepts a date formatted by: yyyyMMdd e.g. 1, If promotedto&#x3D;20150131 is provided, this will return eligible offers where the promotion starts before or on January 31, 2015 e.g. 2, If promotedfrom&#x3D;20150101&amp;promotedto&#x3D;20150131, this will return eligible offers where the promotion ends on or after January 1, 2015 and start on or before January 31, 2015 (promoted at least one day in January 2015).
     * @param updatefrom Request for offers where the provided date is before or on an offer’s last modified date/time (in GMT). Accepts a date formatted by: yyyyMMdd
     * @param updateto Request for offers where the provided date is after or on an       offer’s last modified date/time (in GMT). Accepts a date     formatted by: yyyyMMdd
     * @param featured Request for featured offers. Provide a boolean value.
     * @param startIndex A maximum of 500 offer results are returned in the offer response. The index indicates which offer within the sorted offer results to start returning in the offer response. Accepts an integer. Default set to 1
     * @param maxOffers Optional. A maximum of 500 offer results are returned in the offer response. The index indicates the maximum number of offers to return in the response. Accepts an integer (greater than 0; less than or equal to 500). Default set to 500
     * @param bins Request for offers that fulfill one or more BIN options by inputting the exact desired bin value(s). Comma-delimit for multiple values
     * @param rpins Request for offers that fulfill one or more RPIN options by inputting the exact desired rpin value(s). Comma-delimit for multiple values
     * @param binsToRpins Request for offers that fulfill one or more BIN to RPIN pairing options by inputting the exact desired bin value, a tilde(\&quot;~\&quot;), and the exact rpin value.
     * @param accountranges Request for offers that fulfill a specific Account Range by     providing either  a \&quot;from\&quot; prefix value OR a \&quot;to\&quot; prefix value OR a \&quot;from\&quot; and a \&quot;to\&quot; prefix values. Each provided prefix value   must be a minimum of 4 digits. Each account range request must contain a colon(\&quot;:\&quot;) to distinguish between the \&quot;from\&quot; and \&quot;to\&quot; values (even if only one boundary is provided).
     * @param accountrangesToRpins Request for offers that fulfill a specific Account Range to RPIN pairing by providing either a \&quot;from\&quot; prefix value ~ RPIN OR a \&quot;to\&quot; prefix value ~ RPIN OR a \&quot;from\&quot; and a \&quot;to\&quot; prefix values ~ RPIN. Each provided account range prefix value must be a minimum of 4 digits. Each account range portion of the request must contain a colon(\&quot;:\&quot;) to distinguish between the \&quot;from\&quot; and \&quot;to\&quot; values (even if only one boundary is provided). Use a tilde(\&quot;~\&quot;) to separate the account range from the exact desired rpin.
     * @param pans Request for offers by PAN (must be a minimum of 16 digits). The provided PAN is padded-right with \&quot;0\&quot; to a length of 21 digits. The system returns the offers where the padded value is within the subset of at least one of the offer&#x27;s account range assignments.
     * @param nonCardAttribute Request for offers that do not have assignments to card attribute fields. Provide a boolean value. Default sets value to \&quot;false\&quot;.
     * @param origin Required for applying a geolocation filter. Input the origin by specifying the latitude, a comma (\&quot;,\&quot;), and the longitude. Coordinates must be inputted in decimal degree format. The accepted range for latitude is between -90 and 90, inclusive. The accepted range for longitude is between -180 and 180, inclusive.
     * @param radius Optional for applying a geolocation filter.. A maximum radius of 1000 (kilometers) or 621.371 (miles) is accepted. Default sets to 60 miles (or 100 kilometers if the \&quot;unit\&quot; geolocation parameter has been set to \&quot;km\&quot;).
     * @param unit Optional for applying a geolocation filter.. Indicate the distance unit of miles or kilometers. Default sets to miles. To use kilometers, specify \&quot;km\&quot;.
     * @param nonGeo Optional for applying a geolocation filter.. Request for offers that have not been assigned merchant addresses with geo-location(latitude/longitude) coordinates. At minimum, an origin must be also provided to call this flag. Accepts a boolean value - By default, the boolean value is set to false. 
     * @return RetrieveOffersByFiltergetResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public RetrieveOffersByFiltergetResponse getretrieveOffersByFilter(String businessSegment, String cardPaymentType, String cardProduct, String category, String subcategory, String merchant, String program, String promotionChannel, String promotingRegion, String promotingCountry, String redemptionRegion, String redemptionCountry, String merchantRegion, String merchantCounty, String language, Boolean expired, String validfrom, String validto, String promotedfrom, String promotedto, String updatefrom, String updateto, Boolean featured, Integer startIndex, Integer maxOffers, String bins, String rpins, String binsToRpins, String accountranges, String accountrangesToRpins, String pans, Boolean nonCardAttribute, String origin, BigDecimal radius, BigDecimal unit, Boolean nonGeo) throws RestClientException {
        Object postBody = null;
        
        String path = UriComponentsBuilder.fromPath("/vmorc/offers/v1/byfilter").build().toUriString();
        String resourcePath = UriComponentsBuilder.fromPath("byfilter").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "business_segment", businessSegment));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "card_payment_type", cardPaymentType));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "card_product", cardProduct));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "category", category));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "subcategory", subcategory));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "merchant", merchant));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "program", program));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "promotion_channel", promotionChannel));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "promoting_region", promotingRegion));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "promoting_country", promotingCountry));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "redemption_region", redemptionRegion));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "redemption_country", redemptionCountry));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "merchant_region", merchantRegion));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "merchant_county", merchantCounty));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "language", language));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "expired", expired));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "validfrom", validfrom));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "validto", validto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "promotedfrom", promotedfrom));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "promotedto", promotedto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "updatefrom", updatefrom));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "updateto", updateto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "featured", featured));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "start_index", startIndex));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "max_offers", maxOffers));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "bins", bins));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "rpins", rpins));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "bins_to_rpins", binsToRpins));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "accountranges", accountranges));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "accountranges_to_rpins", accountrangesToRpins));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pans", pans));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "non_cardAttribute", nonCardAttribute));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "origin", origin));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "radius", radius));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "unit", unit));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "non_geo", nonGeo));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "basicAuth" };

        ParameterizedTypeReference<RetrieveOffersByFiltergetResponse> returnType = new ParameterizedTypeReference<RetrieveOffersByFiltergetResponse>() {};
        return apiClient.invokeAPI(path, resourcePath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType, RetrieveOffersByFiltergetResponse.class);
    }
    
    /**
     * 
     * Request for offers by an offer id
     * <p><b>2XX</b> - Successful response object.
     * @param offerid Retrieve offers by their offer ids. Provide an offer id integer or a comma-delimited string of offer id integers
     * @param updatefrom Request for offers that are updated after a specified date (in GMT). Accepts a date formatted by: yyyyMMdd
     * @param updateto Request for offers that are updated before a specified date (in GMT). Accepts a date formatted by: yyyyMMdd
     * @param startIndex Optional. A maximum of 500 offer results are returned in the offer response. The index indicates which offer within the sorted offer results to start returning in the offer response. Accepts an integer. Default set to 1
     * @param maxOffers Optional. A maximum of 500 offer results are returned in the offer response. The index indicates the maximum number of offers to return in the response. Accepts an integer (greater than 0; less than or equal to 500). Default set to 500
     * @return RetrieveOffersByOfferIdgetResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public RetrieveOffersByOfferIdgetResponse getretrieveOffersByOfferId(String offerid, String updatefrom, String updateto, Integer startIndex, Integer maxOffers) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'offerid' is set
        if (offerid == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'offerid' when calling getretrieveOffersByOfferId");
        }
        
        String path = UriComponentsBuilder.fromPath("/vmorc/offers/v1/byofferid").build().toUriString();
        String resourcePath = UriComponentsBuilder.fromPath("byofferid").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "offerid", offerid));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "updatefrom", updatefrom));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "updateto", updateto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "start_index", startIndex));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "max_offers", maxOffers));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "basicAuth" };

        ParameterizedTypeReference<RetrieveOffersByOfferIdgetResponse> returnType = new ParameterizedTypeReference<RetrieveOffersByOfferIdgetResponse>() {};
        return apiClient.invokeAPI(path, resourcePath, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType, RetrieveOffersByOfferIdgetResponse.class);
    }
    
}

