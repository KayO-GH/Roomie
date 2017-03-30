package com.roomiegh.roomie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.roomiegh.roomie.models.SignIn;
import com.roomiegh.roomie.util.DBUtil;

import java.util.ArrayList;

/**
 * Created by Kwadwo Agyapon-Ntra on 04/08/2015.
 */
public class SignInManager {
    private RoomieDbHelper dBHelper;
    private Context context;
    private SQLiteDatabase db;
    private boolean isOk = false;
    private SignIn signIn;
    private ArrayList<SignIn> allSignIns;
    private ContentValues cv;
    private Cursor cr;

    private int iEmail, iPassword, iSignInTime, iSignOutTime;

    private String whereConditions;
    private String[] whereArgs;
    private long  rowsEffected;

    private String[] columns = new String[]{
            DBUtil.COLUMN_EMAIL, DBUtil.COLUMN_PASSWORD,
            DBUtil.COLUMN_SIGN_IN_TIME, DBUtil.COLUMN_SIGN_OUT_TIME
    };


    public SignInManager(Context context) {
        this.context = context;
        dBHelper = new RoomieDbHelper(context);//creates tables for us
        // signIn = new SignIn();
        allSignIns = new ArrayList<>();
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

    public boolean createSignIn(SignIn signIn){
        openForWrite();
        cv.put(DBUtil.COLUMN_EMAIL, signIn.getEmail());
        cv.put(DBUtil.COLUMN_PASSWORD, signIn.getPassword());
        cv.put(DBUtil.COLUMN_SIGN_IN_TIME, String.valueOf(signIn.getSignInTme()));
        cv.put(DBUtil.COLUMN_SIGN_OUT_TIME, String.valueOf(signIn.getSignOutTime()));


        rowsEffected = db.insert(DBUtil.SIGN_IN_TABLE, null, cv);
        if (rowsEffected!=-1){
            isOk=true;
        }
        close();
        return isOk;
    }

    public boolean updateSignIn(SignIn signIn){
        /*
        * update signIn_tbl where title=?
        */
        openForWrite();
        whereConditions = DBUtil.COLUMN_EMAIL+"=?";
        whereArgs = new String[]{signIn.getEmail()};

        cv.put(DBUtil.COLUMN_EMAIL, signIn.getEmail());
        cv.put(DBUtil.COLUMN_PASSWORD, signIn.getPassword());
        cv.put(DBUtil.COLUMN_SIGN_IN_TIME, String.valueOf(signIn.getSignInTme()));
        cv.put(DBUtil.COLUMN_SIGN_OUT_TIME, String.valueOf(signIn.getSignOutTime()));

        rowsEffected = db.update(DBUtil.SIGN_IN_TABLE, cv, whereConditions, whereArgs);
        if (rowsEffected!=0){
            isOk=true;
        }

        close();
        return isOk;
    }

    public boolean deleteSignIn(SignIn signIn){
        openForWrite();
        whereConditions = DBUtil.COLUMN_EMAIL+"=?";
        whereArgs = new String[]{signIn.getEmail()};

        rowsEffected = db.delete(DBUtil.SIGN_IN_TABLE, whereConditions, whereArgs);
        if (rowsEffected!=0){
            isOk=true;
        }
        close();
        return isOk;
    }

    public SignIn getSignIn(SignIn signIn){
        openForRead();
        whereConditions = DBUtil.COLUMN_EMAIL+"=?" ;//used to be COLUMN_SIGN_IN_ID
        whereArgs = new String[]{signIn.getEmail()};

        cr = db.query(DBUtil.SIGN_IN_TABLE, columns, whereConditions, whereArgs, null, null, null);
        //int iID = cr.getColumnIndex(DBUtil.COLUMN_SIGN_IN_ID);//adding to debug android.database.CursorIndexOutOfBoundsException: Index -1 requested, with a size of 1
        iEmail = cr.getColumnIndex(DBUtil.COLUMN_EMAIL);
        iPassword = cr.getColumnIndex(DBUtil.COLUMN_PASSWORD);
        iSignInTime = cr.getColumnIndex(DBUtil.COLUMN_SIGN_IN_TIME);
        iSignOutTime = cr.getColumnIndex(DBUtil.COLUMN_SIGN_OUT_TIME);

        //debugging cursor with no values, i.e. no signin found for the given email address
        if(cr.getCount()<1)
            return null;

        if (cr!=null){
            cr.moveToFirst();
            /*make to move to first or else you'll get this error:
            FATAL EXCEPTION: main
            android.database.CursorIndexOutOfBoundsException: Index -1 requested, with a size of 1*/
            this.signIn = new SignIn(
                    cr.getString(iEmail),cr.getString(iPassword),
                    cr.getString(iSignInTime),cr.getString(iSignOutTime)
            );
        }

        close();
        return this.signIn;//was this.signIn
    }

    public ArrayList<SignIn>getAllSignIns(){
        openForRead();
        cr = db.query(DBUtil.SIGN_IN_TABLE, columns, null, null, null, null, null);

        iEmail = cr.getColumnIndex(DBUtil.COLUMN_EMAIL);
        iPassword = cr.getColumnIndex(DBUtil.COLUMN_PASSWORD);
        iSignInTime = cr.getColumnIndex(DBUtil.COLUMN_SIGN_IN_TIME);
        iSignOutTime = cr.getColumnIndex(DBUtil.COLUMN_SIGN_OUT_TIME);

        for (cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()){
            signIn = new SignIn(
                    cr.getString(iEmail),cr.getString(iPassword),
                    cr.getString(iSignInTime),cr.getString(iSignOutTime)
            );
            allSignIns.add(signIn);
        }

        close();
        return allSignIns;
    }
}
