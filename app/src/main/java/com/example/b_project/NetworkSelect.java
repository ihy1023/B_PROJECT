package com.example.b_project;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


// 사용 new NetworkSelect().execute("") 전체 불러오기
// 사용 new NetworkSelect().execute("id") id 불러오기
public class NetworkSelect extends AsyncTask<String, Void, String>{

    private URL Url;
    private String URL_Adress = "http://218.147.200.116:8080/mobile/testDB.jsp";
    private Custom_Adapter adapter;
    String res;

    //생성자:어뎁터에 데이터를 보냄  network -> adapter로 데이터 보냄
    public NetworkSelect(Custom_Adapter adapter){
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

        try{
            Url = new URL(URL_Adress);
            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();

            //전송모드 설정
            conn.setDefaultUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST"); //전달방식 (POST, GET)
            conn.setRequestProperty("Content-type","application/x-www-form-urlencoded; charset=utf-8"); //웹페이지 연결할때 속성

            //전송값 설정(요청값 설정/ (요청 <-> 응답))
            StringBuffer buffer = new StringBuffer();
            buffer.append("moviename").append("=").append(strings[0]);
            //서버로 전송 (요청값 전달, request)
            OutputStreamWriter outStream = new OutputStreamWriter(conn.getOutputStream(), "utf-8"); //stream
            PrintWriter writer = new PrintWriter(outStream); //writer / 실제 값 넣어주기 위함 (outputsteam을 writer에 연결)
            writer.write(buffer.toString()); // string
            writer.flush(); // 전송

            StringBuilder builder = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null){
                builder.append(line+"\n");
            }
            res = builder.toString();

        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        Log.i("Get result", res);
        return res;
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);

        ArrayList<commentInfo> commentList = new ArrayList<commentInfo>(); //데이터 받을 곳
        int count = 0;
        try{
            count = JsonParser.getCommentInfoJson(s, commentList);
        } catch (JSONException e){
            e.printStackTrace();
        }
        if(count != 0){
            adapter.setDatas(commentList);
            adapter.notifyDataSetInvalidated();
        }
    }
}