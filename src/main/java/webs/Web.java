package webs;

import java.io.Serializable;

import static Function.Function.usesPreferenc;

/**
 * Created by hadoop on 17-3-16.
 */

public class Web implements Serializable {
        private  final double responsetime;
        private  final double availability;
        private  final double throughput;
        private  final String name;
        private int lable;
        private int[] QosDe = new int[3];
        private double[] normalization = new double[3];
        public Web(double res, double ava, double thr,String ne){
            this.responsetime = res;
            this.availability = ava;
            this.throughput = thr;
            this.name = ne;
        }
        public Web(Web web){
            this.availability = web.getAvailability();
            this.responsetime = web.getResponsetime();
            this.throughput = web.getThroughput();
            this.name = web.getName();
            this.normalization = web.getNormalization().clone();

        }
        public void setLable(int s){
            lable = s;
        }
        public int getLable(){
            return lable;
        }
         public void setNormalization(double[] nor){
             normalization = nor.clone();
         }


        public   double getUtility(){
            return  usesPreferenc[0]* normalization[0] + usesPreferenc[1] * normalization[1] + usesPreferenc[2] * normalization[2];
        }
        public void setQosDe(int[] qo){
            QosDe = qo;
        }
        public int[] getQosDe(){
            return QosDe;
        }
        public  String getName(){
            return name;
        }
        public double getResponsetime(){
            return responsetime;
        }
        public double getAvailability(){
            return availability;
        }
        public double getThroughput(){
            return throughput;
        }
        public  double[] getNormalization(){return normalization ;}
}


