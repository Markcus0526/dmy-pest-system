package com.bingchong.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.provider.ContactsContract;
import com.bingchong.Constant;
import com.bingchong.activity.LoginActivity;
import com.bingchong.bean.BlightBean;
import com.bingchong.bean.DBObjBean;
import com.bingchong.bean.HelpBean;
import com.bingchong.bean.NoticeBean;
import com.bingchong.bean.OpinionBean;
import com.bingchong.bean.OpinionKindBean;
import com.bingchong.bean.PointBean;
import com.bingchong.bean.TaskBean;
import com.bingchong.bean.TaskReportBean;
import com.bingchong.bean.UserTrackBean;
import com.bingchong.bean.ValueBean;
import com.bingchong.bean.WatcherBean;
import com.bingchong.bean.XianBean;
import com.bingchong.bean.ReportBean;
import com.bingchong.bean.FieldBean;
import com.bingchong.bean.FormBean;
import com.bingchong.bean.ExtraTaskBean;
import com.bingchong.bean.UserBean;
import com.bingchong.net.AsyncHttpClient;
import com.bingchong.net.AsyncHttpResponseHandler;
import com.bingchong.net.HttpApi;
import com.bingchong.net.HttpParams;
import com.bingchong.parser.ParseUserTrack;
import com.bingchong.parser.ParserBlight;
import com.bingchong.parser.ParserField;
import com.bingchong.parser.ParserForm;
import com.bingchong.parser.ParserHelp;
import com.bingchong.parser.ParserNotice;
import com.bingchong.parser.ParserObject;
import com.bingchong.parser.ParserOpinionKind;
import com.bingchong.parser.ParserPlace;
import com.bingchong.parser.ParserReport;
import com.bingchong.parser.ParserExtraTask;
import com.bingchong.parser.ParserTask;
import com.bingchong.parser.ParserTaskReport;
import com.bingchong.parser.ParserUser;
import com.bingchong.parser.ParserValue;
import com.bingchong.parser.ParserWatcher;
import com.bingchong.parser.ParserXian;

public class DataMgr {
	public static int getTimeOut() { return 5 * 1000; }
	
	private static ArrayList<XianBean> mListXians = null;
    private static ArrayList<OpinionKindBean> mOpinionKinds = null;

	
	public static UserBean		m_curUser = null;
	
	public static void clearAllDatas(){
		mListXians = null;
		mOpinionKinds = null;
		try{
			DB db = new DB(LoginActivity.getMainActivity());
			TblObject tbl = db.getTblObject();
			//tbl.deleteAll();
            tbl.deleteObjectsByType(DBObjBean.TYPE_POINT);
            tbl.deleteObjectsByType(DBObjBean.TYPE_POINT_INFO);
		}
		catch(Exception ex){

		}
	}
	
	public static ArrayList<UserBean> getUsers(String search, int xian_id){
		ArrayList<UserBean> listUsers = new ArrayList<UserBean>();
		if(m_curUser.isAdmin())
		{
			HttpParams params = new HttpParams();
			params.addParam("uid", m_curUser.id);
			params.addParam("xian_id", xian_id);
			params.addParam("search", search);
			
			String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_USERS;
			String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
			listUsers = ParserUser.getParsedResult(response);
		}
		
		return listUsers;
	}
	
