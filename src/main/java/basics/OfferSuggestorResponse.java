package basics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OfferSuggestorResponse {
    @JsonProperty("bestPromotionChannel")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String bestPromotionChannel = null;

    @JsonProperty("bestCardProduct")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String bestCardProduct = null;

    @JsonProperty("bestOfferType")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String bestOfferType = null;

    @JsonProperty("offerList")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<OfferItem> offerList = null;

    public List<OfferItem> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<OfferItem> offerList) {
        this.offerList = offerList;
    }

    public String getBestPromotionChannel() {
        return bestPromotionChannel;
    }

    public void setBestPromotionChannel(String bestPromotionChannel) {
        this.bestPromotionChannel = bestPromotionChannel;
    }

    public String getBestCardProduct() {
        return bestCardProduct;
    }

    public void setBestCardProduct(String bestCardProduct) {
        this.bestCardProduct = bestCardProduct;
    }

    public String getBestOfferType() {
        return bestOfferType;
    }

    public void setBestOfferType(String bestOfferType) {
        this.bestOfferType = bestOfferType;
    }
}
