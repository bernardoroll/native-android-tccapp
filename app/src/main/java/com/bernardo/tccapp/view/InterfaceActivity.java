package com.bernardo.tccapp.view;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bernardo.tccapp.R;


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

    private Drawable[] mFordDrawables;
    private Drawable[] mGuitarDrawables;
    private Drawable[] mSkylineDrawables;
    private Drawable[][] mDrawables;


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
        //fillDrawableArrays();
    }

    private void fillDrawableArrays () {
        Resources res = getResources();
        mFordDrawables = new Drawable[] {
                res.getDrawable(R.drawable.ford_640, getBaseContext().getTheme()),
                res.getDrawable(R.drawable.ford_1280, getBaseContext().getTheme()),
                res.getDrawable(R.drawable.ford_1920, getBaseContext().getTheme())};
        mGuitarDrawables = new Drawable[] {
                res.getDrawable(R.drawable.guitar_640, getBaseContext().getTheme()),
                res.getDrawable(R.drawable.guitar_1280, getBaseContext().getTheme()),
                res.getDrawable(R.drawable.guitar_1920, getBaseContext().getTheme())};
        mSkylineDrawables = new Drawable[] {
                res.getDrawable(R.drawable.skyline_640, getBaseContext().getTheme()),
                res.getDrawable(R.drawable.skyline_1280, getBaseContext().getTheme()),
                res.getDrawable(R.drawable.skyline_1920, getBaseContext().getTheme())};

        mDrawables = new Drawable[][] {mFordDrawables, mGuitarDrawables, mSkylineDrawables};

    }

    private Drawable getSelectedFile() {
        Drawable d;

        return d;
    }

    private void btnCalculateClicked() {

        int iFile = getFileIndexChecked();
        int iFileSize = getFileSizeIndexChecked();

        if(iFile >= 0 && iFileSize >= 0) {
            Drawable d = mDrawables[iFile][iFileSize];

            for(int i = 0; i < NUMBER_OF_TESTS; i++) {

                ImageView ivImage =(ImageView) findViewById(R.id.activity_interface_iv_image);
                ivImage.setImageDrawable(d);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException iEx) {
                    Log.d(TAG, "btnCalculateClicked() error: " + iEx.getMessage());
                }
            }
        }


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
