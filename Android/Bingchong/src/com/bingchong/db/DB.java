package com.bingchong.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB extends SQLiteOpenHelper {
	
	public static final String CREATE_DB_SQL_PREFIX = "CREATE TABLE IF NOT EXISTS ";
	public static final String DELETE_DB_SQL_PREFIX = "DROP TABLE IF EXISTS ";

	public final static String DATABASE_NAME = "bingchong.db";
	
	private TblObject  tblObject = null;

	public DB(Context context) {
		super(context, DATABASE_NAME, null, 1);
		tblObject = new TblObject(this);
	}
	
	public TblObject getTblObject(){ return tblObject;}

	public final void onCreate(SQLiteDatabase db) {
		tblObject.onCreate(db, CREATE_DB_SQL_PREFIX);
	}

	public final void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		Log.w("---[DEBUG]----", "Upgrading database from version " + oldVersion
				+ " to " + newVersion + ", which will destroy all old data");
		
		
		tblObject.onUpgrade(db, oldVersion, newVersion, DELETE_DB_SQL_PREFIX);
		onCreate(db);
	}
}
