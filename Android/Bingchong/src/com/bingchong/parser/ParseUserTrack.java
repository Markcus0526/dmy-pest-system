package com.bingchong.parser;

import java.util.ArrayList;

import com.bingchong.bean.UserTrackBean;
import org.json.JSONArray;
import org.json.JSONObject;

public class ParseUserTrack {
    public static ArrayList<UserTrackBean> getParsedResult(String jsonResponse){
        ArrayList<UserTrackBean> arrList = null;
        try {

            JSONObject mainJsonObject = new JSONObject(jsonResponse);

            JSONArray array = mainJsonObject.getJSONArray("data");
            arrList = new ArrayList<UserTrackBean>();
            for (int i = 0; i < array.length(); i++) {
                UserTrackBean obj = parseJsonResponse((JSONObject) array.get(i));
                arrList.add(obj);
            }
            return arrList;

        }
        catch (Exception ex) {
        }

        return arrList;
    }
    private static UserTrackBean parseJsonResponse(JSONObject jsonObject) throws Exception{
        try {
            UserTrackBean result = new UserTrackBean();
            result.userid = jsonObject.getInt("userid");
            result.name = jsonObject.getString("username");

            // parse all track pos
            JSONArray trackPos = jsonObject.getJSONArray("tracks");
            for (int i = 0; i < trackPos.length(); i++)
            {
                // parse one track pos
                JSONObject jsonOnePos = trackPos.getJSONObject(i);
                UserTrackBean.TrackPos oneTrk = new UserTrackBean.TrackPos();
                oneTrk.latitude = jsonOnePos.getDouble("latitude");
                oneTrk.longitude = jsonOnePos.getDouble("longitude");
                oneTrk.date = jsonOnePos.getString("date");
                // add track data
                result.tracks.add(oneTrk);
            }

            return result;

        }
        catch (Exception ex) {
        }
        return null;
    }



}
