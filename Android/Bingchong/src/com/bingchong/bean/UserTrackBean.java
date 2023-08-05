package com.bingchong.bean;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: KimOC
 * Date: 2014/6/29
 * Time: 上午 9:04
 * To change this template use File | Settings | File Templates.
 */
public class UserTrackBean {

    public int userid;
    public String name;
    public double latitude = 0;
    public double longitude = 0;
    public ArrayList<TrackPos> tracks = new ArrayList<TrackPos>(0);

    public static class TrackPos {
        public double latitude;
        public double longitude;
        public String date;
    }
}
