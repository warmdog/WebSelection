package Main;

/**
 * Created by hadoop on 17-3-16.
 */

import Function.*;
import org.json.JSONException;
import org.json.JSONObject;
import webs.Web;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hadoop on 17-3-15.
 */
public class WebSelection implements Serializable {

    public static void main(String... args) throws Exception {
        List<List<Web>> webs = new ArrayList<>();
        int aa = 1000;
        for (int i = 0; i < 10; i++) {
            if(i==0)webs.add(getWeb(aa,"/home/hadoop/Documents/fileT"+i+".json"));
            if(i==1)webs.add(getWeb(aa,"/home/hadoop/Documents/fileT"+i+".json"));
            if(i==2)webs.add(getWeb(aa,"/home/hadoop/Documents/fileT"+i+".json"));
            if(i==3)webs.add(getWeb(aa,"/home/hadoop/Documents/fileT"+i+".json"));
            if(i==4)webs.add(getWeb(aa,"/home/hadoop/Documents/fileT"+i+".json"));
            if(i==5)webs.add(getWeb(aa,"/home/hadoop/Documents/fileT"+i+".json"));
            if(i==6)webs.add(getWeb(aa,"/home/hadoop/Documents/fileT"+i+".json"));
            if(i==7)webs.add(getWeb(aa,"/home/hadoop/Documents/fileT"+i+".json"));
            if(i==8)webs.add(getWeb(aa,"/home/hadoop/Documents/fileT"+i+".json"));
            if(i==9)webs.add(getWeb(aa,"/home/hadoop/Documents/fileT"+i+".json"));
        }
        Function a = new Function(webs);
        a.setTimes(15000000);
        a.setNormalization();
        a.setNormalizedQuality_degree();
        List<List<Web>> selected = new ArrayList<>();
        selected =a.getSelectedWeb();
//        for (List<Web> pp : selected){
//            for (Web p : pp){
//                System.out.print(p.getLable()+ "  ");
//            }
//            System.out.println();
//        }

        GA  ga = new GA();
        // 1
        System.out.println("GA(PART):   ");
        long time = System.currentTimeMillis();
        ga.Inilization(selected);
        System.out.println("Running time :" + (System.currentTimeMillis()-time));
        System.out.println();
        //2

//        System.out.println("GA(ALL): ");
//        ga.Inilization(webs);

        System.out.println();
        //3
        System.out.println("the best composition of Part:");
        FindBestComposition part = new FindBestComposition(selected);
        //part.findBest();

        System.out.println();
        //4
//        System.out.println("the best of all Composition:");
//        FindBestComposition best = new FindBestComposition(webs);
//        best.findBest();
        // 5 QosDecomposition
        long tiem = System.currentTimeMillis();
        QoSDecomposition com =  new QoSDecomposition();
        com.Inilization(webs);
        System.out.println("Run time: "+(System.currentTimeMillis()-tiem));

    }


    public static List<Web> getWeb(int a , String filepath) throws JSONException {

        List<Web> Webs = new ArrayList<Web>();
        String jsonData = readFile(filepath);
        JSONObject jobj = new JSONObject(jsonData);
        Random random = new Random();
        ArrayList<Integer> list = new ArrayList<Integer>();
        //     System.out.println(jobj.getJSONArray("1").toString());
        int length = jobj.length();
        for(int i = 0; i < a; i++) {
            //  System.out.println(jobj.length());

            int number = random.nextInt(length);
            // random select web no use
            for (int j = 0; ; j++) {

                if(!list.contains(number)){
                    list.add(number);
                    break;
                }else{
                    number = random(a);
                }
            }
            String webcontent = jobj.getJSONArray(String.valueOf(i)).toString();
            String[] content = webcontent.split(",");

            String Webname = content[0].replaceAll("[\\[\\]]","").replaceAll("\"","");
            Double availability = Double.parseDouble(content[1]);
            Double response = Double.parseDouble(content[2].replaceAll("[\\[\\]]",""));
            Double throughput = Double.parseDouble(content[3].replaceAll("[\\[\\]]",""));
            Web web = new Web(response,availability,throughput,Webname);
            Webs.add(web);

        }
        return Webs;
    }

    public static int random(int size){
        Random random = new Random();
        int number = random.nextInt(size);
        return number;
    }

    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

