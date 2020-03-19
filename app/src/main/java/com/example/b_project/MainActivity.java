package com.example.b_project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    //리스트 뷰 변수
    Button btnlogin, btnsidelogin,btnsidelogout,btnsideHOME;
    LinearLayout LinearLogin, LinearLogout,LoginLayout,LogoutLayout;
    TextView txt1;
    ImageView img,img1;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    ArrayList<Movie> movieList;
    //슬라이드 메뉴 리스트뷰
    String[] menu={"액션","애니","코미디","범죄","판타지","공포",};
    ListView listmenu;
    ViewFlipper flipper;

    //리스트 뷰 임의의 값 넣어둠
    int REQUEST_ACT =10;
    String id="";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!=RESULT_OK){
            btnlogin.setVisibility(View.VISIBLE);
            LinearLogin.setVisibility(View.VISIBLE);
            LinearLogout.setVisibility(View.INVISIBLE);
            LoginLayout.setVisibility(View.INVISIBLE);
            LogoutLayout.setVisibility(View.VISIBLE);
            id="";
            MyApp.getInstance().setText(id);
            return;
        }
        if (requestCode == REQUEST_ACT){
            String resultMsg = data.getStringExtra("result_msg");
            id=resultMsg;
            btnlogin.setVisibility(View.INVISIBLE);
            LinearLogout.setVisibility(View.VISIBLE);
            LinearLogin.setVisibility(View.INVISIBLE);
            LoginLayout.setVisibility(View.VISIBLE);
            LogoutLayout.setVisibility(View.INVISIBLE);
            txt1.setText(id+" 님");
            MyApp.getInstance().setText(id);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnlogin=findViewById(R.id.btnlogin);
        btnsidelogin=findViewById(R.id.btnsidelogin);
        LinearLogin=findViewById(R.id.LinearLogin);
        LinearLogout=findViewById(R.id.LinearLogout);
        txt1=findViewById(R.id.txt1);
        btnsidelogout=findViewById(R.id.btnsidelogout);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        movieList = new ArrayList<Movie>();
        LoginLayout=findViewById(R.id.LoginLayout);
        LogoutLayout=findViewById(R.id.logoutLayout);
        btnsideHOME=findViewById(R.id.btnsideHOME);

        listmenu=(ListView)findViewById(R.id.listmenu);
        listmenu.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menu));

        int[] img2={R.drawable.cgv,R.drawable.lotte,R.drawable.megabox};
        //뷰 플리퍼
        flipper=(ViewFlipper)findViewById(R.id.flipper);
        Animation show= AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
        flipper.setInAnimation(show);
        flipper.setFlipInterval(3000);
        flipper.startFlipping();
        flipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (flipper.getDisplayedChild()){
                    case 0:
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cgv.co.kr"));
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.lottecinema.co.kr"));
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.megabox.co.kr"));
                        startActivity(intent2);
                        break;
                    default: break;
                }
                return true;
            }
        });



        //Asynctask - OKHttp

        String all = "https://api.themoviedb.org/3/movie/upcoming?api_key=f32f8cf42496810a68d546d9a66f1085&language=ko-KR&page=1";
        String[] strings = {all};
        MyAsyncTask mAsyncTask = new MyAsyncTask();
        mAsyncTask.execute(strings[0]);

        //LayoutManager
        //recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)) ;

        /*btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sch = edt1.getText().toString();
                //sch.replace(" ","+");

                String url;

                if (sch.equals(" ") && sch.equals("")){
                    url = "https://api.themoviedb.org/3/movie/upcoming?api_key=f32f8cf42496810a68d546d9a66f1085&language=ko-KR&page=1";
                    String[] strings = {url};
                    MyAsyncTask mAsyncTask = new MyAsyncTask();
                    mAsyncTask.execute(strings[0]);
                }else {
                    url = "https://api.themoviedb.org/3/search/movie?api_key=f32f8cf42496810a68d546d9a66f1085&query="+sch+"&language=ko-KR&page=1";
                    String[] strings = {url};
                    MyAsyncTask mAsyncTask = new MyAsyncTask();
                    mAsyncTask.execute(strings[0]);
                }

            }
        });*/


        //사이드 슬라이드 옵션 바 시작
        final DrawerLayout drawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
        //슬라이드 뷰
        final View drawerView=(View)findViewById(R.id.side);

        //드로어 화면을 열고 닫을 버튼
        ImageButton btnopen=(ImageButton)findViewById(R.id.btnopen);
        ImageButton btnclose=(ImageButton)findViewById(R.id.btnclose);
        btnopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
            }
        });
        //사이드 슬라이드 옵션 바 끝

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getApplicationContext(),login.class);
                startActivityForResult(in,REQUEST_ACT);

            }
        });
        btnsidelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),login.class);
                startActivityForResult(in,REQUEST_ACT);
            }
        });


        btnsidelogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActivityResult(1,1,null);
                drawerLayout.closeDrawer(drawerView);
            }
        });

        listmenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String all="";
                String[] strings={""};
                MyAsyncTask mAsyncTask = new MyAsyncTask();
                switch (position) {
                    case 0:
                        all = "https://api.themoviedb.org/3/discover/movie?api_key=f32f8cf42496810a68d546d9a66f1085&with_genres=28&language=ko-KR";
                        strings = new String[]{all};
                        break;
                    case 1:
                        all = "https://api.themoviedb.org/3/discover/movie?api_key=f32f8cf42496810a68d546d9a66f1085&with_genres=16&language=ko-KR";
                        strings = new String[]{all};
                        break;
                    case 2:
                        all = "https://api.themoviedb.org/3/discover/movie?api_key=f32f8cf42496810a68d546d9a66f1085&with_genres=35&language=ko-KR";
                        strings = new String[]{all};
                        break;
                    case 3:
                        all = "https://api.themoviedb.org/3/discover/movie?api_key=f32f8cf42496810a68d546d9a66f1085&with_genres=80&language=ko-KR";
                        strings = new String[]{all};
                        break;
                    case 4:
                        all = "https://api.themoviedb.org/3/discover/movie?api_key=f32f8cf42496810a68d546d9a66f1085&with_genres=14&language=ko-KR";
                        strings = new String[]{all};
                        break;
                    case 5:
                        all = "https://api.themoviedb.org/3/discover/movie?api_key=f32f8cf42496810a68d546d9a66f1085&with_genres=27&language=ko-KR";
                        strings = new String[]{all};
                        break;
                }
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                mAsyncTask.execute(strings[0]);
                drawerLayout.closeDrawer(drawerView);
            }
        });

        btnsideHOME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String all = "https://api.themoviedb.org/3/movie/upcoming?api_key=f32f8cf42496810a68d546d9a66f1085&language=ko-KR&page=1";
                String[] strings = {all};
                MyAsyncTask mAsyncTask = new MyAsyncTask();
                mAsyncTask.execute(strings[0]);

                //LayoutManager
                //recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false)) ;
                drawerLayout.closeDrawer(drawerView);
            }
        });

        img=(ImageView)findViewById(R.id.img);
        img1=(ImageView)findViewById(R.id.img1);
        final String[] vArray={"한국영화","외국영화","음악영화"};

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg=new AlertDialog.Builder(MainActivity.this);
                dlg.setItems(vArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i)
                        {
                            case 0:
                                Intent in=new Intent(getApplicationContext(),Back.class);
                                startActivity(in);
                                break;
                            case 1:
                                Intent in1=new Intent(getApplicationContext(),Back1.class);
                                startActivity(in1);
                                break;
                            case 2:
                                Intent in2=new Intent(getApplicationContext(),Back2.class);
                                startActivity(in2);
                                break;
                        }
                    }
                });
                dlg.setNegativeButton("확인",null);
                dlg.show();

            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),Second.class);
                startActivity(in);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("영화제목을 입력하세요.");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            //검색어를 다 입력하고 서치 버튼을 눌렀을때
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MainActivity.this, s + "에 대한 영화를 검색합니다.", Toast.LENGTH_LONG).show();
                //여기서 AsyncTask를 이용 검색 리퀘스트로 데이터를 받아 오게 처리 하자. - AsyncTask 공유할것.

                String url = "https://api.themoviedb.org/3/search/movie?api_key=f32f8cf42496810a68d546d9a66f1085&query="+s+"&language=ko-KR&page=1";
                String[] strings = {url};
                MyAsyncTask mAsyncTask = new MyAsyncTask();
                mAsyncTask.execute(strings[0]);

                return false;
            }

            //검색 입력창에 새로운 텍스트가 들어갈때 마다 호출 - 여기선 필요 없음
            @Override
            public boolean onQueryTextChange(String s) {
                //Log.d("Search", "keyword: " + s);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_search:
                //Toast.makeText(this, "action_search", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, "앱설정-준비중", Toast.LENGTH_LONG).show();
                return true;
            default:
                Toast.makeText(getApplicationContext(),"홈으로 이동.",Toast.LENGTH_SHORT).show();
                String all = "https://api.themoviedb.org/3/movie/upcoming?api_key=f32f8cf42496810a68d546d9a66f1085&language=ko-KR&page=1";
                String[] strings = {all};
                MyAsyncTask mAsyncTask = new MyAsyncTask();
                mAsyncTask.execute(strings[0]);
                return super.onOptionsItemSelected(item);
        }
    }


    public class MyAsyncTask extends AsyncTask<String, Void, Movie[]> {
        //로딩중 표시
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialoDetailActivityg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //progressDialog.setMessage("로딩중...");
            //show dialog
            //progressDialog.show();

            //목록 배열의 내용을 크리어 해 놓는다.
            movieList.clear();

        }

        @Override
        protected Movie[] doInBackground(String... strings) {
            Log.d("AsyncTask", "url : " + strings[0]);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                Movie[] posts = gson.fromJson(rootObject, Movie[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Movie[] result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            //ArrayList에 차례대로 집어 넣는다.
            if(result.length > 0){
                for(Movie p : result){
                    movieList.add(p);
                }
            }
            //어답터 설정
            adapter = new MyRecyclerViewAdapter(MainActivity.this, movieList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
