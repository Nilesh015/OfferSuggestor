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

package com.visa.developer.sample.merchant_locator_api.api;

import com.visa.developer.sample.merchant_locator_api.ApiClient;
import com.visa.developer.sample.merchant_locator_api.model.MerchantLocatorpostPayload;
import com.visa.developer.sample.merchant_locator_api.model.ModelResponse;
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

import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.java.JavaClientCodegen", date = "2018-12-19T10:49:03.812+05:30[Asia/Kolkata]")
@Component("com.visa.developer.sample.merchant_locator_api.api.MerchantLocatorApi")

public class MerchantLocatorApi {
    private ApiClient apiClient;

    public MerchantLocatorApi() {
        this(new ApiClient());
    }

    @Autowired
    public MerchantLocatorApi(ApiClient apiClient) {
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
     * TBD
     * <p><b>2XX</b> - Successful response object.
     * @param body The body parameter
     * @return MerchantLocatorpostResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API

     */
    public ModelResponse postmerchantLocator(MerchantLocatorpostPayload body) throws RestClientException {
        Object postBody = body;
        
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling postmerchantLocator");
        }
        
        String path = UriComponentsBuilder.fromPath("/merchantlocator/v1/locator").build().toUriString();
        String resourcePath = UriComponentsBuilder.fromPath("locator").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "basicAuth" };

        ParameterizedTypeReference<ModelResponse> returnType = new ParameterizedTypeReference<ModelResponse>() {};
        return apiClient.invokeAPI(path, resourcePath, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType, ModelResponse.class);
    }
    
}

