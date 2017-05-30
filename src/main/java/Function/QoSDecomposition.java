package Function;

/**
 * Created by hadoop on 17/04/18.
 */

import webs.Web;
import webs.websScore;
import java.util.*;
import java.util.stream.Collectors;

import static Function.Function.quality_Degree;


public class QoSDecomposition {
    static Random random = new Random();
    static int Number = 50;
    static List<List<Web>> webs = new ArrayList<>();
    public static List<List<Web>> selectParents(List<List<Web>> webs){
        List<List<Web>> parents  = new ArrayList<>();
        for (int i = 0; i < Number; i++) {
            List<Web> father = new ArrayList<>();
            for (int j = 0; j < webs.size(); j++) {
                father.add(webs.get(j).get(GA.random.nextInt(webs.get(j).size())));
            }
            parents.add(father);
        }
        return parents;
        // pa.sort((p1,p2) ->Comparator.comparingDouble((websScore a)->a.getScore()));

    }
    public void Inilization(List<List<Web>> webs){
        this.webs = webs;
        long time =  System.currentTimeMillis();
        List<List<Web>> parents = selectParents(webs);
        double best = 0;
        List<Web> bestCimposition = new ArrayList<>();
        for (int i = 1;  ; i++) {
            List<Web> selected = CrossAndMutate(parents).get(0);
            websScore ss =  new websScore(selected);

            double se = ss.getScore();

//            if(i==1){
//                List<List<Web>> test =CrossAndMutate(parents);
//                for (int j = 0; j < test.size() ; j++) {
//                    List<Web> selected1 = CrossAndMutate(parents).get(j);
//                    websScore ss1 =  new websScore(selected1);
//                    System.out.println(ss1.getScore());
//                }
//            }
            if(se>best){
                best = se;
                bestCimposition = selected;
                System.out.println("Iteration Times: " + i + "  Score: " + best);
            }
//            if(selected.get(0).getName().equals("0Web34")&&selected.get(1).getName().equals("1Web14")&&
//                    selected.get(2).getName().equals("2Web13")&&selected.get(3).getName().equals("3Web7")){
//                break;
//            }
            if((System.currentTimeMillis()-time)>10000) break;
        }
        System.out.println("GA results:");
        for (int i = 0; i < bestCimposition.size(); i++) {
            System.out.println(bestCimposition.get(i).getName() + " " );
        }
    }
    public List<List<Web>>  CrossAndMutate(List<List<Web>> parents){

        List<List<Web>> pa = new ArrayList<>();
        for (int i = 0; i < parents.size(); i++) {
            for (int j = 0; j < parents.size(); j++) {
                if(i!=j){
                    List<Web> child = cross(parents.get(i),parents.get(j));
                    child = mutate(child);
                    double Res =0.0;
                    double Ava =0.0;
                    double Thr =0.0;
                    for (int k = 0; k < child.size() ; k++) {
                        Res += quality_Degree.get(k).get(0)[child.get(k).getQosDe()[0]-1];
                        Ava += quality_Degree.get(k).get(1)[child.get(k).getQosDe()[1]-1];
                        Thr += quality_Degree.get(k).get(2)[child.get(k).getQosDe()[2]-1];
                    }
                    Res = Res/child.size();
                   // System.out.println(Res);
                    Ava = Ava/child.size();
                    //System.out.println(Ava);
                    Thr = Thr/child.size();
                    //System.out.println(Thr);
                    if(Res>=0.40&&Ava>=0.01&&Thr>=0.01){
                        pa.add(child);
                        //System.out.println("QQQQQQQQQQQQQQQ");
                    }
                }
            }
        }
        // store Id and score value
        Map<Integer,Double> sort = new HashMap<>();
        // pa.stream().sorted(Comparator.comparingDouble((webs a) ->a.getScore()).reversed());
        for (int i = 0; i <pa.size() ; i++) {
            websScore test  = new websScore(pa.get(i));
            double socre =  test.getScore();
            sort.put(i,socre);
        }
        // soted
        Map<Integer,Double> sorted = sort.entrySet().stream().
                sorted(Map.Entry.<Integer,Double>comparingByValue().reversed()).
                limit(Number).
                collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1, e2)->e1, LinkedHashMap::new));
        Iterator<Integer> key = sorted.keySet().iterator();
        List<List<Web>> child = new ArrayList<>();
        while(key.hasNext()){
            Integer akey =key.next();
            //System.out.println(sorted.get(akey));
            child.add(pa.get(akey));
        }


        return child;
    }
    public static List<Web> cross(List<Web> father, List<Web> mother){
        double crossRate = 0.5;
        final List<Web> child = new ArrayList<>(father.size());
        for (int i = 0; i < father.size() ; i++) {
            if(Math.random()>crossRate){
                child.add(father.get(i));
            }else{
                child.add(mother.get(i));
            }
        }
        return child;
    }
    public static List<Web> mutate(List<Web> child) {
        List<Web> chi = new ArrayList<>();
        double mutateRate = 0.5;
        for (int i = 0; i < webs.size() ; i++) {
            if(Math.random()>mutateRate){
                chi.add(webs.get(i).get(GA.random.nextInt(webs.get(i).size())));
            }else{
                chi.add(child.get(i));
            }
        }
        return chi;
    }
}

