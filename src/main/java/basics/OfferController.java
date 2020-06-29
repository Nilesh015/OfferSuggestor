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
        //System.out.println(attributes.size());
        String postalCode = attributes.get(0);
        String mCC = attributes.get(1);
        String city = attributes.get(2);
        System.out.println(postalCode);
        System.out.println(mCC);
        System.out.println(city);
        ArrayList<String> PostalCodes = callMerchantLocator(postalCode,mCC);
        //System.out.println("hello after locator");
        //System.out.println(json);
        ArrayList<Double> json1 = callMerchantMeasurement1(postalCode,mCC,PostalCodes,city);
        return json1.toString();
    }

    public ArrayList<String> callMerchantLocator(String postalCode,String mCC) throws IOException {
        MerchantLocatorCall newCall = new MerchantLocatorCall();
        String json = "";
        ArrayList<ArrayList<String>> merchants = new ArrayList<ArrayList<String>>();
        ArrayList<String> PostalCodes = new ArrayList<String>();
        System.out.println(mCC);
        for(int i = 1;i < 10;i++) {
            merchants = newCall.postMerchantLocatorHandler(i,postalCode,mCC);
            System.out.println(merchants);
            for(int j = 0; j < merchants.size();j++) {
                String s = merchants.get(j).get(1);
                int k= s.indexOf('-');
                s = s.substring(0,k);
                if(!PostalCodes.contains(s)) {
                    PostalCodes.add(s);
                }
                //json += "[ " + merchants.get(j).get(0) + " , " + merchants.get(j).get(1) + " , "+ merchants.get(j).get(2) +  " , "+ merchants.get(j).get(3) +" ] ";
            }
        }
        //PostalCodes.add(json);
        System.out.println(PostalCodes);
        return PostalCodes;
        //return json;
    }

    public ArrayList<Double> callMerchantMeasurement1(String postalCode, String mCC, ArrayList<String> PostalCodes, String city) throws IOException {
        MerchantMeasurementCall newCall = new MerchantMeasurementCall();
        ArrayList<Double> Percentages = new ArrayList<Double>();
        int i = 0;
        for(i = 1; i <= 4;i++){
            double j = newCall.postMerchantBenchmarkHandler(i,postalCode,mCC,PostalCodes,city);//for postal code
            Percentages.add(j);
        }
        System.out.println(mCC);
        System.out.println(Percentages);
        //return json;
        return Percentages;
    }

    /*
    @GetMapping("/measure")
    public String callMerchantMeasurement() throws IOException {
        MerchantMeasurementCall newCall = new MerchantMeasurementCall();
        String json = newCall.postMerchantBenchmarkHandler();
        return json;
    }*/

    @GetMapping("/vmorc")
    public String callVMORC() throws IOException {
        OffersDataApiCall newCall = new OffersDataApiCall();
        String json = newCall.getBestOfferParameters();
        return json;
    }
}