package com.example.journey.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Li on 2016/8/16.
 */
public class JoruneyOpenHelper extends SQLiteOpenHelper {

    /* Province表建表语句 */
    public static final String CREATE_PROVINCE = "create table Province("
            +"id integer primary key autoincrement,"
            +"province_name text,"
            +"province_code text)";

    /* City 表建表语句 */
    public static final String CREATE_CITY = "create table City("
            +"id integer primary key autoincrement,"
            +"city_name text,"
            +"city_code text,"
            +"province_id integer)";

    /* User 表建表语句 */
   /* public static final String CREATE_USER = "create table City("
            +"id integer primary key autoincrement,"
            +"user_id text,"
            +"user_name text,"
            +"user_icon blob,"
            +"user_age integer,"
            +"user_token text,"
            +"user_tokensecret text,"
            +"user_score real,"
            +"user_resume text)";*/

    private Context mContext;

    public JoruneyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        //db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
