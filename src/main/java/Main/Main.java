package Main;
import java.util.*;

/**
 * Created by 18868 on 2017/3/31.
 * Microsoft programming test
 * 微软2017年预科生计划在线编程笔试
 */
public class Main {


    public static void main(String[] args) {
        System.out.println();
        List<List<Integer>> sum =  new ArrayList<List<Integer>>();
        List<Integer> part =new ArrayList<Integer>();
        part.add(9);
        part.add(1);
        List<Integer> part1 =new ArrayList<Integer>();
        part1.add(6);
        part1.add(3);
        part1.add(1);
        List<Integer> part2 =new ArrayList<Integer>();
        part2.add(4);
        part2.add(2);
        part2.add(1);
        part2.add(3);
        sum.add(part);
        sum.add(part1);
        sum.add(part2);
        System.out.println(leastBricks(sum));
    }
    public static String reverseWords(String s) {
        String results ="";
        String[] part = s.split(" ");
        for (int i = 0; i < part.length ; i++) {
            int length = part[i].length();
            String ss ="";
            while(length>0){
                ss = ss+part[i].charAt(length-1);
                length--;
            }
            if(i!=part.length-1)
                results +=ss +" ";
        }
        return  results;
    }
    public static int leastBricks(List<List<Integer>> wall) {
        Integer max =0,total=0;
        List<List<Integer>> sum =  new ArrayList<List<Integer>>();
        for (int i = 0; i < wall.size(); i++) {
            List<Integer> part = new ArrayList<Integer>();
            if(wall.get(i).size() ==1){
                total++;
            }
            for (int j = 0; j <wall.get(i).size()-1 ; j++) {

                part.add(part.stream().mapToInt(x->x).sum() + wall.get(i).get(j));
            }
            sum.add(part);
        }
        Map<Integer,Integer> results = new HashMap<Integer, Integer>();
        for (int i = 0; i <sum.size() ; i++) {
            for (int j = 0; j < sum.get(i).size(); j++) {
                if(results.containsKey(sum.get(i).get(j))){
                    results.put(sum.get(i).get(j),results.get(sum.get(i).get(j))+1);
                }else{
                    results.put(sum.get(i).get(j),1);
                }
            }
        }

        System.out.println(results.values().stream().min(Integer::compare).get());
        for(Map.Entry<Integer,Integer> entry:results.entrySet()){
            if(entry.getValue()>max){
                max = entry.getValue();
            }
        }
        if(total == wall.size()) return total;
        return wall.size()-max;
    }
}