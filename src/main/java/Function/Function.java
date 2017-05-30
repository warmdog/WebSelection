package Function;

import webs.Web;

import java.io.Serializable;
import java.util.*;

/**
 * Created by hadoop on 17-3-17.
 */
public class Function implements Serializable {
    public static double[] usesPreferenc = {0.8,0.1,0.1};
    private static List<List<Web>> webs = new ArrayList<>();
    private  List<List<Web>> selectedWeb =  new ArrayList<>();
    private  List<double[]> maxAndmin = new ArrayList<>();
    private  List<Integer> numberofClass = new ArrayList<>();
    public static List<List<Double[]>> quality_Degree =  new ArrayList<>();
    private static int calculation ;
    public Function(List<List<Web>> webs) {
        this.webs = webs;
    }

    public static List<Integer> getNumberClass(){
        int numberOfClass = webs.size();
        List<Integer> results = new ArrayList<Integer>();


        int num = (int)Math.pow(calculation,1.0/numberOfClass)+1 ;
        System.out.println("number of selected services:  " +num);
        for (int i = 0; i < numberOfClass ; i++) {
            if(webs.get(i).size() >= num) {results.add(num);}
            else { results.add(webs.get(i).size());}
        }
        return results;
    }
    public void setTimes(int c){calculation =c;}
    public List<List<Web>>  getSelectedWeb(){
        for (int i = 0; i < webs.size(); i++) {
            int number  =  numberofClass.get(i);
            List<Web> part = new ArrayList<>();
            webs.get(i).sort(Comparator.comparing(Web::getLable));
            for (int j = 0; j < number; j++) {
                if(j<webs.get(i).size()){
                    part.add(webs.get(i).get(j));
                }

            }
            selectedWeb.add(part);
        }
        return selectedWeb;
    }

    public  void setNormalization(){
        numberofClass = getNumberClass();
        for (int i = 0; i < webs.size() ; i++) {
            int selected = numberofClass.get(i);
            int numbers  = webs.get(i).size();
            int level = numbers/selected;
            if(level<10){
                level =10;
            }

            double maxResponse = webs.get(i).stream().max(Comparator.comparing(Web ::getResponsetime)).get().getResponsetime();
            double minResponse = webs.get(i).stream().min(Comparator.comparing(Web ::getResponsetime)).get().getResponsetime();
            double difRes = maxResponse - minResponse;

            double maxAva = webs.get(i).stream().max((a,b) ->Double.compare(a.getAvailability(),b.getAvailability())).get().getAvailability();
            double minAva = webs.get(i).stream().min((a,b) ->Double.compare(a.getAvailability(),b.getAvailability())).get().getAvailability();
            double difAva = maxAva - minAva;

            double maxThr = webs.get(i).stream().max(Comparator.comparing((Web p)  -> p.getThroughput())).get().getThroughput();
            double minThr = webs.get(i).stream().min(Comparator.comparing((Web p)  -> p.getThroughput())).get().getThroughput();
            double difThr = maxThr - minThr;

            double[] maxandmin = {difRes,difAva,difThr};
            maxAndmin.add(maxandmin);
            System.out.println("Level:  "+level);
            for (int j = 0; j < numbers ; j++) {
                Web aa = webs.get(i).get(j);
                double res = webs.get(i).get(j).getResponsetime();
                double ava = webs.get(i).get(j).getAvailability();
                double thr = webs.get(i).get(j).getThroughput();

                double Nres =(maxResponse-res)/difRes;
                double Nava = -1*(maxAva - ava)/difAva +1;
                double Nthr = -1*(maxThr - thr)/difThr +1;
                double[] bb = {Nres,Nava,Nthr};
                aa.setNormalization(bb);

                int Lres = level -(int)(Nres * level);
                int Lava = level -(int)(Nava * level);
                int Lthr = level -(int)(Nthr * level);
                int L = setLable(Lres, Lava, Lthr);
                aa.setLable(L);

              //  if(L==1) System.out.println(aa.getResponsetime()+"  "+ aa.getAvailability()+"  " + aa.getThroughput() +"  "+ aa.getName());
            }
        }
    }

    public static int setLable(int Lres, int Lava, int Lthr){
       int l = Math.max(Math.max(Lres,Lava),Lthr);
        if(Lres == Lava && Lres == Lthr && Lres ==0){
            l =1;
        }
        return l;

    }

