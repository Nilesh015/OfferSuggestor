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
        ArrayList<ArrayList<String>> PostalCodesMerchantIDs = callMerchantLocator(postalCode,mCC);
        //System.out.println("hello after locator");
        //System.out.println(json);
        ArrayList<Double> MerchantPercentages = callMerchantMeasurement1(postalCode,mCC,PostalCodesMerchantIDs.get(0),city);
        ArrayList<ArrayList<Integer>> MerchantCountOffers = new ArrayList<ArrayList<Integer>>(); //replace with function call later
        Random rand = new Random();
        double[] ScorePoints = new double[20];
        double[] PriorityPoints = new double[4];
        PriorityPoints[0]=.9;
        PriorityPoints[1]=.7;
        PriorityPoints[2]=.5;
        PriorityPoints[3]=.2;
        for(int i=0;i<20;i++){
            ArrayList<Integer> temp= new ArrayList<>();
            int some = 0;
            for(int j=0;j<4;j++){
                int k= rand.nextInt(10);
                some = some + k;
                temp.add(some);
            }
            ScorePoints[i]=0;
            MerchantCountOffers.add(temp);
        }
        for(int i=0;i<20;i++){
            for(int j=0;j<4;j++){
                ScorePoints[i]=ScorePoints[i]+MerchantCountOffers.get(i).get(j)*PriorityPoints[j]*(1+(MerchantPercentages.get(j)/25));
            }
        }
        double first, second, third;
        third = first = second = -10000;
        for (int i = 0; i < 20 ; i ++)
        {
            /* If current element is greater than first*/
            if (ScorePoints[i] > first)
            {
                third = second;
                second = first;
                first = ScorePoints[i];
            }

            /* If arr[i] is in between first and second then update second  */
            else if (ScorePoints[i] > second)
            {
                third = second;
                second = ScorePoints[i];
            }

            else if (ScorePoints[i] > third)
                third = ScorePoints[i];
        }
        int position1=0,position2=0,position3=0;
        for(int i=0;i<20;i++){
            if(ScorePoints[i]==first) position1=i;
            else if(ScorePoints[i]==second) position2=i;
            else if(ScorePoints[i]==third) position3=i;
        }
        System.out.println("Offer"+position1+" , "+ "Offer"+position2+" , " + "Offer"+position3+" , " + "are the top three offers");


        String json = parameterRecommender(PostalCodesMerchantIDs.get(1),mCC);
        json = json + "\r\n"+ "Offer"+position1+" , "+ "Offer"+position2+" , " + "Offer"+position3+" , " + "are the top three offers";
        return json;
    }

    public ArrayList<ArrayList<String>> callMerchantLocator(String postalCode,String mCC) throws IOException {
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
                if(!PostalCodes.contains(s)) {
                    PostalCodes.add(s);
                }
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

    public String parameterRecommender(ArrayList<String> MerchantIDs,String mCC) throws IOException {
        OffersDataApiCall newCall = new OffersDataApiCall();
        //   MerchantLocatorCall merchCall = new MerchantLocatorCall();
        //   ArrayList<ArrayList<String>> merchants = new ArrayList<ArrayList<String>>();
        ArrayList<Integer> merchantIDs = new ArrayList<Integer>();
        for(int i = 0; i < MerchantIDs.size();i++){
            //      merchants = merchCall.postMerchantLocatorHandler(i,postalCode,mCC);
            //      for(int j = 0; j < merchants.size();j++) {
            //          int merchantID = Integer.parseInt(merchants.get(j).get(0));
            int merchantID = Integer.parseInt(MerchantIDs.get(i));
            merchantIDs.add(merchantID);
        }

        String json = newCall.getBestOfferParameters(merchantIDs);
        return json;
    }
}