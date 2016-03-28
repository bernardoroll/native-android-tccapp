package com.bernardo.tccapp.view;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bernardo.tccapp.R;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Todas as imagens utilizadas neste aplicativo foram adquiridas gratuitamente, segundo os termos de
 * serviço do site pixbay.com (@see <a href=https://pixabay.com/pt/service/terms/#usage">clique aqui
 * </a> para ler os termos).
 *
 * Tamanho das imagens:
 * - 640x426
 * - 1280x853
 * - 1920x1280
 *
 */
public class InterfaceActivity extends AppCompatActivity {

    private final String TAG = "InterfaceActivity";

    private String[] mImageName = new String[] { "ford", "guitar", "skyline" };

    private final int NUMBER_OF_TESTS = 50;

    private String [] mFordImageSizes = new String [] {"118 KB", "462 KB", "1.1 MB"};
    private String [] mGuitarImageSizes = new String [] {"37 KB", "142 KB", "337 KB"};
    private String [] mSkylineImageSizes = new String [] {"103 KB", "373 KB", "795 KB"};
    private String[][] mImageSizes = new String[][] { mFordImageSizes, mGuitarImageSizes, mSkylineImageSizes};

    //private RadioGroup rgImage, rgImageSize;
    private static RadioGroup rgImage;
    private static RadioGroup rgImageSize;
    private static TextView tvFileSize;
    private static Button btnCalculate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface);

        rgImage = (RadioGroup) findViewById(R.id.activity_interface_rg_images);
        rgImageSize = (RadioGroup) findViewById(R.id.activity_interface_rg_image_size);
        tvFileSize = (TextView) findViewById(R.id.activity_interface_tv_file_size);
        btnCalculate = (Button) findViewById(R.id.activity_interface_btn_calculate);

        initialLoad();

        RadioButton rbDefaultImage = (RadioButton)
                findViewById(R.id.activity_interface_rb_image_guitar);
        RadioButton rbDefaultImageSize = (RadioButton)
                findViewById(R.id.activity_interface_rb_image_size_small);

        rbDefaultImage.setChecked(true);
        rbDefaultImageSize.setChecked(true);

    }

    private void initialLoad() {
        rgImage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rgImageChanged();
            }
        });

        rgImageSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rgImageSizeChanged();
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCalculateClicked();
            }
        });

    }

    private Drawable getSelectedDrawable(int iFile, int iFileSize) {
        switch (iFile) {
            case 0: //Ford
                switch(iFileSize) {
                    case 0: // 640
                        return getResources().getDrawable(R.drawable.ford_640, getBaseContext().getTheme());
                    case 1: // 1280
                        return getResources().getDrawable(R.drawable.ford_1280, getBaseContext().getTheme());
                    case 2: // 1920
                        return getResources().getDrawable(R.drawable.ford_1920, getBaseContext().getTheme());
                }
                break;
            case 1: //Guitar
                switch(iFileSize) {
                    case 0: // 640
                        return getResources().getDrawable(R.drawable.guitar_640, getBaseContext().getTheme());
                    case 1: // 1280
                        return getResources().getDrawable(R.drawable.guitar_1280, getBaseContext().getTheme());
                    case 2: // 1920
                        return getResources().getDrawable(R.drawable.guitar_1920, getBaseContext().getTheme());
                }
                break;
            case 2: //Skyline
                switch(iFileSize) {
                    case 0: // 640
                        return getResources().getDrawable(R.drawable.skyline_640, getBaseContext().getTheme());
                    case 1: // 1280
                        return getResources().getDrawable(R.drawable.skyline_1280, getBaseContext().getTheme());
                    case 2: // 1920
                        return getResources().getDrawable(R.drawable.skyline_1920, getBaseContext().getTheme());
                }
                break;
        }
        return null;
    }

    private int getSelectedDrawableId(int iFile, int iFileSize) {
        switch (iFile) {
            case 0: //Ford
                switch(iFileSize) {
                    case 0: // 640
                        return R.drawable.ford_640;
                    case 1: // 1280
                        return R.drawable.ford_1280;
                    case 2: // 1920
                        return R.drawable.ford_1920;
                }
                break;
            case 1: //Guitar
                switch(iFileSize) {
                    case 0: // 640
                        return R.drawable.guitar_640;
                    case 1: // 1280
                        return R.drawable.guitar_1280;
                    case 2: // 1920
                        return R.drawable.guitar_1920;
                }
                break;
            case 2: //Skyline
                switch(iFileSize) {
                    case 0: // 640
                        return R.drawable.skyline_640;
                    case 1: // 1280
                        return R.drawable.skyline_1280;
                    case 2: // 1920
                        return R.drawable.skyline_1920;
                }
                break;
        }
        return -1;
    }

    private void btnCalculateClicked() {

        View mViewGroupOptions = findViewById(R.id.activity_interface_ll_options);
        View mViewGroupImageView = findViewById(R.id.activity_interface_ll_image_view);

        mViewGroupOptions.setVisibility(View.GONE);
        mViewGroupImageView.setVisibility(View.VISIBLE);

        int iFile = getFileIndexChecked();
        int iFileSize = getFileSizeIndexChecked();
        //Drawable d = getSelectedDrawable(iFile, iFileSize);
        int id = getSelectedDrawableId(iFile, iFileSize);

        if(id >= 0) {
            //RelativeLayout rlOptions = (RelativeLayout)
            //        findViewById(R.id.activity_interface_rl_options);
            //rlOptions.setVisibility(View.GONE);



            double minTime = Double.MAX_VALUE;
            double maxTime = Double.MIN_VALUE;
            double totalTime = 0;
            for(int i = 0; i < NUMBER_OF_TESTS; i++) {
                ImageView ivImage =(ImageView) findViewById(R.id.activity_interface_iv_image);
                //ivImage.setVisibility(View.VISIBLE);

                Calendar startTime = new GregorianCalendar();
                ivImage.setImageResource(id);
                //ivImage.setImageDrawable(d);
                Calendar endTime = new GregorianCalendar();
                long totalMillis = endTime.getTimeInMillis() - startTime.getTimeInMillis();
                if (totalMillis < minTime) {
                    minTime = totalMillis;
                }
                if (totalMillis > maxTime) {
                    maxTime = totalMillis;
                }
                totalTime += totalMillis;

                try {
                    Thread.sleep(100);
                } catch (InterruptedException iEx) {
                    Log.d(TAG, "btnCalculateClicked() error: " + iEx.getMessage());
                }

                //ivImage.setVisibility(View.GONE);

            }

            //rlOptions.setVisibility(View.VISIBLE);



            double averageTimeInSeconds = (totalTime / NUMBER_OF_TESTS) / 1000;
            double shortestTimeInSeconds = minTime / 1000;
            double longestTimeInSeconds = maxTime / 1000;
            double totalTimeInSeconds = totalTime / 1000;

            double serviceMeanTime = averageTimeInSeconds;

            showResults(serviceMeanTime, averageTimeInSeconds, shortestTimeInSeconds,
                    longestTimeInSeconds, totalTimeInSeconds);

            mViewGroupImageView.setVisibility(View.GONE);
            mViewGroupOptions.setVisibility(View.VISIBLE);

        }
    }

    private void showResults(double serviceMeanTime, double averageTimeInSeconds,
                             double shortestTimeInSeconds, double longestTimeInSeconds,
                             double totalTimeInSeconds) {

        Log.d(TAG, ".showResults() called.");

        TextView tvServiceMeanTime = (TextView)
                findViewById(R.id.activity_interface_tv_service_mean_time);
        TextView tvAverageTime = (TextView)
                findViewById(R.id.activity_interface_tv_average_time);
        TextView tvShortestTime = (TextView)
                findViewById(R.id.activity_interface_tv_shortest_time);
        TextView tvLongestTime = (TextView)
                findViewById(R.id.activity_interface_tv_longest_time);
        TextView tvTotalTime = (TextView) findViewById(R.id.activity_interface_tv_total_time);
        Resources res = getResources();

        tvServiceMeanTime.setText(Html.fromHtml(String.format(res.getString(R.string.service_mean_time),
                serviceMeanTime)));
        tvAverageTime.setText(Html.fromHtml(String.format(res.getString(R.string.average_time),
                averageTimeInSeconds)));
        tvShortestTime.setText(Html.fromHtml(String.format(res.getString(R.string.shortest_time),
                shortestTimeInSeconds)));
        tvLongestTime.setText(Html.fromHtml(String.format(res.getString(R.string.longest_time),
                longestTimeInSeconds)));
        tvTotalTime.setText(Html.fromHtml(String.format(res.getString(R.string.total_time), totalTimeInSeconds)));
    }

    private void rgImageChanged() {
        showFileSize();
    }

    private void rgImageSizeChanged() {
        showFileSize();
    }

    private void showFileSize() {
        if(!getFileSize().equals("")) {
            tvFileSize.setText(String.format(getResources().getString(R.string.image_file_size),
                    getFileSize()));
           tvFileSize.setVisibility(View.VISIBLE);
        }
    }

    private String getFileSize() {

        int iFile = getFileIndexChecked();
        int iFileSize = getFileSizeIndexChecked();

        return iFile >= 0 && iFileSize >= 0 ? mImageSizes[iFile][iFileSize] : "";
/*
        if(rgImageSize.getCheckedRadioButtonId() >= 0 && rgImage.getCheckedRadioButtonId() >= 0) {
            View rbImageChecked = findViewById(rgImage.getCheckedRadioButtonId());
            View rbImageSizeChecked = findViewById(rgImageSize.getCheckedRadioButtonId());

            int indexFile = rgImage.indexOfChild(rbImageChecked);
            int indexFileSize = rgImageSize.indexOfChild(rbImageSizeChecked);

            return mImageSizes[indexFile][indexFileSize];
        }
        return "";*/
    }

    private int getFileIndexChecked() {
        if(rgImageSize.getCheckedRadioButtonId() >= 0) {
            View rbImageChecked = findViewById(rgImage.getCheckedRadioButtonId());
            return rgImage.indexOfChild(rbImageChecked);
        }
        return -1;
    }

    private int getFileSizeIndexChecked() {
        if(rgImageSize.getCheckedRadioButtonId() >= 0) {
            View rbImageSizeChecked = findViewById(rgImageSize.getCheckedRadioButtonId());
            return rgImageSize.indexOfChild(rbImageSizeChecked);
        }
        return -1;
    }

}
