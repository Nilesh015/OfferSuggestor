package com.visa.developer.sample.merchant_search_api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelResponse {
    @JsonProperty("merchantSearchServiceResponse")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private MerchantSearchpostResponse merchantSearchpostResponse;

    public MerchantSearchpostResponse getMerchantSearchpostResponse() {
        return merchantSearchpostResponse;
    }

    public void setMerchantSearchpostResponse(MerchantSearchpostResponse merchantSearchpostResponse) {
        this.merchantSearchpostResponse = merchantSearchpostResponse;
    }

    @Override
    public String toString() {
        return "ModelResponse{" +
                "merchantSearchpostResponse=" + merchantSearchpostResponse +
                '}';
    }
}
