package com.example.b_project;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

public class login extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginform);

        final EditText edtID = findViewById(R.id.edtID);
        final EditText edtPW = findViewById(R.id.edtPW);
        CheckBox chkIdSave =findViewById(R.id.chkIdSave);
        Button btnRg= findViewById(R.id.btnRg);
        Button btnLg= findViewById(R.id.btnLg);
        ImageButton btnLclose = findViewById(R.id.btnLclose);

        btnRg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), register.class);
                startActivity(in); //회원가입 페이지 인텐트 호출
            }
        });
        btnLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String id = edtID.getText().toString();
                    String pw = edtPW.getText().toString();
                    String result;

                    LoginActivity task = new LoginActivity();
                    result = task.execute(id,pw).get();
                    if (result.equals(id)){
                    Intent intent = new Intent();
                    intent.putExtra("result_msg", id);
                    setResult(RESULT_OK, intent);
                    finish();}
                    else{
                        finish();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        btnLclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
