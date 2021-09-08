package com.themescoder.androidstore.databases;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * DB_Manager creates Singleton Instance of DB_Manager to Open or Close SQLiteDatabase
 **/


public class DB_Manager {

    // Keeps track of Opened or Closed Database
    private Integer mOpenCounter = 0;

    private SQLiteDatabase db;

    private static DB_Manager instance;
    private static SQLiteOpenHelper mDatabaseHelper;



    //*********** Initialize DB_Manager and SQLiteOpenHelper Instances ********//

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {

        if (instance == null) {
            instance = new DB_Manager();
            mDatabaseHelper = helper;
        }
    }



    //*********** Returns DB_Manager Instance ********//

    public static synchronized DB_Manager getInstance() {

        if (instance == null) {
            throw new IllegalStateException(DB_Manager.class.getSimpleName() + " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }



    //*********** Used to Open the DB Connection ********//

    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter += 1;

        if(mOpenCounter == 1) {
            db = mDatabaseHelper.getWritableDatabase();
        }

        return db;
    }



    //*********** Used to Close the DB Connection ********//

    public synchronized void closeDatabase() {
        mOpenCounter -= 1;

        if(mOpenCounter == 0) {
            db.close();
        }
    }
    
}

