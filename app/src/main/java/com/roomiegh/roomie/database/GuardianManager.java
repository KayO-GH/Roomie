package com.roomiegh.roomie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.roomiegh.roomie.models.Guardian;
import com.roomiegh.roomie.util.DBUtil;

import java.util.ArrayList;

/**
 * Created by Kwadwo Agyapon-Ntra on 01/08/2015.
 */
public class GuardianManager {
    private RoomieDbHelper dBHelper;
    private Context context;
    private SQLiteDatabase db;
    private boolean isOk = false;
    private Guardian guardian;
    private ArrayList<Guardian> allGuardians;
    private ContentValues cv;
    private Cursor cr;

    private int iGuardFName, iGuardLName, iGuardPhone, iGuardPhone2,
            iGuardOccupation, iGuardAddress, iRefNo;

    private String whereConditions;
    private String[] whereArgs;
    private long  rowsEffected;

    private String[] columns = new String[]{
            DBUtil.COLUMN_GUARD_F_NAME,DBUtil.COLUMN_GUARD_L_NAME,
            DBUtil.COLUMN_GUARD_PHONE, DBUtil.COLUMN_GUARD_PHONE_2,
            DBUtil.COLUMN_GUARD_OCCUPATION,DBUtil.COLUMN_GUARD_ADDRESS,
            DBUtil.COLUMN_REF_NO
    };


    public GuardianManager(Context context) {
        this.context = context;
        dBHelper = new RoomieDbHelper(context);//creates tables for us
        // guardian = new Guardian();
        allGuardians = new ArrayList<>();
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

    public boolean createGuardian(Guardian guardian){
        openForWrite();
        cv.put(DBUtil.COLUMN_GUARD_F_NAME, guardian.getGuardFName());
        cv.put(DBUtil.COLUMN_GUARD_L_NAME, guardian.getGuardLName());
        cv.put(DBUtil.COLUMN_GUARD_PHONE, guardian.getGuardPhone());
        cv.put(DBUtil.COLUMN_GUARD_PHONE_2, guardian.getGuardPhone2());
        cv.put(DBUtil.COLUMN_GUARD_OCCUPATION, guardian.getGuardOccupation());
        cv.put(DBUtil.COLUMN_GUARD_ADDRESS, guardian.getGuardAddress());
        cv.put(DBUtil.COLUMN_REF_NO, guardian.getRefNo());


        rowsEffected = db.insert(DBUtil.TENANT_GUARDIAN_TABLE, null, cv);
        if (rowsEffected!=-1){
            isOk=true;
        }
        close();
        return isOk;
    }

    public boolean updateGuardian(Guardian guardian){
        /*
        * update guardian_tbl where title=?
        */
        openForWrite();
        whereConditions = DBUtil.COLUMN_REF_NO+"=?";
        whereArgs = new String[]{String.valueOf(guardian.getRefNo())};

        cv.put(DBUtil.COLUMN_GUARD_F_NAME, guardian.getGuardFName());
        cv.put(DBUtil.COLUMN_GUARD_L_NAME, guardian.getGuardLName());
        cv.put(DBUtil.COLUMN_GUARD_PHONE, guardian.getGuardPhone());
        cv.put(DBUtil.COLUMN_GUARD_PHONE_2, guardian.getGuardPhone2());
        cv.put(DBUtil.COLUMN_GUARD_OCCUPATION, guardian.getGuardOccupation());
        cv.put(DBUtil.COLUMN_GUARD_ADDRESS, guardian.getGuardAddress());
        cv.put(DBUtil.COLUMN_REF_NO, guardian.getRefNo());

        rowsEffected = db.update(DBUtil.TENANT_GUARDIAN_TABLE, cv, whereConditions, whereArgs);
        if (rowsEffected!=0){
            isOk=true;
        }

        close();
        return isOk;
    }

    public boolean deleteGuardian(Guardian guardian){
        openForWrite();
        whereConditions = DBUtil.COLUMN_REF_NO+"=?";
        whereArgs = new String[]{String.valueOf(guardian.getRefNo())};

        rowsEffected = db.delete(DBUtil.TENANT_GUARDIAN_TABLE, whereConditions, whereArgs);
        if (rowsEffected!=0){
            isOk=true;
        }
        close();
        return isOk;
    }

    public Guardian getGuardian(Guardian guardian){
        openForRead();
        whereConditions = DBUtil.COLUMN_REF_NO+"=?" ;
        whereArgs = new String[]{String.valueOf(guardian.getRefNo())};

        cr = db.query(DBUtil.TENANT_GUARDIAN_TABLE, columns, whereConditions, whereArgs, null, null, null);
        iGuardFName = cr.getColumnIndex(DBUtil.COLUMN_GUARD_F_NAME);
        iGuardLName  = cr.getColumnIndex(DBUtil.COLUMN_GUARD_L_NAME);
        iGuardPhone = cr.getColumnIndex(DBUtil.COLUMN_GUARD_PHONE);
        iGuardPhone2 = cr.getColumnIndex(DBUtil.COLUMN_GUARD_PHONE_2);
        iGuardOccupation  = cr.getColumnIndex(DBUtil.COLUMN_GUARD_OCCUPATION);
        iGuardAddress = cr.getColumnIndex(DBUtil.COLUMN_GUARD_ADDRESS);
        iRefNo = cr.getColumnIndex(DBUtil.COLUMN_REF_NO);

        if(cr.getCount()<1)
            return null;

        if (cr!=null){
            cr.moveToFirst();
            this.guardian = new Guardian(
                    cr.getString(iGuardFName),cr.getString(iGuardLName),
                    cr.getString(iGuardPhone),cr.getString(iGuardPhone2),
                    cr.getString(iGuardOccupation),cr.getString(iGuardAddress),
                    cr.getInt(iRefNo)
            );
        }

        close();
        return this.guardian;
    }

    public ArrayList<Guardian>getAllGuardians(){
        openForRead();
        cr = db.query(DBUtil.TENANT_GUARDIAN_TABLE, columns, null, null, null, null, null);

        iGuardFName = cr.getColumnIndex(DBUtil.COLUMN_GUARD_F_NAME);
        iGuardLName  = cr.getColumnIndex(DBUtil.COLUMN_GUARD_L_NAME);
        iGuardPhone = cr.getColumnIndex(DBUtil.COLUMN_GUARD_PHONE);
        iGuardPhone2 = cr.getColumnIndex(DBUtil.COLUMN_GUARD_PHONE_2);
        iGuardOccupation  = cr.getColumnIndex(DBUtil.COLUMN_GUARD_OCCUPATION);
        iGuardAddress = cr.getColumnIndex(DBUtil.COLUMN_GUARD_ADDRESS);
        iRefNo = cr.getColumnIndex(DBUtil.COLUMN_REF_NO);

        for (cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()){
            guardian = new Guardian(
                    cr.getString(iGuardFName),cr.getString(iGuardLName),
                    cr.getString(iGuardPhone),cr.getString(iGuardPhone2),
                    cr.getString(iGuardOccupation),cr.getString(iGuardAddress),
                    cr.getInt(iRefNo)
            );
            allGuardians.add(guardian);
        }

        close();
        return allGuardians;
    }
}
