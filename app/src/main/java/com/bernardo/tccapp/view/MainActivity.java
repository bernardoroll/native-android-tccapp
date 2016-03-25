package com.bernardo.tccapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bernardo.tccapp.R;

public class MainActivity extends AppCompatActivity {

    private Button mBtnMatrixMultiply, mBtnIoTest, mBtnInterfaceTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setButtonsListenersEvents();

    }

    private void setButtonsListenersEvents() {
        mBtnInterfaceTest = (Button) findViewById(R.id.activity_main_btn_interface_test);
        mBtnIoTest = (Button) findViewById(R.id.activity_main_btn_io_test);
        mBtnMatrixMultiply = (Button) findViewById(R.id.activity_main_btn_matrixes_multiplication);

        mBtnMatrixMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnMatrixMultiplyListener();
            }
        });

        mBtnInterfaceTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnInterfaceTestListener();
            }
        });

        mBtnIoTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnIoTestListener();
            }
        });

    }

    private void setBtnMatrixMultiplyListener() {
        Intent matrixMultiplyIntent = new Intent(this, MatrixMultiplyActivity.class);
        startActivity(matrixMultiplyIntent);
    }

    private void setBtnInterfaceTestListener() {
        Intent interfaceTest = new Intent(this, InterfaceActivity.class);
        startActivity(interfaceTest);
    }

    private void setBtnIoTestListener() {
        Intent ioTest = new Intent(this, IOActivity.class);
        startActivity(ioTest);
    }
}
