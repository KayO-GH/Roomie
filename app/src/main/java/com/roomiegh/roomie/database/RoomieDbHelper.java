package com.roomiegh.roomie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.roomiegh.roomie.util.DBUtil;

/**
 * Created by Kwadwo Agyapon-Ntra on 30/07/2015.
 */
public class RoomieDbHelper extends SQLiteOpenHelper{

    public RoomieDbHelper(Context context){
        super(context, DBUtil.DB_NAME,null,DBUtil.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBUtil.CREATE_INSTITUTION_TABLE);
        db.execSQL(DBUtil.CREATE_TENANT_EDU_TABLE);
        db.execSQL(DBUtil.CREATE_TENANT_GUARDIAN_TABLE);
        db.execSQL(DBUtil.CREATE_TENANT_PERSONAL_TABLE);
        db.execSQL(DBUtil.CREATE_SIGN_IN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion==DBUtil.DB_VERSION){
            db.execSQL("DROP TABLE IF EXISTS '"+DBUtil.INSTITUTION_TABLE +"'");
            db.execSQL("DROP TABLE IF EXISTS '"+DBUtil.TENANT_EDU_TABLE +"'");
            db.execSQL("DROP TABLE IF EXISTS '"+DBUtil.TENANT_GUARDIAN_TABLE +"'");
            db.execSQL("DROP TABLE IF EXISTS '"+DBUtil.TENANT_PERSONAL_TABLE +"'");
            db.execSQL("DROP TABLE IF EXISTS '"+DBUtil.SIGN_IN_TABLE +"'");
            onCreate(db);
        }
    }
}
