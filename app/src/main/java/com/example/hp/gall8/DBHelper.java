package com.example.hp.gall8;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Gall8.db";
    public static final String TABLE_NAME = "User_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "NAME";
    public static final String COL_5 = "EMAIL";
    public static final String COL_6 = "USERLEVEL";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, NAME TEXT, EMAIL TEXT, USERLEVEL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String username, String password, String email, String name, String userlevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, password);
        contentValues.put(COL_4, name);
        contentValues.put(COL_5, email);
        contentValues.put(COL_6, userlevel);

        long result = db.insert(TABLE_NAME, null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME , null);
        return res;
    }

    public Cursor getInfo(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME  + " WHERE USERNAME = " + "'"+username+"'", null);
        int cursorCount = res.getCount();

        return res;
    }


    public Cursor getName() {

        String[] columns = {
                COL_1
        };

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME , null);
        return res;
    }

    public boolean checkUser(String user) {

        // array of columns to fetch
        String[] columns = {
                COL_1
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COL_2 + " = ?";

        // selection argument
        String[] selectionArgs = {user};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param user
     * @param password
     * @return true/false
     */
    public boolean checkUser1(String user, String password) {

        // array of columns to fetch
        String[] columns = {
                COL_1
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COL_2 + " = ?" + " AND " + COL_3 + " = ?";


        String[] selectionArgs = {user, password};


        Cursor cursor = db.query(TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}
