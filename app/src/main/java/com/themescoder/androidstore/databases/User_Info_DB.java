package com.themescoder.androidstore.databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.themescoder.androidstore.models.user_model.UserDetails;


/**
 * User_Info_DB creates the table User_Record and handles all CRUD operations relevant to User_Record
 **/

public class User_Info_DB {

    SQLiteDatabase db;

    // Table Name
    public static final String TABLE_USER_INFO = "User_Record";
    // Table Columns
    public static final String CUSTOMERS_ID                      = "customers_id";
    public static final String CUSTOMERS_FIRSTNAME               = "customers_firstname";
    public static final String CUSTOMERS_LASTNAME                = "customers_lastname";
    public static final String CUSTOMERS_EMAIL_ADDRESS           = "customers_email_address";
    public static final String CUSTOMERS_PASSWORD                = "customers_password";
    public static final String CUSTOMERS_DOB                     = "customers_dob";
    public static final String CUSTOMERS_TELEPHONE               = "customers_telephone";
    public static final String CUSTOMERS_FAX                     = "customers_fax";
    public static final String CUSTOMERS_GENDER                  = "customers_gender";
    public static final String CUSTOMERS_NEWSLETTER              = "customers_newsletter";
    public static final String CUSTOMERS_PICTURE                 = "customers_picture";




    //*********** Returns the Query to Create TABLE_USER_INFO ********//

    public static String createTable() {

        return "CREATE TABLE "+ TABLE_USER_INFO +
                "(" +
                    CUSTOMERS_ID               +" TEXT," +
                    CUSTOMERS_FIRSTNAME        +" TEXT," +
                    CUSTOMERS_LASTNAME         +" TEXT," +
                    CUSTOMERS_EMAIL_ADDRESS    +" TEXT," +
                    CUSTOMERS_PASSWORD         +" TEXT," +
                    CUSTOMERS_DOB              +" TEXT," +
                    CUSTOMERS_TELEPHONE        +" TEXT," +
                    CUSTOMERS_FAX              +" TEXT," +
                    CUSTOMERS_GENDER           +" TEXT," +
                    CUSTOMERS_NEWSLETTER       +" TEXT," +
                    CUSTOMERS_PICTURE          +" TEXT" +
                ")";
    }



    //*********** Insert New User Data ********//

    public void insertUserData(UserDetails user){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        ContentValues values = new ContentValues();

        values.put(CUSTOMERS_ID,              user.getId());
        values.put(CUSTOMERS_FIRSTNAME,       user.getFirstName());
        values.put(CUSTOMERS_LASTNAME,        user.getLastName());
        values.put(CUSTOMERS_EMAIL_ADDRESS,   user.getEmail());
        values.put(CUSTOMERS_PASSWORD,        user.getPassword());
        values.put(CUSTOMERS_DOB,             user.getDob());
        values.put(CUSTOMERS_TELEPHONE,       user.getPhone());
        values.put(CUSTOMERS_GENDER,          user.getGender());
        values.put(CUSTOMERS_PICTURE,         user.getAvatar());

        db.insert(TABLE_USER_INFO, null, values);

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }



    //*********** Get the Details of single User ********//

    public UserDetails getUserData(String userID){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        Cursor cursor =  db.rawQuery( "SELECT * FROM "+ TABLE_USER_INFO +" WHERE "+ CUSTOMERS_ID +" =?", new String[] {userID});

        UserDetails userInfo = new UserDetails();


        if (cursor.moveToFirst()) {
            do {
                userInfo.setId(cursor.getString(0));
                userInfo.setFirstName(cursor.getString(1));
                userInfo.setLastName(cursor.getString(2));
                userInfo.setEmail(cursor.getString(3));
                userInfo.setPassword(cursor.getString(4));
                userInfo.setDob(cursor.getString(5));
                userInfo.setPhone(cursor.getString(6));
                userInfo.setGender(cursor.getString(7));
                userInfo.setAvatar(cursor.getString(8));

            } while (cursor.moveToNext());


            // close the Database
            DB_Manager.getInstance().closeDatabase();

            return userInfo;
        }

        return null;
    }



    //*********** Update the Details of existing User ********//

    public void updateUserData(UserDetails user){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        ContentValues values = new ContentValues();

        values.put(CUSTOMERS_ID,              user.getId());
        values.put(CUSTOMERS_FIRSTNAME,       user.getFirstName());
        values.put(CUSTOMERS_LASTNAME,        user.getLastName());
        values.put(CUSTOMERS_EMAIL_ADDRESS,   user.getEmail());
        values.put(CUSTOMERS_PASSWORD,        user.getPassword());
        values.put(CUSTOMERS_DOB,             user.getDob());
        values.put(CUSTOMERS_TELEPHONE,       user.getPhone());
        values.put(CUSTOMERS_GENDER,          user.getGender());
        values.put(CUSTOMERS_PICTURE,         user.getAvatar());

        db.update(TABLE_USER_INFO, values, CUSTOMERS_EMAIL_ADDRESS +" = ?", new String[]{user.getEmail()});

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }



    //*********** Update the Password of existing User ********//

    public void updateUserPassword(UserDetails user){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        ContentValues values = new ContentValues();

        values.put(CUSTOMERS_PASSWORD,        user.getPassword());

        db.update(TABLE_USER_INFO, values, CUSTOMERS_ID +" = ?", new String[]{user.getId()});

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }



    //*********** Delete the Data of the User ********//

    public void deleteUserData(String userID){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        db.delete(TABLE_USER_INFO, CUSTOMERS_ID +" = ?", new String[] {userID});

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }
}
