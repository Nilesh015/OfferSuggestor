package basics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class OfferController {
    @GetMapping("/search")
    public String findNearbyMerchants() throws IOException {
        String storeID = "165833808";
        MerchantSearchCall newCall = new MerchantSearchCall();
        ArrayList<String> attributes = new ArrayList<String>();
        attributes = newCall.postMerchantSearchHandler(storeID);
        String postalCode = attributes.get(0);
        String mCC = attributes.get(1);
        String json = callMerchantLocator(postalCode,mCC);
        return json;
    }

    public String callMerchantLocator(String postalCode,String mCC) throws IOException {
        MerchantLocatorCall newCall = new MerchantLocatorCall();
        String json = "";
        ArrayList<ArrayList<String>> merchants = new ArrayList<ArrayList<String>>();
        for(int i = 1;i < 10;i++) {
            merchants = newCall.postMerchantLocatorHandler(i,postalCode,mCC);
            for(int j = 0; j < merchants.size();j++) {
                json += "[ " + merchants.get(j).get(0) + " , " + merchants.get(j).get(1) + " ] ";
            }
        }
        return json;
    }
    @GetMapping("/measure")
    public String callMerchantMeasurement() throws IOException {
        MerchantMeasurementCall newCall = new MerchantMeasurementCall();
        String json = newCall.postMerchantBenchmarkHandler();
        return json;
    }
}