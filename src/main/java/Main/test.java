package Main;

import org.apache.commons.math3.distribution.LogNormalDistribution;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by hadoop on 17/03/30.
 */
public class test {

    public static void main(String[] args){
        Scanner test = new Scanner(System.in);
//        while(test.hasNext()){
//            String a = test.next();
//            System.out.println(a);
//        }
        System.out.println(updateBits(256,8,2,5));
        System.out.println(100.0/(double)1000);
        System.out.println(checkPerfectNumber(6));
        for (int i = 0; i < 1; i++) {
            System.out.println(Math.exp(gussian(10,20)));
        }
        int[] a ={2,0+22,0};
        a[2]++;
        if (a[2] > 0){ a[0]++;}
         if (a[2] >=1) {a[1]++;}

        for (int i = 0; i <a.length ; i++) {
            System.out.println(a[i]);
        }
    }
    public static double gussian(int mean,int std){
        Random random = new Random();
        Double mu = 50.0;
        Double sigma = 1.0;
        LogNormalDistribution logNormal = new LogNormalDistribution(sigma, mu);
        System.out.println("Random Value : " + logNormal.sample());
        System.out.println("Random Value : " + logNormal.sample());
        return  random.nextGaussian()*std +mean;
    }
    public static boolean checkPerfectNumber(int num) {
        if (num <= 0) {
            return false;
        }
        int sum = 0;
        for (int i = 1; i * i <= num; i++) {
            if (num % i == 0) {
                sum += i; System.out.println(sum);
                System.out.println(i);
                if (i * i != num) {
                    sum += num / i;
                }

            }
        }

        return sum - num == num;
    }
    public static int updateBits(int n,int m,int i,int j){
        int max = ~0;
        System.out.println(Integer.toBinaryString(max));
        int left =  max-((1<<j)-1);
        int right = ((1<<i) -1 );
        System.out.println(Integer.toBinaryString(left));
        System.out.println(Integer.toBinaryString(right));
        int mask = left |right;
        System.out.println(Integer.toBinaryString(mask));
        return (n & mask) |(m<<i);
    }

}

