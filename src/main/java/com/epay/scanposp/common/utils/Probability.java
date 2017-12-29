package com.epay.scanposp.common.utils;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Maven Dependence
 * <dependency>
 * <groupId>org.apache.commons</groupId>
 * <artifactId>commons-math3</artifactId>
 * <version>3.6.1</version>
 * </dependency>
 * Created by DreamInSun on 2016/8/26.
 */
public class Probability {

    private static Random g_rand = null;

    private static NormalDistribution g_NormalDist = new NormalDistribution(0, 1);

    private static Random getRandom() {
        if (null == g_rand) {
            g_rand = new Random();
        }
        return g_rand;
    }

    /*========== Static Export Function ==========*/

    /**
     * @return
     */
    public static Boolean generateResult(double valve) {
        double rand = Probability.getRandom().nextGaussian();
        double probability = g_NormalDist.cumulativeProbability(rand);
        System.out.println("Rand: " + Double.toString(rand) + "\tProbability: " + Double.toString(probability));
        return (probability < valve);
    }

    public static int getProbabilityRange(List rangeList) {
        double rand = Probability.getRandom().nextGaussian();
        double probability = g_NormalDist.cumulativeProbability(rand);
        int idx = 0;
        for(int i=0;i<rangeList.size();i++){
        	Map<String, Object> resmap=(Map<String, Object>)rangeList.get(i);
            String rate= String.valueOf(resmap.get("rate"));
        	if(rate==null||"".equals(rate)||"null".equals(rate)){
        		rate="0";
            }
            System.out.println("probability："+probability+"--"+"rate："+rate);
            double valuenew=Double.valueOf(rate);
        	 if (probability > valuenew) {
                 probability -=valuenew;
                 idx++;
             } else {
                 break;
             }
        }
        return idx;
    }

    public static void main(String[] args) {
        /*
        int total = 7000000, cnt = 0;
        for (int i = 0; i <= total; i++) {
            Boolean result = Probability.generateResult(0.005);
            if (result) {
                cnt++;
            }
        }
        System.out.println("Total: " + Integer.toString(total) + "\t count: " + cnt);
        System.out.println("Result Probability: " + Double.toString((double) cnt / (double) total));
        */
        int total = 100000, cnt1 = 0, cnt2 = 0;
//        List lis=[{isrepeat=0, awardProbability=0.00300000, amountRemain=2, awardID=fed9faa2-cadd-4010-b6cd-7eea0d1e1e32, awardName=一等奖, prizeName=笔记本电脑, lockVersion=0},
//                  {isrepeat=0, awardProbability=0.00500000, amountRemain=5, awardID=fed9faa2-cadd-4010-b6cd-7eea0d1e1e33, awardName=二等奖, prizeName=手机, lockVersion=0}, {isrepeat=0, awardProbability=0.00700000, amountRemain=8, awardID=fed9faa2-cadd-4010-b6cd-7eea0d1e1e34, awardName=三等奖, prizeName=ipad, lockVersion=0}];
        // List lis rangeMap = {0.005, 0.008};
        for (int i = 0; i <= total; i++) {
            int result = Probability.getProbabilityRange(null);
            if (result == 1) {
                cnt1++;
            } else if (result == 2) {
                cnt2++;
            }
        }
        System.out.println("Total: " + Integer.toString(total) + "\t count1: " + cnt1 + "\t count2: " + cnt2);
        System.out.println("Result 1 Probability: " + Double.toString((double) cnt1 / (double) total));
        System.out.println("Result 2 Probability: " + Double.toString((double) cnt2 / (double) total));
    }
}
