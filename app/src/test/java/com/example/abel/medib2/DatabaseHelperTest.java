package com.example.abel.medib2;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Abel on 6/30/2018.
 */

public class DatabaseHelperTest {
    public class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "Medib_Key_Value";
        private static final String TABLE_NAME = "Store";

        private String COLUMN_KEY = "key";
        private String COLUMN_VALUE = "value";

        public   int VERSION = 1;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        public boolean store(String key, String value , int insertStatus) { //modified to mock db.insert which returns int value
             ContentValues keyValue = new ContentValues();
            SQLiteDatabase db = this.getWritableDatabase();

            keyValue.put(COLUMN_KEY, key);
            keyValue.put(COLUMN_VALUE, value);

            if (insertStatus != -1)
                return true;

            return false;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
        public String get(Cursor cursor){ //modified to mock db operations (cursor passed as argument {originally computed by calling db.query})

            if(cursor != null && cursor.moveToFirst()){
                return cursor.getString(0);
            }
            return null;
        }
    }


    @Test
    //test boolean store()
    //test1 (insertStatus != -1 (db.insert successful)) test if store returns true
    public void test1(){

        Context c = InstrumentationRegistry.getTargetContext();
        DatabaseHelper dh = new DatabaseHelper(c);

        assertEquals(true ,dh.store("key1" , "value1" , 1) );
    }
    @Test
    //test2 (insertStatus == -1 (db.insert failed )) test if store returns false
    public void test2(){
        Context c = InstrumentationRegistry.getTargetContext();
        DatabaseHelper dh = new DatabaseHelper(c);

        assertEquals(false ,dh.store("key1" , "value1" , -1) );

    }

    //test String get(String key)
    @Test
    //test1 (cursor != null , cursor.moveToFirst == true) test if it returns a string
    public void  test3(){
        Context c = InstrumentationRegistry.getTargetContext();
        DatabaseHelper dh = new DatabaseHelper(c);

        Cursor cu = new Cursor() {
            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public int getPosition() {
                return 0;
            }

            @Override
            public boolean move(int i) {
                return false;
            }

            @Override
            public boolean moveToPosition(int i) {
                return false;
            }

            @Override
            public boolean moveToFirst() {
                return true;
            }

            @Override
            public boolean moveToLast() {
                return false;
            }

            @Override
            public boolean moveToNext() {
                return false;
            }

            @Override
            public boolean moveToPrevious() {
                return false;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean isBeforeFirst() {
                return false;
            }

            @Override
            public boolean isAfterLast() {
                return false;
            }

            @Override
            public int getColumnIndex(String s) {
                return 0;
            }

            @Override
            public int getColumnIndexOrThrow(String s) throws IllegalArgumentException {
                return 0;
            }

            @Override
            public String getColumnName(int i) {
                return null;
            }

            @Override
            public String[] getColumnNames() {
                return new String[0];
            }

            @Override
            public int getColumnCount() {
                return 0;
            }

            @Override
            public byte[] getBlob(int i) {
                return new byte[0];
            }

            @Override
            public String getString(int i) {
                return "mockedReturn";
            }

            @Override
            public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {

            }

            @Override
            public short getShort(int i) {
                return 0;
            }

            @Override
            public int getInt(int i) {
                return 0;
            }

            @Override
            public long getLong(int i) {
                return 0;
            }

            @Override
            public float getFloat(int i) {
                return 0;
            }

            @Override
            public double getDouble(int i) {
                return 0;
            }

            @Override
            public int getType(int i) {
                return 0;
            }

            @Override
            public boolean isNull(int i) {
                return false;
            }

            @Override
            public void deactivate() {

            }

            @Override
            public boolean requery() {
                return false;
            }

            @Override
            public void close() {

            }

            @Override
            public boolean isClosed() {
                return false;
            }

            @Override
            public void registerContentObserver(ContentObserver contentObserver) {

            }

            @Override
            public void unregisterContentObserver(ContentObserver contentObserver) {

            }

            @Override
            public void registerDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public void setNotificationUri(ContentResolver contentResolver, Uri uri) {

            }

            @Override
            public Uri getNotificationUri() {
                return null;
            }

            @Override
            public boolean getWantsAllOnMoveCalls() {
                return false;
            }

            @Override
            public void setExtras(Bundle bundle) {

            }

            @Override
            public Bundle getExtras() {
                return null;
            }

            @Override
            public Bundle respond(Bundle bundle) {
                return null;
            }
        }; //getString modified to return mockedReturn
        assertEquals(String.class ,dh.get(cu).getClass() );
        assertEquals("mockedReturn" ,dh.get(cu) );
    }
}