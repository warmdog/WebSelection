package Main;

/**
 * Created by hadoop on 17-3-16.
 */

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by hadoop on 17-3-16.
 */

/**
 * Computes an approximation to pi
 * Usage: JavaSparkPi [slices]
 */
public final class JavaSparkPi {

    public static void main(String[] args) throws Exception {
//        SparkConf conf = new SparkConf()
//                .setAppName("JavaSparkPi")
//                .setMaster("yarn")
//                .set("spark.driver.host", "163.143.92.188");
        deleinfo();
        SparkSession spark = SparkSession
                .builder().appName("JavaSparkPi")
                .getOrCreate();

        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        int slices = (args.length == 1) ? Integer.parseInt(args[0]) : 2;
        int n = 100000 * slices;
        List<Integer> l = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            l.add(i);
        }

        JavaRDD<Integer> dataSet = jsc.parallelize(l, slices);

        int count = dataSet.map(integer -> {
            double x = Math.random() * 2 - 1;
            double y = Math.random() * 2 - 1;
            return (x * x + y * y <= 1) ? 1 : 0;
        }).reduce((integer, integer2) -> integer + integer2);

        System.out.println("Pi is roughly " + 4.0 * count / n);
        JavaRDD<String> lines = jsc.textFile("data.txt");
        JavaPairRDD<String, Integer> pairs = lines.mapToPair(s -> new Tuple2(s, 1));
        JavaPairRDD<String, Integer> counts = pairs.reduceByKey((a, b) -> a + b);

        spark.stop();
    }
    public static void deleinfo(){
        //Logger.getLogger("org.apache.spark").setLevel(Level.OFF);
        //Logger.getLogger("org.apache.log4j").setLevel(Level.OFF);
        Logger.getLogger("kafka.utils").setLevel(Level.OFF);
        Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF);
        Logger.getLogger("com.github.fommil.netlib").setLevel(Level.OFF);
    }
}

