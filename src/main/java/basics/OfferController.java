package basics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class OfferController {
    
    @GetMapping("/filter")
    public ArrayList<Integer> filterOffers(String business_segment,String card_payment,String card_product,String promotional_channel) throws IOException {
        OffersDataApiCall newCall =new OffersDataApiCall();
        ArrayList<Integer> offer_ids = new ArrayList<Integer>();
        offer_ids=newCall.getRetrieveOffersByFilterHandler(business_segment,card_payment,card_product,promotional_channel);
        return offer_ids;

    }
    
    @GetMapping("/search")
    public String findNearbyMerchants() throws IOException {
        String storeID = "165833808";
        MerchantSearchCall newCall = new MerchantSearchCall();
        ArrayList<String> attributes = new ArrayList<String>();
        attributes = newCall.postMerchantSearchHandler(storeID);

        String postalCode = (attributes.get(0)).substring(0, attributes.get(0).indexOf('-'));
        String mCC = attributes.get(1);
        String city = attributes.get(2);
        System.out.println(postalCode);
        System.out.println(mCC);
        System.out.println(city);
        ArrayList<ArrayList<String>> PostalCodesMerchantIDs = getMerchantIDAndPostalCodeList(postalCode,mCC);


        ArrayList<String> uniquePostalCodes = new ArrayList<String>();
        for(int i = 0;i < PostalCodesMerchantIDs.get(0).size();i++){
            if(!uniquePostalCodes.contains(PostalCodesMerchantIDs.get(0).get(i)))
                uniquePostalCodes.add(PostalCodesMerchantIDs.get(0).get(i));
        }
        ArrayList<Double> MerchantPercentages = callMerchantMeasurement1(postalCode,mCC,uniquePostalCodes,city);

        OffersDataApiCall offerCall = new OffersDataApiCall();
        String json = offerCall.getBestOfferParameters(PostalCodesMerchantIDs,MerchantPercentages,postalCode,city);
        //json = json + "\r\n"+ "Offer"+position1+" , "+ "Offer"+position2+" , " + "Offer"+position3+" , " + "are the top three offers";
        return json;
    }

    public ArrayList<ArrayList<String>> getMerchantIDAndPostalCodeList(String postalCode,String mCC) throws IOException {
        MerchantLocatorCall newCall = new MerchantLocatorCall();
        String json = "";
        ArrayList<ArrayList<String>> merchants = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> PostalCodesMerchantIDs = new ArrayList<ArrayList<String>>();
        ArrayList<String> PostalCodes = new ArrayList<String>();
        ArrayList<String> MerchantIDs = new ArrayList<String>();
        System.out.println(mCC);
        for(int i = 1;i < 8;i++) {
            merchants = newCall.postMerchantLocatorHandler(i,postalCode,mCC);
            System.out.println(merchants);
            for(int j = 0; j < merchants.size();j++) {
                String s = merchants.get(j).get(1);
                String s1 = merchants.get(j).get(0);
                MerchantIDs.add(s1);
                int k= s.indexOf('-');
                s = s.substring(0,k);
                PostalCodes.add(s);
                json += "[ " + merchants.get(j).get(0) + " , " + merchants.get(j).get(1) + " ] ";
            }
        }
        PostalCodesMerchantIDs.add(PostalCodes);
        PostalCodesMerchantIDs.add(MerchantIDs);
        //PostalCodes.add(json);
        System.out.println(json);
        System.out.println(PostalCodesMerchantIDs);
        return PostalCodesMerchantIDs;
        //return json;
    }

    public ArrayList<Double> callMerchantMeasurement1(String postalCode, String mCC, ArrayList<String> PostalCodes, String city) throws IOException {
        Random rand = new Random();
        MerchantMeasurementCall newCall = new MerchantMeasurementCall();
        ArrayList<Double> Percentages = new ArrayList<Double>();
        for(int i=0;i<4;i++){ //manually filled should be removed later
            Percentages.add(-30.0 + (60.0)*rand.nextDouble());
        }
        return Percentages;
       /* int i = 0;
        for(i = 1; i <= 4;i++){
            double j = newCall.postMerchantBenchmarkHandler(i,postalCode,mCC,PostalCodes,city);//for postal code
            Percentages.add(j);
        }
        System.out.println(mCC);
        System.out.println(Percentages);
        //return json;
        return Percentages; */
    }
    /*
    @GetMapping("/measure")
    public String callMerchantMeasurement() throws IOException {
        MerchantMeasurementCall newCall = new MerchantMeasurementCall();
        String json = newCall.postMerchantBenchmarkHandler();
        return json;
    }*/
}
