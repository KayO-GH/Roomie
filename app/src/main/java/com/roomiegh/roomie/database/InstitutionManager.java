package com.roomiegh.roomie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.roomiegh.roomie.models.Institution;
import com.roomiegh.roomie.util.DBUtil;

import java.util.ArrayList;

/**
 * Created by Kwadwo Agyapon-Ntra on 01/08/2015.
 */
public class InstitutionManager {
    private RoomieDbHelper dBHelper;
    private Context context;
    private SQLiteDatabase db;
    private boolean isOk = false;
    private Institution institution;
    private ArrayList<Institution> allInstitutions;
    private ContentValues cv;
    private Cursor cr;

    private int iInsName,iInsID;
    private String whereConditions;
    private String[] whereArgs;
    private long  rowsEffected;

    private String[] columns = new String[]{
            DBUtil.COLUMN_INS_ID, DBUtil.COLUMN_INS_NAME
    };


    public InstitutionManager(Context context) {
        this.context = context;
        dBHelper = new RoomieDbHelper(context);//creates tables for us
        // institution = new Institution();
        allInstitutions = new ArrayList<>();
        cv = new ContentValues();

    }

    private void openForRead(){
        db = dBHelper.getReadableDatabase();
    }

    private void openForWrite(){
        db =dBHelper.getWritableDatabase();
    }

    private void close(){
        db.close();
        dBHelper.close();
    }

    public boolean createInstitution(Institution institution){
        openForWrite();
        cv.put(DBUtil.COLUMN_INS_NAME, institution.getInsName());
        
        rowsEffected = db.insert(DBUtil.INSTITUTION_TABLE, null, cv);
        if (rowsEffected!=-1){
            isOk=true;
        }
        close();
        return isOk;
    }

    public boolean updateInstitution(Institution institution){
        /*
        * update institution_tbl where title=?
        */
        openForWrite();
        whereConditions = DBUtil.COLUMN_INS_NAME+"=?";
        whereArgs = new String[]{String.valueOf(institution.getInsName())};

        cv.put(DBUtil.COLUMN_INS_NAME, institution.getInsName());

        rowsEffected = db.update(DBUtil.INSTITUTION_TABLE, cv, whereConditions, whereArgs);
        if (rowsEffected!=0){
            isOk=true;
        }

        close();
        return isOk;
    }

    public boolean deleteInstitution(Institution institution){
        openForWrite();
        whereConditions = DBUtil.COLUMN_INS_NAME+"=?";
        whereArgs = new String[]{String.valueOf(institution.getInsName())};

        rowsEffected = db.delete(DBUtil.INSTITUTION_TABLE, whereConditions, whereArgs);
        if (rowsEffected!=0){
            isOk=true;
        }
        close();
        return isOk;
    }

    public Institution getInstitution(Institution institution){
        openForRead();
        whereConditions = DBUtil.COLUMN_INS_NAME+" like '%" + institution.getInsName() + "%'" ;
        //add whereArgs and use in query

        cr = db.query(DBUtil.INSTITUTION_TABLE, columns, whereConditions, null, null, null, null);
        iInsName = cr.getColumnIndex(DBUtil.COLUMN_INS_NAME);


        if (cr!=null){
            cr.moveToFirst();
            this.institution = new Institution(
                    cr.getString(iInsName)
            );
        }

        close();
        return this.institution;
    }

    public ArrayList<Institution>getAllInstitutions(){
        openForRead();
        cr = db.query(DBUtil.INSTITUTION_TABLE, columns, null, null, null, null, null);

        iInsName = cr.getColumnIndex(DBUtil.COLUMN_INS_NAME);

        for (cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()){
            institution = new Institution(
                    cr.getString(iInsName)
            );
            allInstitutions.add(institution);
        }

        close();
        return allInstitutions;
    }

}