    public static void getPointsList(int xian_id, String stime, String search, AsyncHttpResponseHandler handler)
    {
        String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_PLACE_SEARCH +
                "?admin_id=" + m_curUser.id + "&xian_id=" + xian_id + "&search=" + search + "&stime=" + stime;

        if(xian_id == 0)
            serviceUrl = Constant.URL_SERVER + Constant.URL_GET_PLACE_SEARCH +
                    "?admin_id=" + m_curUser.id + "&stime=" + stime;

        try
        {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(serviceUrl, handler);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }	
	
	public static ArrayList<PointBean> getPoints(String search, int xian_id){
		return getPoints(search, xian_id, false);
	}

	public static ArrayList<PointBean> getPoints(String search, int xian_id, boolean isExtra){
		
		HttpParams params = new HttpParams();
		if(m_curUser.isAdmin())
			params.addParam("admin_id", m_curUser.id);
		else
			params.addParam("watcher_id", m_curUser.id);
		params.addParam("xian_id", xian_id);
		params.addParam("search", search);
		
		if(isExtra)
			params.addParam("extra", isExtra);
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_PLACE_SEARCH;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
		
		ArrayList<PointBean> listPoints = ParserPlace.getParsedResult(response);
		if(listPoints == null)
			listPoints = new ArrayList<PointBean>();
			
		return listPoints;		
	}
	
	public static ArrayList<PointBean> getPointsWithInfoAndUpdateDB(int xian_id){
		
		HttpParams params = new HttpParams();
		if(m_curUser.isAdmin())
			params.addParam("admin_id", m_curUser.id);
		else
			params.addParam("watcher_id", m_curUser.id);
		params.addParam("xian_id", xian_id);
		params.addParam("inc_info", "true");
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_PLACE_SEARCH;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
		
		ArrayList<PointBean> listPoints = ParserPlace.getParsedResult(response);
		if(listPoints == null){
			listPoints = new ArrayList<PointBean>();
			return listPoints;
		}
		
		// up
		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();
		ArrayList<DBObjBean> newList = new ArrayList<DBObjBean>();

		for(int i= 0; i < listPoints.size(); i++)
		{
			PointBean obj = listPoints.get(i);
			String contents = obj.html_info;
			obj.html_info = null;
			DBObjBean dbObj = tbl.findObject(DBObjBean.TYPE_POINT_INFO, obj.id);
			
			if(dbObj != null)
				continue;
			
			dbObj = new DBObjBean();
			dbObj.contents = contents;
			dbObj.name = obj.name;
			dbObj.obj_id = obj.id;
			dbObj.type = DBObjBean.TYPE_POINT_INFO;
			newList.add(dbObj);
		}

		tbl.addObjects(newList);		
			
		return listPoints;		
	}	
	
	public static boolean updateExtraPoint(PointBean obj){
		HttpParams params = new HttpParams();
		if(m_curUser.isAdmin())
			params.addParam("admin_id", m_curUser.id);
		else
			params.addParam("watcher_id", m_curUser.id);
		params.addParam("point_id", obj.id);
		params.addParam("status", obj.status);
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_UPLOAD_POINT;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
		if(ParserObject.getParsedResult(response))
			return true;
		return false;
	}
	
	public static boolean insertExtraPoint(PointBean obj){
		HttpParams params = obj.getPostParameters();
		if(m_curUser.isAdmin())
			params.addParam("admin_id", m_curUser.id);
		else
			params.addParam("watcher_id", m_curUser.id);
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_UPLOAD_POINT;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
		if(ParserObject.getParsedResult(response))
			return true;
		return false;
	}	
	
    public static ArrayList<UserTrackBean> getUserTrack(int xian_id, String date){
    	
		HttpParams params = new HttpParams();
		params.addParam("xian_id", xian_id);
		params.addParam("date", date);

		String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_USER_TRACK;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);			
		
		ArrayList<UserTrackBean> mListUserTrks = ParseUserTrack.getParsedResult(response);
        return mListUserTrks;
    }	
    
    public static void getUserTrackList(int xian_id, String date, AsyncHttpResponseHandler handler)
    {
        String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_USER_TRACK +
                "?xian_id=" + xian_id + "&date=" + date;

        try
        {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(serviceUrl, handler);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }    
	
	public static String getPointInfo(int point_id){
		HttpParams params = new HttpParams();
		params.addParam("point_id", point_id);
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_PLACE_INFO;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);			
		return response;
	}

	public static boolean insertExtraBlight(BlightBean obj){
		HttpParams params = obj.getPostParameters();
		if(m_curUser.isAdmin())
			params.addParam("admin_id", m_curUser.id);
		else
			params.addParam("watcher_id", m_curUser.id);
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_UPLOAD_BLIGHT;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
		if(ParserObject.getParsedResult(response))
			return true;
		return false;
	}		
	
	public static ArrayList<BlightBean> getBlights(String search){
		return getBlights(search, false);
	}

