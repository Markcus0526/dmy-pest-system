package com.bingchong.db;

import java.util.ArrayList;

import com.bingchong.bean.DBObjBean;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TblObject {
	private static String TABLE_OBJECT = "tb_object";

	private final static String COLUMN_ID = "id";
	private final static String COLUMN_OBJ_ID = "obj_id";
	private final static String COLUMN_OBJ_TYPE = "obj_type";
	private final static String COLUEM_OBJ_NAME = "name";
	private final static String COLUMN_OBJ_VAL  = "value";
	
	private 	SQLiteOpenHelper	mHelper;
	
	public TblObject(SQLiteOpenHelper helper)
	{
		mHelper = helper;
	}
	
	public final void onCreate(SQLiteDatabase db, String dbPrefix) {
		db.execSQL(dbPrefix + TABLE_OBJECT
				+ " (id integer primary key autoincrement, " + 
				COLUMN_OBJ_ID + " INT, " + 
				COLUMN_OBJ_TYPE + " INT, " + 
				COLUEM_OBJ_NAME + " TEXT, " + 
				COLUMN_OBJ_VAL + " TEXT)");
	}

	public final void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion, String dbPrefix) {
		Log.w("---[DEBUG]----", "Upgrading database from version " + oldVersion
				+ " to " + newVersion + ", which will destroy all old data");

		db.execSQL(dbPrefix + TABLE_OBJECT);
	}
	public void updateMarker(DBObjBean obj)
	{
		SQLiteDatabase mdb = null;
		try {
			mdb = mHelper.getWritableDatabase();
			
			String sql_update = "update "+ TABLE_OBJECT+ " set "+ 
					COLUMN_OBJ_ID + " = '"+ obj.obj_id + "', " +
					COLUMN_OBJ_TYPE + " = '"+ obj.type + "', " +
					COLUEM_OBJ_NAME + " = '"+ obj.name + "', " +
					COLUMN_OBJ_VAL + " = '"+ obj.contents + "' " +
					" where " + COLUMN_ID + " = " + obj.id;
			mdb.execSQL(sql_update);
				
		} catch (Exception e) {
			Log.e(" ==== ", e.getMessage());
		} finally {
			if (mdb != null)
				mdb.close();
		}		
	}
	
	public void addObject(DBObjBean obj)
	{
		SQLiteDatabase mdb = null;
		ContentValues values = new ContentValues();
		try {
			mdb = mHelper.getWritableDatabase();

			// duplication check
			// add new marker
			values.put(COLUMN_OBJ_ID, obj.obj_id);
			values.put(COLUMN_OBJ_TYPE, obj.type);
			values.put(COLUEM_OBJ_NAME, obj.name);
			values.put(COLUMN_OBJ_VAL, obj.contents);
			mdb.insert(TABLE_OBJECT, null, values);

		} catch (Exception e) {
			Log.e(" add marker ==== ", e.getMessage());
		} finally {
			if (mdb != null)
				mdb.close();
		}		
	}
	
	public void addObjects(ArrayList<DBObjBean> arr)
	{
		SQLiteDatabase mdb = null;
		ContentValues values = new ContentValues();
		try {
			mdb = mHelper.getWritableDatabase();
			mdb.beginTransaction();
			
			for(int i = 0; i < arr.size(); i++)
			{
				DBObjBean obj = arr.get(i);
	
				// duplication check
				// add new marker
				values.put(COLUMN_OBJ_ID, obj.obj_id);
				values.put(COLUMN_OBJ_TYPE, obj.type);
				values.put(COLUEM_OBJ_NAME, obj.name);
				values.put(COLUMN_OBJ_VAL, obj.contents);
				mdb.insert(TABLE_OBJECT, null, values);
			}
			mdb.setTransactionSuccessful();
			mdb.endTransaction();
			mdb.close();
			mdb = null;

		} catch (Exception e) {
			Log.e(" add marker ==== ", e.getMessage());
		} finally {
			if (mdb != null)
				mdb.close();
		}		
	}	
	
	private DBObjBean getObjFromCursor(Cursor cursor)
	{
		DBObjBean obj = new DBObjBean();
		obj.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
		obj.obj_id = cursor.getInt(cursor.getColumnIndex(COLUMN_OBJ_ID));
		obj.type  = cursor.getInt(cursor.getColumnIndex(COLUMN_OBJ_TYPE));
		obj.name = cursor.getString(cursor.getColumnIndex(COLUEM_OBJ_NAME));
		obj.contents = cursor.getString(cursor.getColumnIndex(COLUMN_OBJ_VAL));
		
		return obj;
	}
	
	public ArrayList<DBObjBean> findObjects(String name, int type)
	{
		ArrayList<DBObjBean> arrList = new ArrayList<DBObjBean>();
		SQLiteDatabase mdb = null;
		Cursor cursor = null;
		try {
			mdb = mHelper.getReadableDatabase();

			// duplication check
			String sql_exist_check = "select * from "
					+ TABLE_OBJECT
					+ " where " + 
					COLUEM_OBJ_NAME + " like '%" + name + "%' and " +
					COLUMN_OBJ_TYPE + "='" + type + "'";
			if(name == null || name.length() == 0)
			{
				sql_exist_check = "select * from "
						+ TABLE_OBJECT
						+ " where " + 
						COLUMN_OBJ_TYPE + "='" + type + "'";			
			}
			
			cursor = mdb.rawQuery(sql_exist_check, null);
			if (cursor != null && cursor.getCount() > 0) 
			{
				cursor.moveToFirst();
				for(int i = 0; i < cursor.getCount();i++)
				{
					DBObjBean obj = getObjFromCursor(cursor);
					arrList.add(obj);
					cursor.moveToNext();
				}
				mdb.close();
				mdb = null;
			}


		} catch (Exception e) {
			Log.e(" ==== ", e.getMessage());
			return null;
		} finally {
			if (mdb != null)
				mdb.close();
		}
		
		return arrList;		
	}	
	

	public DBObjBean findObject(int type, long obj_id)
	{
		DBObjBean obj = null;
		SQLiteDatabase mdb = null;
		Cursor cursor = null;
		try {
			mdb = mHelper.getReadableDatabase();
			
			// duplication check
			String sql_exist_check = "select * from "
					+ TABLE_OBJECT
					+ " where " + COLUMN_OBJ_ID + "='" + obj_id + "' and " +
					COLUMN_OBJ_TYPE + "='" + type + "'";
			
			cursor = mdb.rawQuery(sql_exist_check, null);
			if (cursor != null && cursor.getCount() > 0) 
			{
				cursor.moveToFirst();
				obj = getObjFromCursor(cursor);
				mdb.close();
				mdb = null;
			}
			
				
		} catch (Exception e) {
			Log.e(" ==== ", e.getMessage());
			return null;
		} finally {
			if (mdb != null)
				mdb.close();
		}
		return obj;
	}
	public DBObjBean getObject(int id)
	{
		DBObjBean obj = null;
		SQLiteDatabase mdb = null;
		Cursor cursor = null;
		try {
			mdb = mHelper.getReadableDatabase();
			
			// duplication check
			String sql_exist_check = "select * from "
					+ TABLE_OBJECT
					+ " where " + COLUMN_ID + "='" + id + "'";
			
			cursor = mdb.rawQuery(sql_exist_check, null);
			if (cursor != null && cursor.getCount() > 0) 
			{
				cursor.moveToFirst();
				obj = getObjFromCursor(cursor);
				mdb.close();
				mdb = null;
			}
			
				
		} catch (Exception e) {
			Log.e(" ==== ", e.getMessage());
			return null;
		} finally {
			if (mdb != null)
				mdb.close();
		}
		return obj;
	}
	
	public void deleteAll(){
		SQLiteDatabase mdb = null;
		try {
			mdb = mHelper.getWritableDatabase();
			
			// duplication check
			String sql_del_qry = "delete from " + TABLE_OBJECT;
			
			mdb.execSQL(sql_del_qry);
			
		} catch (Exception e) {
			Log.e(" ==== ", e.getMessage());
		} finally {
			if (mdb != null)
				mdb.close();
		}			
	}
	
	public void deleteAllWithoutBlight(){
		SQLiteDatabase mdb = null;
		try {
			mdb = mHelper.getWritableDatabase();
			
			// duplication check
			String sql_del_qry = "delete from " + TABLE_OBJECT +
					" where " + COLUMN_OBJ_TYPE + "<>'" + DBObjBean.TYPE_BLIGHT + "'";
			mdb.execSQL(sql_del_qry);
			
		} catch (Exception e) {
			Log.e(" ==== ", e.getMessage());
		} finally {
			if (mdb != null)
				mdb.close();
		}			
	}	

	
	public void deleteObject(int id)
	{
		SQLiteDatabase mdb = null;
		try {
			mdb = mHelper.getWritableDatabase();
			
			// duplication check
			String sql_del_qry = "delete from " + TABLE_OBJECT + 
					" where " + COLUMN_ID + "='" + id + "'";
			
			mdb.execSQL(sql_del_qry);
			
		} catch (Exception e) {
			Log.e(" ==== ", e.getMessage());
		} finally {
			if (mdb != null)
				mdb.close();
		}		
	}
	public void deleteObjectsByType(int type)
	{
		SQLiteDatabase mdb = null;
		try {
			mdb = mHelper.getWritableDatabase();
			
			// duplication check
			String sql_del_qry = "delete from " + TABLE_OBJECT + 
					" where " + COLUMN_OBJ_TYPE + "='" + type + "'";
			
			mdb.execSQL(sql_del_qry);
			
		} catch (Exception e) {
			Log.e(" ==== ", e.getMessage());
		} finally {
			if (mdb != null)
				mdb.close();
		}		
	}
	public void deleteObject(int type, int obj_id)
	{
		SQLiteDatabase mdb = null;
		try {
			mdb = mHelper.getWritableDatabase();
			
			// duplication check
			String sql_del_qry = "delete from " + TABLE_OBJECT 
					+ " where " + COLUMN_OBJ_ID + "='" + obj_id + "' and " +
					COLUMN_OBJ_TYPE + "='" + type + "'";
			
			mdb.execSQL(sql_del_qry);
			
		} catch (Exception e) {
			Log.e(" ==== ", e.getMessage());
		} finally {
			if (mdb != null)
				mdb.close();
		}		
	}		
}
