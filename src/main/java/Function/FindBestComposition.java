package Function;

import webs.Web;
import webs.websScore;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadoop on 17/03/29.
 */
public class FindBestComposition {
    private static List<List<Web>> webs = new ArrayList<>();

    public FindBestComposition(List<List<Web>> webs) {
        this.webs = webs;
    }

    public  double findBest() {
        double Maxscore = 0;
        double finalScore = 0;
        long time = System.currentTimeMillis();
        int kind = webs.size();
        if (kind == 1) {
            List<Web> finall = new ArrayList<>();
            for (int i = 0; i < webs.get(0).size(); i++) {
                // get every web list
                List<Web> web = new ArrayList<>(1);
                web.add(webs.get(0).get(i));

                //
                websScore listscore = new websScore(web);
                double score = listscore.getScore();
                //System.out.println(i +"  " + web.get(0).getName() + " "+ score);
                if (score > Maxscore) {
                    //if(!finall.isEmpty()){
                    finall.clear();
                    finall.add(new Web(web.get(0)));

                    Maxscore = score;
                }
            }
            System.out.println(finall.get(0).getName() + " " + "Maxscore = " + Maxscore);
            System.out.println("Running time :" + (System.currentTimeMillis() - time));
        } else {
            List<Web> finall = new ArrayList<>();
            List<Integer> row = new ArrayList<>(kind);
            int num = 1;
            BigInteger all = BigInteger.valueOf(num);
            // get the number of all compositions
            for (int i = 0; i < kind; i++) {
                BigInteger single = BigInteger.valueOf(webs.get(i).size());
                all = all.multiply(single);
                row.add(webs.get(i).size());
            }
            System.out.println(" all Composition:" + all);
            BigInteger ii;
            for (ii = BigInteger.valueOf(1); ii.compareTo(all) < 0; ii = ii.add(BigInteger.valueOf(1))) {
                List<Web> web = new ArrayList<>(kind);
                List<Integer> composition = getComposition(row, ii);
                for (int j = 0; j < composition.size(); j++) {
                    web.add( webs.get(j).get(composition.get(j)));
                }
                websScore listscore = new websScore(web);
                double score = listscore.getScore();
                if (score > Maxscore) {
                    //if(!finall.isEmpty()){
                    finall.clear();
                    for (int j = 0; j < web.size(); j++) {
                        finall.add(new Web(web.get(j)));
                    }
                    //         System.out.println("The better score:" +score);
                    // }
                    //System.out.println(score);
                    Maxscore = score;
                }
            }
            for (int i = 0; i < finall.size(); i++) {
                System.out.println(finall.get(i).getName() + " " );

            }
            System.out.println(" " + "Maxscore = " + Maxscore);
            System.out.println("Running time :" + (System.currentTimeMillis() - time));
        }
        return Maxscore;
    }
    public List<Integer> getComposition(List<Integer> all,BigInteger a){
        List<Integer> select = new ArrayList<>();
        BigInteger total =BigInteger.valueOf(1);
        int size = all.size();
        List<Integer> getComposition  = new ArrayList<>();
        select.add(all.get(all.size()-1));
        for (int i = (all.size()-1); i >=0; i--) {
            total =  total.multiply(BigInteger.valueOf(all.get(i)));

        }
        if(! (a.compareTo(BigInteger.valueOf(select.get(0))) >0)){
            for (int j = 0; j < all.size(); j++) {
                if(j<all.size()-1){
                    getComposition.add(0);
                }else{
                    int b  = a.intValue();
                    getComposition.add(b-1);
                }
            }
            return getComposition;
        }else{

            List<Integer> select1 = new ArrayList<>(size);
            if(a.compareTo(total)>0) {
                List<Integer> bb = new ArrayList<>();
                bb.add(404);
                return bb;
            }
            if(a.compareTo(total)==0){
                for (int i = 0; i <all.size(); i++) {
                    select1.add(all.get(i)-1);
                }
                return select1;
            }
            int i =size-1;
            BigInteger x = a.remainder(BigInteger.valueOf(all.get(i)));
            Integer row = x.intValue();
            if (row==0){
                select1.add(all.get(i)-1);
            }else{
                int b = row-1;
                select1.add (b);
            }
            BigInteger num = getNum(a,all.get(i));
            int row2 =0;
            int row3 =0;
            int row4 =0;// for while
            while(!(num.compareTo(BigInteger.valueOf(all.get(i-1)))<0)){
                row3 =1;

                if( num.compareTo(BigInteger.valueOf(all.get(i-1)))==0&& row ==0 && row4==1){
                    num=BigInteger.ZERO;
                    select1.add(all.get(i-1)-1);
                    break;
                }
                int row1 =getRow(num,all.get(i-1));
                if(row ==0&row1==0){
                    select1.add(all.get(i-1)-1);
                    row2=1;
                }else if(row ==0&& row1!=0){
                    select1.add(row1-1);
                    row4 =1;
                    row=1;
                }
                else{
                    select1.add(row1);
                }
                num =getNum(num,all.get(i-1));
                i--;
                if(i<0){
                    break;
                }

            }
            // for 4 5 7 8
            if(row!=0 ){
                int d = getRow(num,all.get(i-1));
                select1.add(d);
            }
            //for 9 18
            int d = getRow(num,all.get(i-1));
            if( row==0 && row2==1 ){

                select1.add(d-1);
            }
            // in case 3*3*3  for 6

            if( row==0 && row2==0 && row3==0){

                select1.add(d-1);

            }
            if( row==0 && row2==0 && row3==1){

                select1.add(d);
            }

            while(size > select1.size()){
                Integer add = 0;
                select1.add(add);
                //System.out.println("j" +0);
            }
//            // select1.add(99);
            List<Integer> copy =  new ArrayList<>(select1.size());
            for (int j = select1.size()-1; j>=0;j--) {
                copy.add(select1.get(j));
            }
            return copy;
        }
    }
    public BigInteger getNum(BigInteger a, Integer b){
        BigInteger c =BigInteger.valueOf(b);
        BigInteger d = a.divide(c);
        return d;
    }
    public Integer getRow(BigInteger a, Integer b){
        BigInteger d = a.remainder(BigInteger.valueOf(b));
        int c = d.intValue();
        return c;
    }
}
