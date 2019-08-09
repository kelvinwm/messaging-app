package com.example.lenovo.timsntsaapp;

/**
 * Created by lenovo on 12/20/2017.
 */

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contactss";
    private static final String TABLE_ACTIVATE = "activatekey";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ACTIVE = "active";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";

        String CREATE= "CREATE TABLE " + TABLE_ACTIVATE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";


        db.execSQL(CREATE);
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVATE);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void addContact(String name, String number) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Contact Name
        values.put(KEY_PH_NO,number); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

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

    // Deleting single contact
    public void deleteContact(String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_NAME + " = ?",
                new String[] { String.valueOf(contact) });
        db.close();
    }

    public void updateActivation(String key) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, key);
        db.update(TABLE_ACTIVATE, args, KEY_PH_NO + "=" +"5", null);
        db.close();
    }
    // Adding new contact
    public void addActivation(String ls) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,ls); // Contact Name
        values.put(KEY_PH_NO, "5"); // Contact Name

        // Inserting Row
        db.insert(TABLE_ACTIVATE, null, values);
        db.close(); // Closing database connection
    }

    // Getting activation key
    public String getActivationkey() {
        List<Contact> contactList = new ArrayList<Contact>();
        String keyy = null;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACTIVATE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.set_active(cursor.getString(1));
                keyy = cursor.getString(1);
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return keyy;
    }
}

