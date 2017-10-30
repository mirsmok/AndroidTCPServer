package com.example.android_tcp_server;

/**
 * Created by mirsmok on 25.10.17.
 */


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "IQHomeData";

    // Contacts table name
    private static final String TABLE_HEATING = "Heating";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_VALUE = "value";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    };
    public void initHeating(){
        if(!isHeatingTableExist()) {
            addHeatingData("setpoint", "22.2");
            addHeatingData("setpointId","1002");
            addHeatingData("processTemperature","21.0");
            addHeatingData("processTemperatureId","2001");
            addHeatingData("waterLoop","40.0");
            addHeatingData("waterLoopId","1002");
            addHeatingData("state","OFF");
            addHeatingData("controll","remote");
            addHeatingData("setpointSource","remote");
            addHeatingData("outputMode","automatic");
        }
    };
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_HEATING + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_VALUE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEATING);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    public boolean isHeatingTableExist(){

        //SQLiteDatabase db = this.getWritableDatabase();
        String result=this.getHeatingData("setpoint");
        if(result!=null && result!="")
            return true;
        else
		    return false;
    }

    // Adding new contact
    public void addHeatingData(String Name, String Value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Name); // Contact Name
        values.put(KEY_VALUE, Value); // Contact Phone

        // Inserting Row
        db.insert(TABLE_HEATING, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public String getHeatingData(String Name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String value;
        Cursor cursor = db.query(TABLE_HEATING, new String[] { KEY_ID,
                        KEY_NAME, KEY_VALUE }, KEY_NAME + "=?",
                new String[] { Name }, null, null, null, null);
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            value=cursor.getString(2);
            cursor.close();
            return value;
            }
        else
            return "";

        // return value
    }
/*
    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
*/
    // Updating single row
    public int setHeatingData(String Name, String Value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VALUE, Value);

        // updating row
        return db.update(TABLE_HEATING, values, KEY_NAME + " = ?",
                new String[] { Name });
    }

    // Deleting single contact
    public void deleteHeatingData(String Name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HEATING, KEY_NAME + " = ?",
                new String[] { Name });
        db.close();
    }

/*
    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
*/
}