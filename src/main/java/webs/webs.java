package webs;

import java.util.List;

/**
 * Created by hadoop on 17/03/30.
 */
public class webs {
    public static List<Web> webs ;
    static int maxDuration;
    public webs(List<Web> aa, int time){
        webs =aa;
        maxDuration = time;
    }
    public webs(List<Web> aa){
        webs =aa;
    }
    public  static double getScore(){
        double sum =webs.stream().mapToDouble(Web::getUtility).sum();
        return sum;
    }
    public List<Web> getWebs(){
        return webs;
    }

}
