package com.example.b_project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {
    static public int getCommentInfoJson(String response, ArrayList<commentInfo> commentList)throws JSONException {
        String strMoviename;
        String strId;
        String strComment;
        String strRating;
        String strdate;

        JSONObject rootJSON = new JSONObject(response);
        JSONArray jsonArray = new JSONArray(rootJSON.getString("datas"));
        for (int i=0; i<jsonArray.length();i++){
            JSONObject jsonObj = (JSONObject) jsonArray.get(i);
            if (jsonObj.getString("MOVIENAME").toString().equals("null")){
                strMoviename="-";
            }else{
                strMoviename=jsonObj.getString("MOVIENAME").toString();
            }
            if (jsonObj.getString("ID").toString().equals(null)){
                strId="-";
            }else{
                strId=jsonObj.getString("ID").toString();
            }
            if (jsonObj.getString("COMMENT").toString().equals(null)){
                strComment="-";
            }else{
                strComment=jsonObj.getString("COMMENT").toString();
            }
            if (jsonObj.getString("RATING").toString().equals(null)){
                strRating="-";
            }else{
                strRating=jsonObj.getString("RATING").toString();
            }
            if (jsonObj.getString("WRITEDATE").toString().equals(null)){
                strdate="-";
            }else{
                strdate=jsonObj.getString("WRITEDATE").toString();
            }
            commentList.add(new commentInfo(strMoviename,strId,strComment,strRating,strdate));
        }
        return jsonArray.length();
    }
    static public int getResultJson(String response) throws JSONException{
        JSONArray jsonArray = new JSONArray(response);
        JSONObject jsonObject = new JSONObject(jsonArray.getString(0));
        return Integer.parseInt(jsonObject.getString("RESULT_OK"));
    }
}