    public void setNormalizedQuality_degree(){
        List<Double> cla = new ArrayList<>();
        for (int i = 0; i < webs.size(); i++) {
            List<Web> part =  webs.get(i);
            // more less more better
            double maxResponse = part.stream().max(Comparator.comparing(Web ::getResponsetime)).get().getResponsetime();
            double minResponse = part.stream().min(Comparator.comparing(Web ::getResponsetime)).get().getResponsetime();
            double difRes = (maxResponse - minResponse)/3;
            double[] resArray = {minResponse, minResponse+difRes, minResponse+2*difRes,maxResponse};

            double maxAva = part.stream().max((a,b) ->Double.compare(a.getAvailability(),b.getAvailability())).get().getAvailability();
            double minAva = part.stream().min((a,b) ->Double.compare(a.getAvailability(),b.getAvailability())).get().getAvailability();
            double difAva = (maxAva - minAva)/3;
            double[] avaArray = {minAva, minAva+difAva, minAva+2*difAva,maxAva};

            double maxThr = part.stream().max(Comparator.comparing((Web p)  -> p.getThroughput())).get().getThroughput();
            double minThr = part.stream().min(Comparator.comparing((Web p)  -> p.getThroughput())).get().getThroughput();
            double difThr = (maxThr - minThr)/3;
            double[] thrArray = {minThr,minThr + difThr, minThr + 2*difThr, maxThr};
            // degree level is 4
            int all = webs.get(i).size();

            int[] resNumber = {0,0,0,0};
            int[] avaNumber = {0,0,0,0};
            int[] thrNumber = {0,0,0,0};


            for (int j = 0; j < part.size() ; j++) {
                Web one =  part.get(j);
                double res = one.getResponsetime();
                double ava =  one.getAvailability();
                double thr =  one.getThroughput();
                // set OosDe
                int[] QosDe = new int[3];
                QosDe[0] = res<=resArray[0]? 1: res<=resArray[1]? 2: res<=resArray[2]?3:4;
                QosDe[1] = ava<=avaArray[0]? 1: ava<=avaArray[1]? 2: ava<=avaArray[2]?3:4;
                QosDe[2] = thr<=thrArray[0]? 1: thr<=thrArray[1]? 2: thr<=thrArray[2]?3:4;
                one.setQosDe(QosDe);
                //negative count number
                if(res <= resArray[0]){
                    resNumber[0]++;
                }if(res<= resArray[1]){ resNumber[1]++;}
                if(res<= resArray[2]) {resNumber[2]++;}
                if(true){
                    resNumber[3]++;
                }

                //positive
                if(true){
                    avaNumber[0]++;
                }
                if(ava >= avaArray[1]){avaNumber[1]++;}
                if(ava >= avaArray[2]) {avaNumber[2]++;}
                if(ava >= avaArray[3]) {avaNumber[3]++;}

                //positive
                if(true){
                    thrNumber[0]++;
                }
                if(thr >= thrArray[1]){ thrNumber[1]++;}
                if(thr >= thrArray[2]) {thrNumber[2]++;}
                if(thr >= thrArray[3]) {thrNumber[3]++;}
            }
            double[] Res = new double[4];
            double[] Ava = new double[4];
            double[] Thr = new double[4];
            for (int j = 0; j < 4 ; j++) {
                Res[j] = resNumber[j]/(double)all;
                Ava[j] = avaNumber[j]/(double)all;
                Thr[j] = thrNumber[j]/(double)all;
            }
            //level  4
            Double[] resDegree = new Double[4];
            Double[] avaDegree = new Double[4];
            Double[] thrDegree = new Double[4];
            for (int j = 0; j < 4 ; j++) {
                //negative quality
                resDegree[j] = (maxResponse + difRes - minResponse- j * difRes)/(maxResponse + difRes - minResponse) * Res[j];
                //positive quality
                avaDegree[j] = (minAva + difAva * j - minAva +difAva)/(maxAva-minAva+difAva) * Ava[j];
                thrDegree[j] = (minThr + difThr *j -minThr + difThr)/(maxThr - minThr + difThr) * Thr[j];
            }
            //
            List<Double[]> oneclass =  new ArrayList<>();
            oneclass.add(resDegree);
            oneclass.add(avaDegree);
            oneclass.add(thrDegree);

            quality_Degree.add(oneclass);

        }
//        for(List<Double[]> pp : quality_Degree){
//            for(Double[] p : pp){
//                for (int i = 0; i < p.length; i++) {
//                    System.out.print(p[i]+ "  ");
//                }
//                System.out.println();
//            }
//            System.out.println();
//            System.out.println();
//        }

    }


}
