package webs;

import java.util.List;

/**
 * Created by hadoop on 17/03/29.
 */
public class websScore {
    public static List<Web> webs ;
    static int maxDuration;
    public websScore(List<Web> aa, int time){
        webs =aa;
        maxDuration = time;
    }
    public websScore(List<Web> aa){
        webs =aa;
    }
    public  double getScore(){
        double sum =webs.stream().mapToDouble(Web::getUtility).sum();
        return sum;
    }
    public  double getScore1(){
        double sum =webs.stream().mapToDouble(Web::getUtility).sum();
        return sum;
    }
    public static double getScore(List<Web> webs){
        double sum =webs.stream().mapToDouble(Web::getUtility).sum();
        return sum;
    }

}
