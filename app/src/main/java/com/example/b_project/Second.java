package com.example.b_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

public class Second extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        final ImageButton btn1;
        final ViewFlipper viewFlipper;

        btn1 = (ImageButton)findViewById(R.id.btn1);
        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper1);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}