	public static ArrayList<BlightBean> getBlights(String search, boolean isExtra){
		
		HttpParams params = new HttpParams();
		params.addParam("userid", m_curUser.id);
		params.addParam("search", search);

		params.addParam("kind", 0);
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_BLIGHT_SEARCH;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);			
		ArrayList<BlightBean> listBlights = ParserBlight.getParsedResult(response);
		return listBlights;		
	}
	
	public static String getBlightInfo(int blight_id){

		HttpParams params = new HttpParams();
		params.addParam("uid", m_curUser.id);
		params.addParam("blight_id", blight_id);
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_BLIGHT_INFO;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);			
		return response;
	}
	
	public static boolean updateExtraBlight(BlightBean obj){
		HttpParams params = new HttpParams();
		if(m_curUser.isAdmin())
			params.addParam("admin_id", m_curUser.id);
		else
			params.addParam("watcher_id", m_curUser.id);
		params.addParam("blight_id", obj.id);
		params.addParam("status", obj.status);
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_UPLOAD_BLIGHT;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
		if(ParserObject.getParsedResult(response))
			return true;
		return false;
	}
	
	public static ArrayList<TaskBean> getTasks(){
		ArrayList<TaskBean> list = new ArrayList<TaskBean>();
		// TODO
		
		return list;
	}
	
		
	public static ArrayList<ExtraTaskBean> getExtraTasks(String search, int task_id){
		HttpParams params = new HttpParams();
		if(m_curUser.isAdmin())
			params.addParam("admin_id", m_curUser.id);
		else
			params.addParam("watcher_id", m_curUser.id);
		
		if(search != null && search.length() > 0)
			params.addParam("search", search);
		if(task_id > 0)
			params.addParam("task_id", task_id);
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_EXTRA_TASKS;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);			
		ArrayList<ExtraTaskBean> listObjs = ParserExtraTask.getParsedResult(response);
		
		if(listObjs == null)
			listObjs = new ArrayList<ExtraTaskBean>();

		return listObjs;		
	}
	
	public static boolean addExtraTask(ExtraTaskBean task){
		task.admin_id = (int)m_curUser.id;
		HttpParams params = task.getPostParameters();
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_UPLOAD_EXTRA_TASK;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
		if(ParserObject.getParsedResult(response))
			return true;
		return false;
	}
	
	public static boolean updateExtraTask(ExtraTaskBean task){
		HttpParams params = new HttpParams();
		if(m_curUser.isAdmin())
			params.addParam("admin_id", m_curUser.id);
		else
			params.addParam("watcher_id", m_curUser.id);
		params.addParam("task_id", task.id);
		params.addParam("status", task.status);
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_UPLOAD_EXTRA_TASK;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
		if(ParserObject.getParsedResult(response))
			return true;
		return false;
	}
	
	public static ArrayList<DBObjBean> getDbObjects(List list){
		ArrayList<DBObjBean> objList = new ArrayList<DBObjBean>();
		for(int i = 0; i < list.size(); i++){
			Object obj = list.get(i);
			DBObjBean dbObj = new DBObjBean(obj);
			objList.add(dbObj);
		}
		
		return objList;
	}
	
	public static ArrayList<XianBean> getXians(){
		if(mListXians != null && mListXians.size() > 0)
			return mListXians;
		
		HttpParams params = new HttpParams();
		params.addParam("uid", m_curUser.id);
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_XIANS;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);			
		mListXians = ParserXian.getParsedResult(response);
		
		return mListXians;
	}
	
	public static void getXiansList(AsyncHttpResponseHandler handler)
    {
        String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_XIANS +"?id="+ m_curUser.id;

        try
        {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(serviceUrl, handler);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
	
	public static WatcherBean getCurentPointList(){
		if(m_curUser.isAdmin())
			return null;
		HttpParams params = new HttpParams();
		
		params.addParam("table", "watchers");
		params.addParam("field", "user_id");
		params.addParam("value", m_curUser.id);
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_TABLE_ITEM;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
		ArrayList<WatcherBean>  list = ParserWatcher.getParsedResult(response);
		if(list != null && list.size() > 0)
			return list.get(0);
		
		return null;
	}
	
	public static ArrayList<OpinionKindBean> getOpinionKinds(){
		if(mOpinionKinds != null && mOpinionKinds.size() > 0)
			return mOpinionKinds;
		
		HttpParams params = new HttpParams();
		
		params.addParam("table", "opinion_kinds");
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_TABLE_ITEM;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
		mOpinionKinds = ParserOpinionKind.getParsedResult(response);
		if(mOpinionKinds == null)
			mOpinionKinds = new ArrayList<OpinionKindBean>();
		return mOpinionKinds;
	}	
	
	public static ArrayList<HelpBean> getHelps(Boolean isManual){
		
		HttpParams params = new HttpParams();
		
		if(isManual)
			params.addParam("table", "manual");
		else
			params.addParam("table", "helps");
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_TABLE_ITEM;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
		ArrayList<HelpBean> mHelps = ParserHelp.getParsedResult(response);
		if(mHelps == null)
			mHelps = new ArrayList<HelpBean>();
		return mHelps;
	}

	
	public static Boolean uploadCurentPointList(String ptList){
		if(m_curUser.isAdmin())
			return false;
		
		HttpParams params = new HttpParams();
		
		params.addParam("user_id", m_curUser.id);
		params.addParam("point_list", ptList);
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_UPLOAD_WATCHER;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
		if(ParserObject.getParsedResult(response))
			return true;
		return false;
	}	
	
	public static Boolean updateUserInfo(){
		
		HttpParams params = new HttpParams();
		
		params.addParam("user_id", m_curUser.id);
		params.addParam("place", m_curUser.place);
		params.addParam("job", m_curUser.job);
		params.addParam("photo", m_curUser.photoData);
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_UPLOAD_USER;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
		if(ParserObject.getParsedResult(response))
			return true;
		return false;
	}
	
	public static Boolean uploadOpinion(OpinionBean obj){
		HttpParams params = new HttpParams();

		params.addParam("user_id", m_curUser.id);
		params.addParam("kind", obj.kind_id);
		params.addParam("contents", obj.contens);

		String serviceUrl = Constant.URL_SERVER + Constant.URL_UPLOAD_OPINION;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
		if(ParserObject.getParsedResult(response))
			return true;
		return false;
	}	
	
	//---------------- point DB -----------------------------------------	
	public static ArrayList<PointBean> getPointsFromDB(){
		return getPointsFromDB(false);
	}
	
	public static ArrayList<PointBean> getPointsFromDB(Boolean isCheck){
		ArrayList<PointBean> ptList = new ArrayList<PointBean>();
		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();
		
		ArrayList<DBObjBean> list = tbl.findObjects("", DBObjBean.TYPE_POINT);
		for(int i = 0; i < list.size(); i++)
		{
			PointBean obj = (PointBean)list.get(i).getBeanObj();
			if(obj == null)
				continue;
			if(isCheck && (!obj.isCheck()))
				continue;
			ptList.add(obj);
		}
		
		return ptList;
	}
	
	public static PointBean getPointInfoFromDB(int id){

		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();
		
		DBObjBean obj = tbl.findObject(DBObjBean.TYPE_POINT_INFO, id);
		if(obj == null)
			return null;
		
		PointBean bean = new PointBean();
		bean.id = id;
		bean.name = obj.name;
		bean.info = obj.contents;
		
		return bean;
	}
	
	public static Boolean checkPointInfoInDB(ArrayList<PointBean> list){
		
		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();
		
		ArrayList<DBObjBean> newList = new ArrayList<DBObjBean>();
		for(int i= 0; i < list.size(); i++)
		{
			PointBean obj = list.get(i);
			DBObjBean dbObj = tbl.findObject(DBObjBean.TYPE_POINT_INFO, obj.id);
			
			if(dbObj != null)
				continue;
			
			HttpParams params = new HttpParams();
			params.addParam("point_id", obj.id);
			
			String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_PLACE_INFO;
			String info = HttpApi.sendRequest(serviceUrl, params, null);	

			if(info.length() == 0)
				continue;
			
			dbObj = new DBObjBean();
			dbObj.contents = info;
			dbObj.name = obj.name;
			dbObj.obj_id = obj.id;
			dbObj.type = DBObjBean.TYPE_POINT_INFO;
			newList.add(dbObj);
		}

		tbl.addObjects(newList);
		
		return true;
	}
	public static Boolean checkBlightInfoInDB(ArrayList<BlightBean> list){
		
		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();
		
		ArrayList<DBObjBean> newList = new ArrayList<DBObjBean>();
		for(int i= 0; i < list.size(); i++)
		{
			BlightBean obj = list.get(i);
			DBObjBean dbObj = tbl.findObject(DBObjBean.TYPE_BLIGHT_INFO, obj.id);
			
			if(dbObj != null)
				continue;
			
			HttpParams params = new HttpParams();
			params.addParam("point_id", obj.id);
			
			String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_BLIGHT_INFO;
			String info = HttpApi.sendRequest(serviceUrl, params, null);				
			
			if(info.length() == 0)
				continue;
			
			dbObj = new DBObjBean();
			dbObj.contents = info;
			dbObj.name = obj.name;
			dbObj.obj_id = obj.id;
			dbObj.type = DBObjBean.TYPE_BLIGHT_INFO;
			newList.add(dbObj);
		}

		tbl.addObjects(newList);
		
		return true;
	}	
	
	public static Boolean updatePointsToDB(ArrayList<PointBean> list){
		if(list == null)
			return false;
		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();
		
		// update points db
		tbl.deleteObjectsByType(DBObjBean.TYPE_POINT);
		tbl.addObjects(getDbObjects(list));
		
		return true;
	}
	//---------------- end point DB -----------------------------------------	
	
	//---------------- blight DB -----------------------------------------	
	public static ArrayList<BlightBean> getBlightsFromDB(String search){
		ArrayList<BlightBean> ptList = new ArrayList<BlightBean>();
		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();
		
		ArrayList<DBObjBean> list = tbl.findObjects(search, DBObjBean.TYPE_BLIGHT);
		for(int i = 0; i < list.size(); i++)
		{
			BlightBean obj = (BlightBean)list.get(i).getBeanObj();
			if(obj!=null)
				ptList.add(obj);
		}
		
		return ptList;
	}
	
	public static void updateBlightsToDB(ArrayList<BlightBean> list){
		if(list == null)
			return;
		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();
		
		// update blight db
		tbl.deleteObjectsByType(DBObjBean.TYPE_BLIGHT);
		tbl.addObjects(getDbObjects(list));		
	}	
	
	public static BlightBean getBlightInfoFromDB(int id){
		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();
		
		DBObjBean obj = tbl.findObject(DBObjBean.TYPE_BLIGHT_INFO, id);
		if(obj == null)
			return null;
		
		BlightBean bean = new BlightBean();
		bean.id = id;
		bean.name = obj.name;
		bean.info = obj.contents;
		
		return bean;
	}
	
	public static BlightBean getBlightFromDB(int id){
		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();
		
		DBObjBean obj = tbl.findObject(DBObjBean.TYPE_BLIGHT, id);
		if(obj == null)
			return null;
		
		BlightBean bean = (BlightBean)obj.getBeanObj();
	
		return bean;
	}
	//---------------- end blight DB -----------------------------------------	
		
	//---------------- form DB -----------------------------------------
	public static ArrayList<FormBean> getForms(boolean isDetail, long blight_id){
		
		HttpParams params = new HttpParams();
		params.addParam("blight_id", blight_id);
		
		String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_FORMS;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);			
		ArrayList<FormBean> list = ParserForm.getParsedResult(response);
		
		if(isDetail)
		{
			for(int i = 0; i < list.size(); i++){
				FormBean frm = list.get(i);
				
				params = new HttpParams();
				params.addParam("form_id", frm.id);
	
				serviceUrl = Constant.URL_SERVER + Constant.URL_GET_FIELDSS;
				response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
				ArrayList<FieldBean> fields = ParserField.getParsedResult(response);
				frm.setFields(fields);
			}
		}
		
		return list;
	}
	
	public static ArrayList<FieldBean> getFields(int form_id){

		HttpParams params = new HttpParams();
		params.addParam("form_id", form_id);

		String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_FIELDSS;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);
		ArrayList<FieldBean> fields = ParserField.getParsedResult(response);

		return fields;
	}	
	
	public static ArrayList<FormBean> getFormsFromDB(long blight_id){
		ArrayList<FormBean> ptList = new ArrayList<FormBean>();
		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();

		if(blight_id < 0) {
			ArrayList<DBObjBean> list = tbl.findObjects("", DBObjBean.TYPE_FORM);
			for(int i = 0; i < list.size(); i++)
			{
				FormBean obj = (FormBean)list.get(i).getBeanObj();
				ptList.add(obj);
			}
			return ptList;
		}

        BlightBean blightItem = null;
        for(DBObjBean obj : tbl.findObjects("", DBObjBean.TYPE_BLIGHT))
        {
            BlightBean item = (BlightBean)obj.getBeanObj();
            if(item.id == blight_id) {
                blightItem = item;
                break;
            }
        }
        if(blightItem == null)
            return null;

        String[] arrForm = blightItem.form_ids.split(",");
		ArrayList<DBObjBean> list = tbl.findObjects("", DBObjBean.TYPE_FORM);
		for(int i = 0; i < list.size(); i++)
		{
			FormBean obj = (FormBean)list.get(i).getBeanObj();
			for(int j = 0; j < arrForm.length; j++) {
                try
                {
                    if(obj.id == Integer.parseInt(arrForm[j].replace(" ", ""))) {
                        ptList.add(obj);
                        break;
                    }
                }
                catch (Exception ex) {
                }
            }
		}
		
		return ptList;
	}
	
	public static void updateFormsToDB(ArrayList<FormBean> list){
		if(list == null)
			return;
		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();
		
		// update form db
		tbl.deleteObjectsByType(DBObjBean.TYPE_FORM);
		tbl.addObjects(getDbObjects(list));		
	}
	//---------------- form DB -----------------------------------------	
	
	public static ArrayList<ReportBean> getReportsFromDB(){
		ArrayList<ReportBean> ptList = new ArrayList<ReportBean>();
		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();
		
		ArrayList<DBObjBean> list = tbl.findObjects("", DBObjBean.TYPE_REPORT);
		for(int i = 0; i < list.size(); i++)
		{
			ReportBean obj = (ReportBean)list.get(i).getBeanObj();
			if(obj!=null)
				ptList.add(obj);
		}

		Collections.sort(ptList, new Comparator<ReportBean>() {
			@Override
			public int compare(ReportBean s1, ReportBean s2) {
				ReportBean dd1 = (ReportBean)s1;
				ReportBean dd2 = (ReportBean)s2;
				if(dd1.reportDate < dd2.reportDate)
					return 1;
				return -1;
			}
		});

		return ptList;
	}

	public static ReportBean getReportFromDB(int id) {
		ArrayList<ReportBean> arrReports = getReportsFromDB();
		for(ReportBean reportItem : arrReports) {
			if(reportItem.id == id)
				return reportItem;
		}
		return null;
	}

    public static String getHtmlContentFromReport(ReportBean reportBean)
    {
        String htmlContent = "";

        if(reportBean == null)
            return htmlContent;

        htmlContent = "<!doctype html>" +
                "<html>" +
                "<head>" +
                "    <meta charset=\"utf-8\" />" +
                "</head>" +
                "<body>" +
                "<p> </p>" +
                "<table align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\"  border=\"1\" cellspacing=\"0\" cellpadding=\"0\">" +
                "    <tbody>" +
                "    <tr>" +
                "        <td width=\"100\" height=\"40\" align=center>" +
                "            <p>病虫害类别</p>" +
                "        </td>" +
                "        <td width=\"200\" height=\"40\" align=center>" +
                "            <p>" + "虫害" + "</p>" +
                "        </td>" +
                "    </tr>" +
                "    <tr>" +
                "        <td width=\"100\" height=\"40\" align=center>" +
                "            <p>名称</p>" +
                "        </td>" +
                "        <td width=\"200\" height=\"40\" align=center>" +
                "            <p>" + reportBean.blightName + "</p>" +
                "        </td>" +
                "    </tr>" +
                "    <tr>" +
                "        <td width=\"100\" height=\"40\" align=center>" +
                "            <p>测报时间</p>" +
                "        </td>" +
                "        <td width=\"200\" height=\"40\" align=center>" +
                "            <p>" + reportBean.testDate + "</p>" +
                "        </td>" +
                "    </tr>" +
                "    <tr>" +
                "        <td width=\"100\" height=\"40\" align=center>" +
                "            <p>上报时间</p>" +
                "        </td>" +
                "        <td width=\"200\" height=\"40\" align=center>" +
                "            <p>" + reportBean.reportDate + "</p>" +
                "        </td>" +
                "    </tr>" +
                "    <tr>" +
                "        <td width=\"100\" height=\"40\" align=center>" +
                "            <p>报表名称</p>" +
                "        </td>" +
                "        <td width=\"200\" height=\"40\" align=center>" +
                "            <p>" + reportBean.form_name + "</p>" +
                "        </td>" +
                "    </tr>" +
                "    </tbody>" +
                "</table>" +
                "<p> </p>" +
                "<h2 align=\"center\"> <font size=5 color=\"#7AB038\">" + reportBean.form_name + "</font></h2>" +
                "<table align=\"center\" style=\"color: #7AB038;\" bordercolor=\"#7AB038\" border=\"1\" cellspacing=\"0\" cellpadding=\"5\">" +
                "    <tbody>";

        for(FieldBean fieldBean : reportBean.reportForm.getFields()) {
            htmlContent += "<tr>" +
                    "     <td width=\"130\" height=\"40\" align=left>" +
                    "         <p>" + fieldBean.fieldName + "</p>" +
                    "     </td>" +
                    "     <td width=\"150\" height=\"40\" align=left>" +
                    "         <p>" + fieldBean.val + "</p>" +
                    "     </td>" +
                    " </tr>";
        }

        htmlContent +=
                "</body>" +
                "</html>";

        return htmlContent;
    }

	public static void updateReportsToDB(ArrayList<ReportBean> list){
		if(list == null)
			return;
		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();
		
		// update form db
		//tbl.deleteObjectsByType(DBObjBean.TYPE_REPORT);
        int insertIndex = tbl.findObjects("", DBObjBean.TYPE_REPORT).size();
        for(ReportBean item : list) {

            ArrayList<DBObjBean> dbReportList = tbl.findObjects("", DBObjBean.TYPE_REPORT);
            for(int i = 0; i < dbReportList.size(); i++)
            {
                ReportBean obj = (ReportBean)dbReportList.get(i).getBeanObj();
                if(obj!=null)
                    if(obj.blight_id == item.blight_id &&
                            obj.form_id == item.form_id &&
                            obj.point_id == item.point_id &&
                            obj.user_id == item.user_id)
                        tbl.deleteObject(DBObjBean.TYPE_REPORT, obj.id);
            }

            item.id = insertIndex;
            insertIndex++;
        }
		tbl.addObjects(getDbObjects(list));		
	}

    public static void removeReportFromDB(long report_id)
    {
        DB db = new DB(LoginActivity.getMainActivity());
        TblObject tbl = db.getTblObject();

        tbl.deleteObject(DBObjBean.TYPE_REPORT, (int)report_id);
    }
	
	public static boolean reportToServer(ArrayList<ReportBean> list)
	{
		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();
		
		Boolean ret = false;
		
		for(int i = 0; i < list.size(); i++)
		{
			ReportBean rp = list.get(i);
			String serviceUrl = Constant.URL_SERVER + Constant.URL_SET_REPORT;
			HttpParams param = rp.getPostParameters();
			if(param == null)
				continue;
			
			String response = HttpApi.sendRequestWithJson(serviceUrl, param, null);

			if(ParserObject.getParsedResult(response))
			{
				ret = true;
			}
		}
		if(ret){
			tbl.deleteObjectsByType(DBObjBean.TYPE_REPORT);			
		}
		return ret;
	}
	
	
	public static ArrayList<ReportBean> getReports(String search, int position_id, String sTime, String eTime){
		
		ArrayList<ReportBean> list = null;
		if(list == null)
		{
			HttpParams params = new HttpParams();
			params.addParam("uid", m_curUser.id);
			params.addParam("search", search);
			params.addParam("point_id", position_id);
			params.addParam("stime", sTime);
			params.addParam("etime", eTime);
			
			String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_REPORTS;
			String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);					
			list = ParserReport.getParsedResult(response);			
		}
		
		return list;
	}
	
	public static ArrayList<TaskReportBean> getReportHistory(int point_id, String sTime, String eTime){
		
		ArrayList<TaskReportBean> list = null;
		if(list == null)
		{
			HttpParams params = new HttpParams();
			params.addParam("user_id", m_curUser.id);
			params.addParam("point_id", point_id);
			params.addParam("stime", sTime);
			params.addParam("etime", eTime);
			
//			String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_REPORT_HISTORY;
			String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_REPORTS;
			String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);					
			list = ParserTaskReport.getParsedResult(response);	
			
			Collections.sort(list);			
		}
		
		return list;
	}
	
	public static ArrayList<String> getTaskCounts(String sTime, String eTime){
		
		ArrayList<String> list = null;
		if(list == null)
		{
			HttpParams params = new HttpParams();
			params.addParam("user_id", m_curUser.id);
			params.addParam("stime", sTime);
			params.addParam("etime", eTime);
			
			String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_TASK_COUNT;
			String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);					
			list = ParserObject.getParsed(response);			
		}
		
		return list;
	}		
	
	public static ArrayList<TaskBean> getTaskList(String sTime){
		
		ArrayList<TaskBean> list = null;
		if(list == null)
		{
			HttpParams params = new HttpParams();
			params.addParam("user_id", m_curUser.id);
			if(sTime != null)
				params.addParam("stime", sTime);
			else
				params.addParam("check_reports", "true");
			
			String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_TASK_LIST;
			String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);					
			list = ParserTask.getParsedResult(response);
			Collections.sort(list);
		}
		
		return list;
	}
	
	public static ArrayList<NoticeBean> getMessages(String year){
		
		ArrayList<NoticeBean> list = null;
		if(list == null)
		{
			HttpParams params = new HttpParams();
			params.addParam("year", year);
						
			String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_NOTICES;
			String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);				
			list = ParserNotice.getParsedResult(response);			
		}
		
		return list;
	}
	
	public static ArrayList<ValueBean> getAnalysisValues(int xian_id, int point_id, int form_id, int field_id, String sDate, String eDate){
		HttpParams params = new HttpParams();
        if(xian_id >= 0)
            params.addParam("xian_id", xian_id);
		params.addParam("user_id", m_curUser.id);
		params.addParam("form_id", form_id);
		params.addParam("point_id", point_id);
		params.addParam("field_id", field_id);
		params.addParam("stime", sDate);
		params.addParam("etime", eDate);
					
		String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_REPORT_POLYLINES;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);				
		ArrayList<ValueBean> list = ParserValue.getParsedResult(response);			
		return list;
	}
	
	public static ArrayList<ValueBean> getAnalysisValues(int xian_id, int form_id, int field_id, String sDate){
		HttpParams params = new HttpParams();
		params.addParam("user_id", m_curUser.id);
		params.addParam("form_id", form_id);
		params.addParam("xian_id", xian_id);
		params.addParam("field_id", field_id);
		params.addParam("stime", sDate);
					
		String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_REPORT_BARS;
		String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);				
		ArrayList<ValueBean> list = ParserValue.getParsedResult(response);			
		return list;
	}	
	
	public static NoticeBean getMessage(int id){
		
		ArrayList<NoticeBean> list = null;
		if(list == null)
		{

			HttpParams params = new HttpParams();
			params.addParam("uid", id);
						
			String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_NOTICES;
			String response = HttpApi.sendRequestWithJson(serviceUrl, params, null);				
			list = ParserNotice.getParsedResult(response);			
		}
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

    public static void getLatestVersion(AsyncHttpResponseHandler handler){

        String serviceUrl = Constant.URL_SERVER + Constant.URL_GET_VERSION;

        try
        {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(getTimeOut());
            client.get(serviceUrl, handler);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

	public static Boolean updateUsersToDB(ArrayList<UserBean> list){
		if(list == null)
			return false;
		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();

		// update users db
		tbl.deleteObjectsByType(DBObjBean.TYPE_USER);
		tbl.addObjects(getDbObjects(list));

		return true;
	}

	public static ArrayList<UserBean> getUsersFromDB(){
		ArrayList<UserBean> userList = new ArrayList<UserBean>();
		DB db = new DB(LoginActivity.getMainActivity());
		TblObject tbl = db.getTblObject();

		ArrayList<DBObjBean> list = tbl.findObjects("", DBObjBean.TYPE_USER);
		for(int i = 0; i < list.size(); i++)
		{
			UserBean obj = (UserBean)list.get(i).getBeanObj();
			if(obj == null)
				continue;
			userList.add(obj);
		}

		return userList;
	}
}
