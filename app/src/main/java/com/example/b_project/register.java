package com.example.b_project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class register extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ImageButton btnCancel= findViewById(R.id.btnCancel);
        ImageButton btnRegist= findViewById(R.id.btnRegist);
        final EditText RegiEdtId =findViewById(R.id.RegiEdtId);
        final EditText RegiEdtPw = findViewById(R.id.RegiEdtPw);
        final EditText RegiEdtNicname=findViewById(R.id.RegiEdtNicname);
        final EditText RegiEdtEmail = findViewById(R.id.RegiEdtEmail);
        final RadioGroup rgGender=findViewById(R.id.rgGender);

        final EditText RegiEdtAge = findViewById(R.id.RegiEdtAge);
        final Spinner RegiGenre = findViewById(R.id.RegiGenre);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //로그인 화면으로 돌아감
            }
        });
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String Gender ="";
                    String result;
                    String id = RegiEdtId.getText().toString();
                    String pw = RegiEdtPw.getText().toString();
                    String nicname = RegiEdtNicname.getText().toString();
                    String email = RegiEdtEmail.getText().toString();
                    switch (rgGender.getCheckedRadioButtonId()){
                        case R.id.rdoMan:
                            Gender ="남자";
                            break;
                        case R.id.rdoWoman:
                            Gender="여자";
                            break;
                    }
                    String age = RegiEdtAge.getText().toString();
                    String genre = RegiGenre.getSelectedItem().toString();
                    RegisterActivity task = new RegisterActivity();
                    result = task.execute(id,pw,nicname,email,Gender,age,genre).get();
                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                }catch (Exception e) {
                    Log.i("DBregister", "ERROR");
                }
                finish();
           }
        });
    }
}
