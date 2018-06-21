package com.example.abel.lib;

import android.database.Cursor;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "Medib_Key_Value";
    private static final String TABLE_NAME = "Store";

    private static String COLUMN_KEY = "key";
    private static String COLUMN_VALUE = "value";

    public static int VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String query = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_KEY + " TEXT UNIQUE, " + COLUMN_VALUE + " TEXT )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    /*
     * @description stores a key value pair in database
     * @param key String
     * @param value String
     * @return boolean (is the data stored?)
     */

    public boolean store(String key, String value){
        ContentValues keyValue = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        keyValue.put(COLUMN_KEY, key);
        keyValue.put(COLUMN_VALUE, value);

        if(db.insert(TABLE_NAME, null, keyValue) != -1)
            return true;

        return false;
    }

    /*
     * @description delete a key value pair in database
     * @param key String
     * @return boolean (is the data stored?)
     */
    public void delete(String key){
        String[] whereArgs = { key };
        this.getWritableDatabase().delete(TABLE_NAME, COLUMN_KEY+"=?", whereArgs);
    }

    /*
     * @description gets a value from a given key
     * @param key String
     * @return value String
     * @note - returns null if there is no key by the name key
     */
    public String get(String key){
        Cursor cursor;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { COLUMN_VALUE };
        String[] selections = { key };
        cursor = db.query(
                TABLE_NAME,
                columns,
                COLUMN_KEY+"=?",
                selections,
                null,
                null,
                null,
                null);

        if(cursor != null && cursor.moveToFirst()){
            return cursor.getString(0);
        }
        return null;
    }
}
