package basics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferFilterDTO {

    @JsonProperty("businessSegment")
    private String businessSegment;

    @JsonProperty("cardPayment")
    private String cardPayment;

    @JsonProperty("cardProduct")
    private String cardProduct;

    @JsonProperty("promotionalChannel")
    private String promotionalChannel;

    @JsonProperty("offerIDs")
    private List<Integer> offerIDs;

    public String getBusinessSegment() {
        return businessSegment;
    }

    public void setBusinessSegment(String businessSegment) {
        this.businessSegment = businessSegment;
    }

    public String getCardPayment() {
        return cardPayment;
    }

    public void setCardPayment(String cardPayment) {
        this.cardPayment = cardPayment;
    }

    public String getCardProduct() {
        return cardProduct;
    }

    public void setCardProduct(String cardProduct) {
        this.cardProduct = cardProduct;
    }

    public String getPromotionalChannel() {
        return promotionalChannel;
    }

    public void setPromotionalChannel(String promotionalChannel) {
        this.promotionalChannel = promotionalChannel;
    }

    public List<Integer> getOfferIDs() {
        return offerIDs;
    }

    public void setOfferIDs(List<Integer> offerIDs) {
        this.offerIDs = offerIDs;
    }

    @Override
    public String toString() {
        return "OfferFilterDTO{" +
                "businessSegment='" + businessSegment + '\'' +
                ", cardPayment='" + cardPayment + '\'' +
                ", cardProduct='" + cardProduct + '\'' +
                ", promotionalChannel='" + promotionalChannel + '\'' +
                ", offerIDs=" + offerIDs +
                '}';
    }
}
