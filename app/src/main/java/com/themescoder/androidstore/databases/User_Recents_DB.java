package com.themescoder.androidstore.databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


/**
 * User_Recents_DB creates the table User_Recents and handles all CRUD operations relevant to User_Recents
 **/

public class User_Recents_DB {

    // Table Name
    public static final String TABLE_RECENTS = "User_Recents";
    // Table Columns
    public static final String PRODUCT_ID = "products_id";



    //*********** Returns the Query to Create TABLE_RECENTS ********//

    public static String createTable() {

        return "CREATE TABLE "+ TABLE_RECENTS
                + "("
                + PRODUCT_ID    +" INTEGER"
                + ")";
    }



    //*********** Insert New Recent Item ********//

    public void insertRecentItem(int products_id) {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        SQLiteDatabase db = DB_Manager.getInstance().openDatabase();

        ContentValues values = new ContentValues();

        values.put(PRODUCT_ID,      products_id);

        db.insert(TABLE_RECENTS, null, values);

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }



    //*********** Fetch All Recent Items ********//

    public ArrayList<Integer> getUserRecents(){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        SQLiteDatabase db = DB_Manager.getInstance().openDatabase();

        ArrayList<Integer> recents = new ArrayList<Integer>();

        Cursor cursor =  db.rawQuery( "SELECT "+ PRODUCT_ID +" FROM "+ TABLE_RECENTS , null);

        /*if (cursor != null){
            cursor.moveToFirst();

            do {
                recents.add(cursor.getInt(0));

            } while (cursor.moveToNext());
        }*/

        if (cursor.moveToFirst()) {
            do {
                recents.add(cursor.getInt(0));

            } while (cursor.moveToNext());
        }


        // close the Database
        DB_Manager.getInstance().closeDatabase();

        return recents;
    }



    //*********** Update existing Recent Item ********//

    public void updateRecentItem(int products_id) {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        SQLiteDatabase db = DB_Manager.getInstance().openDatabase();

        ContentValues values = new ContentValues();

        values.put(PRODUCT_ID,       products_id);

        db.update(TABLE_RECENTS, values, PRODUCT_ID +" = ?", new String[]{String.valueOf(products_id)});

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }



    //*********** Delete specific Recent Item ********//

    public void deleteRecentItem(int products_id){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        SQLiteDatabase db = DB_Manager.getInstance().openDatabase();

        db.delete(TABLE_RECENTS, PRODUCT_ID +" = ?", new String[]{String.valueOf(products_id)});

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }



    //*********** Clear the table TABLE_RECENTS ********//

    public void clearUserRecents(){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        SQLiteDatabase db = DB_Manager.getInstance().openDatabase();

        db.delete(TABLE_RECENTS, null, null);

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }

}
