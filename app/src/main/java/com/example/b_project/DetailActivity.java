package com.example.b_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by KONG on 2019-06-10.
 */

public class DetailActivity extends AppCompatActivity {
    private ListView listView;
    private Custom_Adapter adapters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String poster_path = intent.getStringExtra("poster_path");
        String overview = intent.getStringExtra("overview");
        final String original_title =intent.getStringExtra("original_title");
        ImageButton btnInsert= (ImageButton) findViewById(R.id.btnInsert);
        listView=findViewById(R.id.coment_list);
        final String id = MyApp.getInstance().getText();
        final EditText edtMovieMemo=(EditText)findViewById(R.id.edtMovieMemo);
        final RatingBar rtbMovieStar=(RatingBar)findViewById(R.id.rtbMovieStar);
        adapters = new Custom_Adapter(this, //위치
                R.layout.comment_listview, //디자인
                new ArrayList<commentInfo>()); //데이터
        listView.setAdapter(adapters);
        final TextView textView_title = (TextView)findViewById(R.id.tv_title);
        textView_title.setText(title);
        ImageView imageView_poster = (ImageView) findViewById(R.id.iv_poster);
        Glide.with(this)
                .load(poster_path)
                .centerCrop()
                .crossFade()
                .into(imageView_poster);

        TextView textView_overview = (TextView)findViewById(R.id.tv_overview);
        textView_overview.setText(overview);

        edtMovieMemo.setText(MyApp.getInstance().getRearcomment());
        rtbMovieStar.setRating(Float.parseFloat(MyApp.getInstance().getRearrating()));
        new NetworkSelect((Custom_Adapter) listView.getAdapter()).execute(intent.getStringExtra("original_title"));
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment=edtMovieMemo.getText().toString();
                String rating = ""+rtbMovieStar.getRating();
                try {
                    String result;
                    CommentImport task = new CommentImport();
                    result = task.execute(original_title,id,comment,rating).get();
                    MyApp.getInstance().setRearrating("3.0");
                    MyApp.getInstance().setRearmoviename("");
                    MyApp.getInstance().setRearcomment("");
                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            }catch (Exception e) {
                Log.i("DBComment", "ERROR");
            }
                finish();
            }
        });
    }
}