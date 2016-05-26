package com.bernardo.tccapp.util;

/**
 * Created by bernardorochasalgueiro on 21/05/16.
 * A class with some statistics functions.
 */
public final class Statistics {

    private Statistics() { }

    /**
     * Gets the standard deviation of a set of values.
     * @param arr array of long
     * @return the standard deviation
     */
    public static double getStandardDeviation(long[] arr) {
        return Math.sqrt(getVariance(arr));
    }

    /**
     * Gets the variance of a set of values.
     * @param arr array of long
     * @return the variance
     */
    public static double getVariance(long [] arr) {
        double avg = getAverage(arr);
        double sum = 0;
        for(long value : arr) {
            sum += Math.pow((value - avg), 2);
        }
        return sum / arr.length;
    }

    /**
     * Gets the average of a set of values.
     * @param arr array of long
     * @return the average
     */
    public static double getAverage(long[] arr) {
        long sum = 0;
        for(long value : arr) {
            sum += value;
        }
        return sum / arr.length;
    }

}
