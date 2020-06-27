package com.visa.developer.sample.merchant_locator_api.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelResponse {

    @JsonProperty("merchantLocatorServiceResponse")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private MerchantLocatorpostResponse merchantLocatorpostResponse;

    public MerchantLocatorpostResponse getMerchantLocatorpostResponse() {
        return merchantLocatorpostResponse;
    }

    public void setMerchantLocatorpostResponse(MerchantLocatorpostResponse merchantLocatorpostResponse) {
        this.merchantLocatorpostResponse = merchantLocatorpostResponse;
    }

    @Override
    public String toString() {
        return "ModelResponse{" +
                "merchantLocatorpostResponse=" + merchantLocatorpostResponse +
                '}';
    }
}
