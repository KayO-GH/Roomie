package com.roomiegh.roomie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.roomiegh.roomie.models.Tenant;
import com.roomiegh.roomie.util.DBUtil;

import java.util.ArrayList;

/**
 * Created by Kwadwo Agyapon-Ntra on 01/08/2015.
 */
public class TenantManager {
    private RoomieDbHelper dBHelper;
    private Context context;
    private SQLiteDatabase db;
    private boolean isOk = false;
    private Tenant tenant;
    private ArrayList<Tenant> allTenants;
    private ContentValues cv;
    private Cursor cr;

    private int iRefNo, iFName, iLName,
            iNationality, iGender, iPhone, iPhone2, iEmail, iPhoto, iDOB;

    private String whereConditions;
    private String[] whereArgs;
    private long  rowsEffected;

    private String[] columns = new String[]{
            DBUtil.COLUMN_REF_NO, DBUtil.COLUMN_F_NAME,
            DBUtil.COLUMN_L_NAME, DBUtil.COLUMN_NATIONALITY,
            DBUtil.COLUMN_GENDER, DBUtil.COLUMN_PHONE,
            DBUtil.COLUMN_PHONE_2,DBUtil.COLUMN_EMAIL,
            DBUtil.COLUMN_PHOTO,DBUtil.COLUMN_DOB
    };


    public TenantManager(Context context) {
        this.context = context;
        dBHelper = new RoomieDbHelper(context);//creates tables for us
        // tenant = new Tenant();
        allTenants = new ArrayList<>();
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

    public boolean createTenant(Tenant tenant){
        openForWrite();
        cv.put(DBUtil.COLUMN_REF_NO, tenant.getRefNo());
        cv.put(DBUtil.COLUMN_F_NAME, tenant.getfName());
        cv.put(DBUtil.COLUMN_L_NAME, tenant.getlName());
        cv.put(DBUtil.COLUMN_NATIONALITY, tenant.getNationality());
        cv.put(DBUtil.COLUMN_GENDER, tenant.getGender());
        cv.put(DBUtil.COLUMN_PHONE, tenant.getPhone());
        cv.put(DBUtil.COLUMN_PHONE_2, tenant.getPhone2());
        cv.put(DBUtil.COLUMN_EMAIL, tenant.getEmail());
        cv.put(DBUtil.COLUMN_PHOTO, tenant.getPhoto());
        cv.put(DBUtil.COLUMN_DOB, tenant.getDob());

        rowsEffected = db.insert(DBUtil.TENANT_PERSONAL_TABLE, null, cv);
        if (rowsEffected!=-1){
            isOk=true;
        }
        close();
        return isOk;
    }

    public boolean updateTenant(Tenant tenant){
        /*
        * update tenant_tbl where title=?
        */
        openForWrite();
        whereConditions = DBUtil.COLUMN_REF_NO+"=?";
        whereArgs = new String[]{String.valueOf(tenant.getRefNo())};

        cv.put(DBUtil.COLUMN_F_NAME, tenant.getfName());
        cv.put(DBUtil.COLUMN_L_NAME, tenant.getlName());
        cv.put(DBUtil.COLUMN_NATIONALITY, tenant.getNationality());
        cv.put(DBUtil.COLUMN_GENDER, tenant.getGender());
        cv.put(DBUtil.COLUMN_PHONE, tenant.getPhone());
        cv.put(DBUtil.COLUMN_PHONE_2, tenant.getPhone2());
        cv.put(DBUtil.COLUMN_EMAIL, tenant.getEmail());
        cv.put(DBUtil.COLUMN_PHOTO, tenant.getPhoto());
        cv.put(DBUtil.COLUMN_DOB, tenant.getDob());

        rowsEffected = db.update(DBUtil.TENANT_PERSONAL_TABLE, cv, whereConditions, whereArgs);
        if (rowsEffected!=0){
            isOk=true;
        }

        close();
        return isOk;
    }

    public boolean deleteTenant(Tenant tenant){
        openForWrite();
        whereConditions = DBUtil.COLUMN_REF_NO+"=?";
        whereArgs = new String[]{String.valueOf(tenant.getRefNo())};

        rowsEffected = db.delete(DBUtil.TENANT_PERSONAL_TABLE, whereConditions, whereArgs);
        if (rowsEffected!=0){
            isOk=true;
        }
        close();
        return isOk;
    }

    public Tenant getTenantByEmail(Tenant tenant){
        openForRead();
        whereConditions = DBUtil.COLUMN_EMAIL+ "=?" ;
        whereArgs = new String[]{tenant.getEmail()};

        cr = db.query(DBUtil.TENANT_PERSONAL_TABLE, columns, whereConditions, whereArgs, null, null, null);
        iRefNo = cr.getColumnIndex(DBUtil.COLUMN_REF_NO);
        iFName = cr.getColumnIndex(DBUtil.COLUMN_F_NAME);
        iLName = cr.getColumnIndex(DBUtil.COLUMN_L_NAME);
        iNationality = cr.getColumnIndex(DBUtil.COLUMN_NATIONALITY);
        iGender = cr.getColumnIndex(DBUtil.COLUMN_GENDER);
        iPhone = cr.getColumnIndex(DBUtil.COLUMN_PHONE);
        iPhone2 = cr.getColumnIndex(DBUtil.COLUMN_PHONE_2);
        iEmail = cr.getColumnIndex(DBUtil.COLUMN_EMAIL);
        iPhoto = cr.getColumnIndex(DBUtil.COLUMN_PHOTO);
        iDOB = cr.getColumnIndex(DBUtil.COLUMN_DOB);


        if(cr.getCount()<1)
            return null;

        if (cr!=null){
            cr.moveToFirst();
            this.tenant = new Tenant(
                    cr.getString(iFName),cr.getString(iLName),
                    cr.getString(iNationality),cr.getString(iGender),
                    cr.getString(iPhone),cr.getString(iPhone2),
                    cr.getString(iEmail),cr.getString(iDOB),
                    cr.getBlob(iPhoto),cr.getInt(iRefNo)
                    );
        }

        close();
        return this.tenant;
    }

    public ArrayList<Tenant>getAllTenants(){
        openForRead();
        cr = db.query(DBUtil.TENANT_PERSONAL_TABLE, columns, null, null, null, null, null);

        iRefNo = cr.getColumnIndex(DBUtil.COLUMN_REF_NO);
        iFName = cr.getColumnIndex(DBUtil.COLUMN_F_NAME);
        iLName = cr.getColumnIndex(DBUtil.COLUMN_L_NAME);
        iNationality = cr.getColumnIndex(DBUtil.COLUMN_NATIONALITY);
        iGender = cr.getColumnIndex(DBUtil.COLUMN_GENDER);
        iPhone = cr.getColumnIndex(DBUtil.COLUMN_PHONE);
        iPhone2 = cr.getColumnIndex(DBUtil.COLUMN_PHONE_2);
        iEmail = cr.getColumnIndex(DBUtil.COLUMN_EMAIL);
        iPhoto = cr.getColumnIndex(DBUtil.COLUMN_PHOTO);
        iDOB = cr.getColumnIndex(DBUtil.COLUMN_DOB);

        for (cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()){
            tenant = new Tenant(
                    cr.getString(iFName),cr.getString(iLName),
                    cr.getString(iNationality),cr.getString(iGender),
                    cr.getString(iPhone),cr.getString(iPhone2),
                    cr.getString(iEmail),cr.getString(iDOB),
                    cr.getBlob(iPhoto),cr.getInt(iRefNo)
            );
            allTenants.add(tenant);
        }

        close();
        return allTenants;
    }

}
