package basics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OfferItem {
    @JsonProperty("offerId")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String offerId = null;

    @JsonProperty("offerTitle")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String offerTitle = null;

    @JsonProperty("offerDesc")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String offerDesc = null;

    @JsonProperty("validFrom")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String validFrom = null;

    @JsonProperty("validTo")
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String validTo = null;

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getOfferDesc() {
        return offerDesc;
    }

    public void setOfferDesc(String offerDesc) {
        this.offerDesc = offerDesc;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }
}
