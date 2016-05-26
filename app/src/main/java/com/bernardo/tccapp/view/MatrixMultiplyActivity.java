package com.bernardo.tccapp.view;

import android.app.ActivityManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bernardo.tccapp.R;
import com.bernardo.tccapp.util.Statistics;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class MatrixMultiplyActivity extends AppCompatActivity {

    private final String TAG = "MatrixMultiplyActivity";

    private final int NUMBER_OF_TESTS = 50;

    private int [][] randomMatrixA, randomMatrixB;
    private Button mBtnCalculate;
    private EditText mEtMatrixSize, mEtRangeValue;
    private SeekBar mSbRangeValues;

    /**
     * Overrides the onCreate({@link Bundle}) method of the super class.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_multiply);

        initialLoad();

    }

    /**
     * Initializes the variables and adds the event handlers.
     */
    private void initialLoad() {

        Log.d(TAG, ".initialLoad() called.");

        mBtnCalculate = (Button) findViewById(R.id.activity_matrix_multiply_btn_calculate);
        mEtMatrixSize = (EditText) findViewById(R.id.activity_matrix_multiply_et_matrix_size);
        mBtnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCalculateClicked();
            }
        });


    }

    /**
     * Fills the matrixes with random numbers between 0 and 100.
     * @param size the size of the square matrixes
     */
    private void fillRandomMatrix(int size) {

        Log.d(TAG, ".fillRandomMatrix() called.");

        randomMatrixA = new int[size][size];
        randomMatrixB = new int[size][size];
        double randomNumber;
        int x = 0;
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                randomNumber = new Random().nextDouble();
                randomMatrixA[i][j] = (int)(randomNumber * 100);
                randomNumber = new Random().nextDouble();
                randomMatrixB[i][j] = (int) (randomNumber * 100);
            }
        }
    }

    /**
     * Handles the click event of the button that calculates the matrixes multiplication.
     */
    private void btnCalculateClicked() {

        Log.d(TAG, ".btnCalculateClicked() called.");

        String typedText = mEtMatrixSize.getText().toString().trim();
        int size = -1;
        try {
            size = Integer.parseInt(typedText);
        } catch (Exception ex) {
            Toast.makeText(this, R.string.invalid_value, Toast.LENGTH_LONG).show();
        }
        if(size > 0) {
            fillRandomMatrix(size);
            multiplyMatrixes();
        }

    }

    /**
     * Multiplies the matrixes
     */
    public void multiplyMatrixes() {

        Log.d(TAG, ".multiplyMatrixes() called.");

        int[][] resultMatrix = new int[randomMatrixA[0].length][randomMatrixB[1].length];

        long eachExecutionTime[] = new long[NUMBER_OF_TESTS];
        long startTimestamp, endTimestamp;

        double minTime = Double.MAX_VALUE;
        double maxTime = Double.MIN_VALUE;
        double totalTime = 0;

        double totalServiceTime = 0;
        double totalOperations = 0;

        startTimestamp = System.currentTimeMillis();

        for(int q = 0; q < NUMBER_OF_TESTS; q++) {
            Calendar startTime = new GregorianCalendar();
            for(int i = 0; i < randomMatrixA[0].length; i++){
                for(int j = 0; j < randomMatrixB[1].length; j++) {
                    for(int k = 0; k < randomMatrixB[0].length; k++) {
                        //if(q > 0){
                            resultMatrix[i][j] += randomMatrixA[i][k] * randomMatrixB[i][j];
                        //} else {
                            //Calendar startServiceTime = new GregorianCalendar();
                            //resultMatrix[i][j] += randomMatrixA[i][k] * randomMatrixB[i][j];
                            //Calendar endServiceTime = new GregorianCalendar();

                            //long totalServiceMilliseconds = endServiceTime.getTimeInMillis() -
                            //        startServiceTime.getTimeInMillis();
                            //totalServiceTime += totalServiceMilliseconds;
                            //totalOperations++;
                        //}
                    }
                }
            }
            Calendar endTime = new GregorianCalendar();
            long totalMillis = endTime.getTimeInMillis() - startTime.getTimeInMillis();
            eachExecutionTime[q] = totalMillis;
            if (totalMillis < minTime) {
                minTime = totalMillis;
            }
            if (totalMillis > maxTime) {
                maxTime = totalMillis;
            }
            totalTime += totalMillis;
        }

        endTimestamp = System.currentTimeMillis();


//        for(ActivityManager.RunningAppProcessInfo rAPI : aManager.getRunningAppProcesses()) {
//            if(rAPI.pid == android.os.Process.myPid()) {
//
//
//                int [] pids = new int[]{rAPI.pid};
//
//                mi = aManager.getProcessMemoryInfo(pids);
//            }
//        }

        //double serviceMeanTime = (totalServiceTime / totalOperations) / 1000;
        double averageTimeInSeconds = (totalTime / NUMBER_OF_TESTS) / 1000;
        double shortestTimeInSeconds = minTime / 1000;
        double longestTimeInSeconds = maxTime / 1000;
        double totalTimeInSeconds = totalTime / 1000;


        double serviceMeanTime = averageTimeInSeconds;

        showResults(serviceMeanTime, averageTimeInSeconds, shortestTimeInSeconds,
                longestTimeInSeconds, totalTimeInSeconds, startTimestamp, endTimestamp,
                eachExecutionTime);

    }

    /**
     * Shows the results on screen.
     * @param serviceMeanTime
     * @param averageTimeInSeconds
     * @param shortestTimeInSeconds
     * @param longestTimeInSeconds
     * @param totalTimeInSeconds
     * @param startTimestamp
     * @param endTimestamp
     * @param eachExecutionTime
     */
    private void showResults(double serviceMeanTime, double averageTimeInSeconds,
                             double shortestTimeInSeconds, double longestTimeInSeconds,
                             double totalTimeInSeconds, long startTimestamp, long endTimestamp,
                             long[] eachExecutionTime) {

        Log.d(TAG, ".showResults() called.");

        TextView tvServiceMeanTime = (TextView)
                findViewById(R.id.activity_matrix_multiply_tv_service_mean_time);
        TextView tvAverageTime = (TextView)
                findViewById(R.id.activity_matrix_multiply_tv_average_time);
        TextView tvShortestTime = (TextView)
                findViewById(R.id.activity_matrix_multiply_tv_shortest_time);
        TextView tvLongestTime = (TextView)
                findViewById(R.id.activity_matrix_multiply_tv_longest_time);
        TextView tvTotalTime = (TextView) findViewById(R.id.activity_matrix_multiply_tv_total_time);

        TextView tvStartTimestamp = (TextView)
                findViewById(R.id.activity_matrix_multiply_tv_start_timestamp);
        TextView tvEndTimestamp = (TextView)
                findViewById(R.id.activity_matrix_multiply_tv_end_timestamp);
        TextView tvVariance = (TextView) findViewById(R.id.activity_matrix_multiply_tv_variance);
        TextView tvStandardDeviation = (TextView)
                findViewById(R.id.activity_matrix_multiply_tv_standard_deviation);

        Resources res = getResources();

        tvStartTimestamp.setText(Html.fromHtml(
                String.format(res.getString(R.string.initial_timestamp), startTimestamp)));

        tvServiceMeanTime.setText(Html.fromHtml(String.format(res.getString(R.string.service_mean_time),
                serviceMeanTime)));
        tvAverageTime.setText(Html.fromHtml(String.format(res.getString(R.string.average_time),
                averageTimeInSeconds)));
        tvShortestTime.setText(Html.fromHtml(String.format(res.getString(R.string.shortest_time),
                shortestTimeInSeconds)));
        tvLongestTime.setText(Html.fromHtml(String.format(res.getString(R.string.longest_time),
                longestTimeInSeconds)));
        tvTotalTime.setText(Html.fromHtml(String.format(res.getString(R.string.total_time),
                totalTimeInSeconds)));

        tvEndTimestamp.setText(Html.fromHtml(String.format(res.getString(R.string.final_timestamp),
                endTimestamp)));

        tvVariance.setText(Html.fromHtml(String.format(res.getString(R.string.variance),
                Statistics.getVariance(eachExecutionTime))));
        tvStandardDeviation.setText(Html.fromHtml(String.format(
                res.getString(R.string.standard_deviation),
                Statistics.getStandardDeviation(eachExecutionTime))));
    }

}
