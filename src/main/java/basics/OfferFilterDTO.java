package basics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferFilterDTO {

    private String businessSegment;
    private String cardPayment;
    private String cardProduct;
    private String promotionalChannel;

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

    @Override
    public String toString() {
        return "OfferFilterDTO{" +
                "businessSegment='" + businessSegment + '\'' +
                ", cardPayment='" + cardPayment + '\'' +
                ", cardProduct='" + cardProduct + '\'' +
                ", promotionalChannel='" + promotionalChannel + '\'' +
                '}';
    }
}
