package com.bernardo.tccapp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bernardo.tccapp.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class IOActivity extends AppCompatActivity {

    private final String TAG = "IOActivity";

    private final int NUMBER_OF_TESTS = 50;

    private enum FileOperation {
        READ,
        WRITE
    }

    private enum FileSize {
        ONE_BYTE,
        EIGHT_BYTES,
        THIRTY_TWO_BYTES,
        SIXTY_FOUR_BYTES,
        ONE_KILOBYTE,
        FOUR_KILOBYTES,
        EIGHT_KILOBYTES,
        THIRTY_TWO_KILOBYTES,
        SIXTY_FOUR_KILOBYTES,
        ONE_MEGABYTE,
        FOUR_MEGABYTES,
        EIGHT_MEGABYTES,
        THIRTY_TWO_MEGABYTES,
        SIXTY_FOUR_MEGABYTES
    }

    private final String ONE_BYTE_FILENAME = "one_byte.bin";
    private final String EIGHT_BYTES_FILENAME = "eight_bytes.bin";
    private final String THIRTY_TWO_BYTES_FILENAME = "thirty_two_bytes.bin";
    private final String SIXTY_FOUR_BYTES_FILENAME = "sixty_four_bytes.bin";
    private final String ONE_KILOBYTE_FILENAME = "one_kilobyte.bin";
    private final String FOUR_KILOBYTES_FILENAME = "four_kilobytes.bin";
    private final String EIGHT_KILOBYTES_FILENAME = "eight_kilobytes.bin";
    private final String THIRTY_TWO_KILOBYTES_FILENAME = "thirty_two_kilobytes.bin";
    private final String SIXTY_FOUR_KILOBYTES_FILENAME = "sixty_four_kilobytes.bin";
    private final String ONE_MEGABYTE_FILENAME = "one_megabyte.bin";
    private final String FOUR_MEGABYTES_FILENAME = "four_megabytes.bin";
    private final String EIGHT_MEGABYTES_FILENAME = "eight_megabytes.bin";
    private final String THIRTY_TWO_MEGABYTES_FILENAME = "thirty_two_megabytes.bin";
    private final String SIXTY_FOUR_MEGABYTES_FILENAME = "sixty_four_megabytes.bin";


    private Button btnCalculate;
    private RadioGroup rgFileOperation, rgFileSize;

    private FileOperation fileOperation;
    private FileSize fileSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io);

        initialLoad();

        RadioButton rbDefaultFileOperation = (RadioButton)
                findViewById(R.id.activity_io_rb_file_operation_read);
        RadioButton rbDefaultFileSize = (RadioButton)
                findViewById(R.id.activity_io_rb_file_8_bytes);

        rbDefaultFileOperation.setChecked(true);
        rbDefaultFileSize.setChecked(true);

    }

    private void initialLoad() {

        Log.d(TAG, ".initialLoad() called.");

        createTestFiles();

        btnCalculate = (Button) findViewById(R.id.activity_io_btn_Calculate);

        rgFileOperation = (RadioGroup) findViewById(R.id.activity_io_rg_file_operation);
        rgFileSize = (RadioGroup) findViewById(R.id.activity_io_rg_file_size);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCalculateClicked();
            }
        });

        rgFileOperation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rgFileOperationChanged(checkedId);
            }
        });

        rgFileSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rgFileSizeChanged(checkedId);
            }
        });
    }

    /**
     * Handles the file operation radio group changed event.
     * @param checkedId
     */
    private void rgFileOperationChanged(int checkedId) {
        switch (checkedId) {
            case R.id.activity_io_rb_file_operation_read:
                fileOperation = FileOperation.READ;
                break;
            case R.id.activity_io_rb_file_operation_write:
                fileOperation = FileOperation.WRITE;
                break;
            default:
                fileOperation = FileOperation.READ;
                break;
        }
    }

    /**
     * Handles the file size radio group changed event.
     * @param checkedId
     */
    private void rgFileSizeChanged(int checkedId) {
        switch (checkedId) {
            case R.id.activity_io_rb_file_1_byte:
                fileSize = FileSize.ONE_BYTE;
                break;
            case R.id.activity_io_rb_file_8_bytes:
                fileSize = FileSize.EIGHT_BYTES;
                break;
            case R.id.activity_io_rb_file_32_bytes:
                fileSize = FileSize.THIRTY_TWO_BYTES;
                break;
            case R.id.activity_io_rb_file_64_bytes:
                fileSize = FileSize.SIXTY_FOUR_BYTES;
                break;
            case R.id.activity_io_rb_file_1_kilobyte:
                fileSize = FileSize.ONE_KILOBYTE;
                break;
            case R.id.activity_io_rb_file_4_kilobytes:
                fileSize = FileSize.FOUR_KILOBYTES;
                break;
            case R.id.activity_io_rb_file_8_kilobytes:
                fileSize = FileSize.EIGHT_KILOBYTES;
                break;
            case R.id.activity_io_rb_file_32_kilobytes:
                fileSize = FileSize.THIRTY_TWO_KILOBYTES;
                break;
            case R.id.activity_io_rb_file_64_kilobytes:
                fileSize = FileSize.SIXTY_FOUR_KILOBYTES;
                break;
            case R.id.activity_io_rb_file_1_megabyte:
                fileSize = FileSize.ONE_MEGABYTE;
                break;
            case R.id.activity_io_rb_file_4_megabytes:
                fileSize = FileSize.FOUR_MEGABYTES;
                break;
            case R.id.activity_io_rb_file_8_megabytes:
                fileSize = FileSize.EIGHT_MEGABYTES;
                break;
            case R.id.activity_io_rb_file_32_megabytes:
                fileSize = FileSize.THIRTY_TWO_MEGABYTES;
                break;
            case R.id.activity_io_rb_file_64_megabytes:
                fileSize = FileSize.SIXTY_FOUR_MEGABYTES;
                break;
            default:
                fileSize = FileSize.EIGHT_BYTES;
                break;
        }
    }

    /**
     * Handles the calculate button clicked event.
     */
    private void btnCalculateClicked() {

        Log.d(TAG, ".btnCalculateClicked() called.");

        try {
            if (fileOperation == FileOperation.READ) {
                readOperationTest();
            } else {
                writeOperationTest();
            }
        } catch(Exception ex) {
            Resources res = getResources();
            new AlertDialog.Builder(this)
                    .setTitle(R.string.error)
                    .setMessage(String.format(res.getString(R.string.error_io_operation),
                            fileOperation == FileOperation.READ ? R.string.reading : R.string.writing))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    private void readOperationTest() {
        Log.d(TAG, ".readOperationTest() called.");
        double minTime = Double.MAX_VALUE;
        double maxTime = Double.MIN_VALUE;
        double totalTime = 0;

        long eachExecutionTime[] = new long[NUMBER_OF_TESTS];
        //double totalServiceTime = 0;

        long startTimestamp = System.currentTimeMillis();

        for(int i = 0; i < NUMBER_OF_TESTS; i++) {

            Calendar startTime = new GregorianCalendar();
            readFile(fileSize);
            Calendar endTime = new GregorianCalendar();
            long totalMillis = endTime.getTimeInMillis() - startTime.getTimeInMillis();

            eachExecutionTime[i] = totalMillis;

            if(totalMillis < minTime) {
                minTime = totalMillis;
            }
            if(totalMillis > maxTime) {
                maxTime = totalMillis;
            }

            totalTime += totalMillis;
        }

        long endTimestamp = System.currentTimeMillis();

        // Neste caso o tempo médio de serviço é igual ao tempo médio.
        //double serviceMeanTime = (totalServiceTime / totalOperations) / 1000;
        double averageTimeInSeconds = (totalTime / NUMBER_OF_TESTS) / 1000;
        double shortestTimeInSeconds = minTime / 1000;
        double longestTimeInSeconds = maxTime / 1000;
        double totalTimeInSeconds = totalTime / 1000;

        showResults(averageTimeInSeconds, shortestTimeInSeconds, longestTimeInSeconds,
                totalTimeInSeconds);

    }

    /**
     * Executes the I/O test writing a file. It's length depends on the
     * {@link com.bernardo.tccapp.view.IOActivity.FileSize} selected.
     */
    private void writeOperationTest() {
        Log.d(TAG, ".writeOperationTest() called.");

        double minTime = Double.MAX_VALUE;
        double maxTime = Double.MIN_VALUE;
        double totalTime = 0;
        //double totalServiceTime = 0;
        //double totalOperations = 0;


        for(int i = 0; i < NUMBER_OF_TESTS; i++) {

            Calendar startTime = new GregorianCalendar();
            writeFile(getTextBySize(fileSize), fileSize);
            Calendar endTime = new GregorianCalendar();
            long totalMillis = endTime.getTimeInMillis() - startTime.getTimeInMillis();

            if(totalMillis < minTime) {
                minTime = totalMillis;
            }
            if(totalMillis > maxTime) {
                maxTime = totalMillis;
            }

            totalTime += totalMillis;
        }

        // Neste caso o tempo médio de serviço é igual ao tempo médio.
        //double serviceMeanTime = (totalServiceTime / totalOperations) / 1000;
        double averageTimeInSeconds = (totalTime / NUMBER_OF_TESTS) / 1000;
        double shortestTimeInSeconds = minTime / 1000;
        double longestTimeInSeconds = maxTime / 1000;
        double totalTimeInSeconds = totalTime / 1000;

        showResults(averageTimeInSeconds, shortestTimeInSeconds, longestTimeInSeconds,
                totalTimeInSeconds);
    }

    private void readFile(FileSize fSize) {
        BufferedReader br = null;
        String path = getFilesDir().getAbsolutePath() + "/";
        String fileText = "";
        try {
            br = new BufferedReader(new FileReader(path + getFileNameBySize(fSize)));
            String line = "";
            while((line = br.readLine()) != null){
                fileText += line;
            }
        } catch(FileNotFoundException fEx) {
            Log.d(TAG, "readFile() error: " + fEx.getMessage());
        } catch(IOException ioEx) {
            Log.d(TAG, "readFile() error: " + ioEx.getMessage());
        }
    }

    private void writeFile(byte[] data, FileSize fSize) {
        FileOutputStream fOut;
        File file;
        String path = getFilesDir().getAbsolutePath() + "/";
        try {
            file = new File(path + getFileNameBySize(fSize));
            if(!file.exists()) {
                file.createNewFile();
                fOut = new FileOutputStream(file);
                fOut.write(data);
                fOut.close();
            }
        }catch(FileNotFoundException fEx) {
            Log.d(TAG, ".writeFile() error: " + fEx.getMessage());
        }catch(IOException ioEx) {
            Log.d(TAG, ".writeFile() error: " + ioEx.getMessage());
        }
    }

    private void createTestFiles() {
        Log.d(TAG, "createTestFiles() called.");
        FileOutputStream fOut;
        File file;
        String path = getFilesDir().getAbsolutePath() + "/";
        try {
            file = new File(path + ONE_BYTE_FILENAME);
            if(!file.exists()) {
                writeFile(getTextBySize(FileSize.ONE_BYTE), FileSize.ONE_BYTE);
            } else if(file.length() != 1) {
                writeFile(getTextBySize(FileSize.ONE_BYTE), FileSize.ONE_BYTE);
            }
            file = new File(path + EIGHT_BYTES_FILENAME);
            if(!file.exists()) {
                writeFile(getTextBySize(FileSize.EIGHT_BYTES),FileSize.EIGHT_BYTES);
            } else if(file.length() != 8) {
                writeFile(getTextBySize(FileSize.EIGHT_BYTES), FileSize.EIGHT_BYTES);
            }
            file = new File(path + THIRTY_TWO_BYTES_FILENAME);
            if(!file.exists()) {
                writeFile(getTextBySize(FileSize.THIRTY_TWO_BYTES), FileSize.THIRTY_TWO_BYTES);
            } else if(file.length() != 32) {
                writeFile(getTextBySize(FileSize.THIRTY_TWO_BYTES), FileSize.THIRTY_TWO_BYTES);
            }
            file = new File(path + SIXTY_FOUR_BYTES_FILENAME);
            if(!file.exists()) {
                writeFile(getTextBySize(FileSize.SIXTY_FOUR_BYTES), FileSize.SIXTY_FOUR_BYTES);
            } else if(file.length() != 64) {
                writeFile(getTextBySize(FileSize.SIXTY_FOUR_BYTES), FileSize.SIXTY_FOUR_BYTES);
            }
            file = new File(path + ONE_KILOBYTE_FILENAME);
            if(!file.exists()) {
                writeFile(getTextBySize(FileSize.ONE_KILOBYTE), FileSize.ONE_KILOBYTE);
            } else if(file.length() != 1024) {
                writeFile(getTextBySize(FileSize.ONE_KILOBYTE), FileSize.ONE_KILOBYTE);
            }
            file = new File(path + FOUR_KILOBYTES_FILENAME);
            if(!file.exists()) {
                writeFile(getTextBySize(FileSize.FOUR_KILOBYTES), FileSize.FOUR_KILOBYTES);
            } else if(file.length() != 4096) {
                writeFile(getTextBySize(FileSize.FOUR_KILOBYTES), FileSize.FOUR_KILOBYTES);
            }
            file = new File(path + EIGHT_KILOBYTES_FILENAME);
            if(!file.exists()) {
                writeFile(getTextBySize(FileSize.EIGHT_KILOBYTES), FileSize.EIGHT_KILOBYTES);
            } else if(file.length() != 8192) {
                writeFile(getTextBySize(FileSize.EIGHT_KILOBYTES), FileSize.EIGHT_KILOBYTES);
            }
            file = new File(path + THIRTY_TWO_KILOBYTES_FILENAME);
            if(!file.exists()) {
                writeFile(getTextBySize(FileSize.THIRTY_TWO_KILOBYTES),
                        FileSize.THIRTY_TWO_KILOBYTES);
            } else if(file.length() != (32 * 1024)) {
                writeFile(getTextBySize(FileSize.THIRTY_TWO_KILOBYTES),
                        FileSize.THIRTY_TWO_KILOBYTES);
            }
            file = new File(path + SIXTY_FOUR_KILOBYTES_FILENAME);
            if(!file.exists()) {
                writeFile(getTextBySize(FileSize.SIXTY_FOUR_KILOBYTES),
                        FileSize.SIXTY_FOUR_KILOBYTES);
            } else if(file.length() != (64 * 1024)){
                writeFile(getTextBySize(FileSize.SIXTY_FOUR_KILOBYTES),
                        FileSize.SIXTY_FOUR_KILOBYTES);
            }
            file = new File(path + ONE_MEGABYTE_FILENAME);
            if(!file.exists()) {
                writeFile(getTextBySize(FileSize.ONE_MEGABYTE), FileSize.ONE_MEGABYTE);
            } else if(file.length() != (1024 * 1024)) {
                writeFile(getTextBySize(FileSize.ONE_MEGABYTE), FileSize.ONE_MEGABYTE);
            }
            file = new File(path + FOUR_MEGABYTES_FILENAME);
            if(!file.exists()) {
                writeFile(getTextBySize(FileSize.FOUR_MEGABYTES), FileSize.FOUR_MEGABYTES);
            } else if(file.length() != (4 * 1024 * 1024)) {
                writeFile(getTextBySize(FileSize.FOUR_MEGABYTES), FileSize.FOUR_MEGABYTES);
            }
            file = new File(path + EIGHT_MEGABYTES_FILENAME);
            if(!file.exists()) {
                writeFile(getTextBySize(FileSize.EIGHT_MEGABYTES), FileSize.EIGHT_MEGABYTES);
            } else if(file.length() != (8 * 1024 * 1024))
                file = new File(path + THIRTY_TWO_MEGABYTES_FILENAME);
            if(!file.exists()) {
                writeFile(getTextBySize(FileSize.THIRTY_TWO_MEGABYTES),
                        FileSize.THIRTY_TWO_MEGABYTES);
            } else if(file.length() != (32 * 1024 * 1024)) {
                writeFile(getTextBySize(FileSize.THIRTY_TWO_MEGABYTES),
                        FileSize.THIRTY_TWO_MEGABYTES);
            }
            file = new File(path + SIXTY_FOUR_MEGABYTES_FILENAME);
            if(!file.exists()) {
                writeFile(getTextBySize(FileSize.SIXTY_FOUR_MEGABYTES),
                        FileSize.SIXTY_FOUR_MEGABYTES);
            } else if(file.length() != (64 * 1024 * 1024)) {
                writeFile(getTextBySize(FileSize.SIXTY_FOUR_MEGABYTES),
                        FileSize.SIXTY_FOUR_MEGABYTES);
            }
        }catch(Exception ex) {
            Log.d(TAG, "createTestFiles() error: " + ex.getMessage());
        }
    }

    private String getFileNameBySize(FileSize fSize) {
        switch (fSize) {
            case ONE_BYTE:
                return ONE_BYTE_FILENAME;
            case EIGHT_BYTES:
                return EIGHT_BYTES_FILENAME;
            case THIRTY_TWO_BYTES:
                return THIRTY_TWO_BYTES_FILENAME;
            case SIXTY_FOUR_BYTES:
                return SIXTY_FOUR_BYTES_FILENAME;
            case ONE_KILOBYTE:
                return ONE_KILOBYTE_FILENAME;
            case FOUR_KILOBYTES:
                return FOUR_KILOBYTES_FILENAME;
            case EIGHT_KILOBYTES:
                return EIGHT_KILOBYTES_FILENAME;
            case THIRTY_TWO_KILOBYTES:
                return THIRTY_TWO_KILOBYTES_FILENAME;
            case SIXTY_FOUR_KILOBYTES:
                return SIXTY_FOUR_KILOBYTES_FILENAME;
            case ONE_MEGABYTE:
                return ONE_MEGABYTE_FILENAME;
            case FOUR_MEGABYTES:
                return FOUR_MEGABYTES_FILENAME;
            case EIGHT_MEGABYTES:
                return EIGHT_MEGABYTES_FILENAME;
            case THIRTY_TWO_MEGABYTES:
                return THIRTY_TWO_MEGABYTES_FILENAME;
            case SIXTY_FOUR_MEGABYTES:
                return SIXTY_FOUR_MEGABYTES_FILENAME;
            default:
                return EIGHT_BYTES_FILENAME;
        }
    }

    private byte[] getTextBySize(FileSize fSize) {
        switch(fSize) {
            case ONE_BYTE:
                return fillArrayOfBytes(1);
            case EIGHT_BYTES:
                return fillArrayOfBytes(8);
            case THIRTY_TWO_BYTES:
                return fillArrayOfBytes(32);
            case SIXTY_FOUR_BYTES:
                return fillArrayOfBytes(64);
            case ONE_KILOBYTE:
                return fillArrayOfBytes(1024);
            case FOUR_KILOBYTES:
                return fillArrayOfBytes(4096);
            case EIGHT_KILOBYTES:
                return fillArrayOfBytes(8192);
            case THIRTY_TWO_KILOBYTES:
                return fillArrayOfBytes(32768);
            case SIXTY_FOUR_KILOBYTES:
                return fillArrayOfBytes(65536);
            case ONE_MEGABYTE:
                return fillArrayOfBytes(1024 * 1024);
            case FOUR_MEGABYTES:
                return fillArrayOfBytes(4096 * 1024);
            case EIGHT_MEGABYTES:
                return fillArrayOfBytes(8192 * 1024);
            case THIRTY_TWO_MEGABYTES:
                return fillArrayOfBytes(32768 * 1024);
            case SIXTY_FOUR_MEGABYTES:
                return fillArrayOfBytes(65536 * 1024);
            default:
                return fillArrayOfBytes(8);
        }
    }

    private byte[] fillArrayOfBytes(int size) {
        byte[] byteArray = new byte[size];
        for(int i = 0; i < size; i++) {
            byteArray[i] = 'A';
        }
        return byteArray;
    }

    private void showResults(double averageTimeInSeconds, double shortestTimeInSeconds,
                             double longestTimeInSeconds, double totalTimeInSeconds) {
        Log.d(TAG, ".showResults() called.");
        TextView tvServiceMeanTime = (TextView) findViewById(R.id.activity_io_tv_service_mean_time);
        TextView tvAverageTime = (TextView) findViewById(R.id.activity_io_tv_average_time);
        TextView tvShortestTime = (TextView) findViewById(R.id.activity_io_tv_shortest_time);
        TextView tvLongestTime = (TextView) findViewById(R.id.activity_io_tv_longest_time);
        TextView tvTotalTime = (TextView) findViewById(R.id.activity_io_tv_total_time);

        Resources res = getResources();

        tvServiceMeanTime.setText(Html.fromHtml(String.format(res.getString(R.string.service_mean_time),
                averageTimeInSeconds)));
        tvServiceMeanTime.setText(Html.fromHtml(String.format(res.getString(R.string.service_mean_time),
                averageTimeInSeconds)));
        tvAverageTime.setText(Html.fromHtml(String.format(res.getString(R.string.average_time),
                averageTimeInSeconds)));
        tvShortestTime.setText(Html.fromHtml(String.format(res.getString(R.string.shortest_time),
                shortestTimeInSeconds)));
        tvLongestTime.setText(Html.fromHtml(String.format(res.getString(R.string.longest_time),
                longestTimeInSeconds)));
        tvTotalTime.setText(Html.fromHtml(String.format(res.getString(R.string.total_time), totalTimeInSeconds)));



    }

}
