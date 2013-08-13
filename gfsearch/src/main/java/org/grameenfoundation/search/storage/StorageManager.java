package org.grameenfoundation.search.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.grameenfoundation.search.ApplicationRegistry;

/**
 * handles local storage
 *
 * @author ctumwebaze@gmail.com
 */
public class StorageManager {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private final Context context;
    private static final StorageManager instance = new StorageManager();

    private StorageManager() {
        this.context = ApplicationRegistry.getApplicationContext();
        this.databaseHelper = new DatabaseHelper(this.context);
        this.database = databaseHelper.getWritableDatabase();
    }

    /**
     * gets the singleton instance of the storage manager
     *
     * @return
     */
    public static StorageManager getInstance() {
        return instance;
    }

    /**
     * closes the datastore
     */
    public void close() {
        if (this.database.inTransaction()) {
            database.setTransactionSuccessful();
            database.endTransaction();
        }

        databaseHelper.close();
    }

    /**
     * checks whether the data store is open.
     *
     * @return
     */
    public boolean isOpen() {
        return database.isOpen();
    }

    /**
     * searches the datastore with the given sql query
     *
     * @param query the sql query to use when searching
     * @return Cursor
     */
    public Cursor sqlSearch(String query) {
        return database.rawQuery(query, null);
    }

    /**
     * deletes all the records in the given table
     *
     * @param table table for which all the records will be deleted.
     * @return
     */
    public int deleteAll(String table) {
        return database.delete(table, null, null);
    }

    /**
     * inserts the given content values into the given table
     *
     * @param table         table in which to insert values.
     * @param contentValues content values to insert
     * @return true if the operation was successful.
     */
    public boolean insert(String table, ContentValues contentValues) {
        return database.insert(table, null, contentValues) > 0;
    }

    /**
     * inserts the given list of content values into the data store within a transaction.
     *
     * @param table            table in which to insert the values
     * @param contentValueList list of content values to insert.
     * @return
     */
    public boolean insert(String table, ContentValues... contentValueList) {
        try {
            database.beginTransaction();

            for (ContentValues contentValues : contentValueList) {
                database.insert(table, null, contentValues);
            }

            database.setTransactionSuccessful();
            return true;
        } finally {
            database.endTransaction();
        }
    }

    /**
     * replaces the given content values in the given table
     *
     * @param table         table in which the given contentvalues will be replaced.
     * @param contentValues content values to replace
     * @return true if the operation was successful.
     */
    public boolean replace(String table, ContentValues contentValues) {
        return database.replace(table, null, contentValues) > 0;
    }

    /**
     * replaces the given list of content values into the data store within a transaction.
     *
     * @param table            table in which to insert the values
     * @param contentValueList list of content values to insert.
     * @return
     */
    public boolean replace(String table, ContentValues... contentValueList) {
        try {
            database.beginTransaction();

            for (ContentValues contentValues : contentValueList) {
                database.replace(table, null, contentValues);
            }

            database.setTransactionSuccessful();
            return true;
        } finally {
            database.endTransaction();
        }
    }

    /**
     * updates the given content values in the given table
     *
     * @param table         table in which the given content values will be updated.
     * @param contentValues content values to update
     * @return true if the operation was successful.
     */
    public boolean update(String table, ContentValues contentValues) {
        return database.replace(table, null, contentValues) > 0;
    }

    /**
     * updates the given list of content values into the data store within a transaction.
     *
     * @param table            table in which to update the values
     * @param contentValueList list of content values to update.
     * @return
     */
    public boolean update(String table, ContentValues... contentValueList) {
        try {
            database.beginTransaction();

            for (ContentValues contentValues : contentValueList) {
                database.replace(table, null, contentValues);
            }

            database.setTransactionSuccessful();
            return true;
        } finally {
            database.endTransaction();
        }
    }

    /**
     * gets the total number of records in the given table
     *
     * @param tableName the table for which a record count is required.
     * @return total number of records in the table.
     */
    public int recordCount(String tableName) {
        Cursor cursor = database.rawQuery("SELECT COUNT(*) as total FROM " + tableName, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    }
}