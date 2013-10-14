package org.grameenfoundation.search.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * utility class responsible for initializing and upgrading the database the database
 *
 * @author Charles Tumwebaze
 */
final class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DatabaseHelperConstants.DATABASE_NAME, null, DatabaseHelperConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        createDatabaseTables(database);
    }

    private void createDatabaseTables(SQLiteDatabase database) {
        // Create Menu Table
        database.execSQL(getMenuTableInitializationSql());

        // Create Menu Item Table
        database.execSQL(getMenuItemTableInitializationSql());

        // Create Available Farmer Id Table
        database.execSQL(getAvailableFarmerIdTableInitializationSql());

        // Create Farmer Local Cache Table
        database.execSQL(getFarmerLocalCacheTableInitializationSql());

        //create search log table
        database.execSQL(getSearchLogTableInitializationSql());
    }

    private String getSearchLogTableInitializationSql() {
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("CREATE TABLE IF NOT EXISTS ").append(DatabaseHelperConstants.SEARCH_LOG_TABLE_NAME);
        sqlCommand.append("(");
        sqlCommand.append(DatabaseHelperConstants.SEARCH_LOG_ROW_ID_COLUMN).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlCommand.append(DatabaseHelperConstants.SEARCH_LOG_CLIENT_ID_COLUMN).append(" VARCHAR, ");
        sqlCommand.append(DatabaseHelperConstants.SEARCH_LOG_CONTENT_COLUMN).append(" TEXT NOT NULL,");
        sqlCommand.append(DatabaseHelperConstants.SEARCH_LOG_DATE_CREATED_COLUMN).append(" DEFAULT CURRENT_TIMESTAMP,");
        sqlCommand.append(DatabaseHelperConstants.SEARCH_LOG_GPS_LOCATION_COLUMN).append(" VARCHAR,");
        sqlCommand.append(DatabaseHelperConstants.SEARCH_LOG_MENU_ITEM_ID_COLUMN).append(" VARCHAR");
        sqlCommand.append(" );");

        return null;
    }

    /**
     * Returns the SQL string for Menu Table creation
     *
     * @return String
     */
    private String getMenuTableInitializationSql() {
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand
                .append("CREATE TABLE IF NOT EXISTS " + DatabaseHelperConstants.MENU_TABLE_NAME);
        sqlCommand.append(" (" + DatabaseHelperConstants.MENU_ROWID_COLUMN
                + " CHAR(16) PRIMARY KEY, " + DatabaseHelperConstants.MENU_LABEL_COLUMN
                + " TEXT NOT NULL);");
        return sqlCommand.toString();
    }

    /**
     * Returns the SQL string for MenuItem Table creation
     *
     * @return String
     */
    private String getMenuItemTableInitializationSql() {

        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("CREATE TABLE IF NOT EXISTS "
                + DatabaseHelperConstants.MENU_ITEM_TABLE_NAME);
        sqlCommand.append(" (" + DatabaseHelperConstants.MENU_ITEM_ROWID_COLUMN
                + " CHAR(16) PRIMARY KEY, "
                + DatabaseHelperConstants.MENU_ITEM_LABEL_COLUMN + " TEXT NOT NULL, "
                + DatabaseHelperConstants.MENU_ITEM_MENUID_COLUMN + " CHAR(16), "
                + DatabaseHelperConstants.MENU_ITEM_PARENTID_COLUMN + " CHAR(16), "
                + DatabaseHelperConstants.MENU_ITEM_POSITION_COLUMN + " INTEGER, "
                + DatabaseHelperConstants.MENU_ITEM_CONTENT_COLUMN + " TEXT, "
                + DatabaseHelperConstants.MENU_ITEM_ATTACHMENTID_COLUMN + " CHAR(16), ");
        sqlCommand.append(" FOREIGN KEY(menu_id) REFERENCES "
                + DatabaseHelperConstants.MENU_TABLE_NAME
                + "(id) ON DELETE CASCADE, ");
        sqlCommand.append(" FOREIGN KEY(parent_id) REFERENCES "
                + DatabaseHelperConstants.MENU_ITEM_TABLE_NAME
                + "(id) ON DELETE CASCADE ");
        sqlCommand.append(" );");
        return sqlCommand.toString();
    }

    /**
     * Returns the SQL string for AvailableFarmerId Table creation
     *
     * @return String
     */
    private String getAvailableFarmerIdTableInitializationSql() {

        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("CREATE TABLE IF NOT EXISTS "
                + DatabaseHelperConstants.AVAILABLE_FARMER_ID_TABLE_NAME);
        sqlCommand.append(" (" + DatabaseHelperConstants.AVAILABLE_FARMER_ID_ROWID_COLUMN
                + " CHAR(16) PRIMARY KEY, "
                + DatabaseHelperConstants.AVAILABLE_FARMER_ID_FARMER_ID + " CHAR(16), "
                + DatabaseHelperConstants.AVAILABLE_FARMER_ID_STATUS + " INTEGER ");
        sqlCommand.append(" );");
        return sqlCommand.toString();
    }

    /**
     * Returns the SQL string for FarmerLocalCache Table creation
     *
     * @return String
     */
    private String getFarmerLocalCacheTableInitializationSql() {

        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("CREATE TABLE IF NOT EXISTS "
                + DatabaseHelperConstants.FARMER_LOCAL_CACHE_TABLE_NAME);
        sqlCommand.append(" (" + DatabaseHelperConstants.FARMER_LOCAL_CACHE_ROWID_COLUMN
                + " CHAR(16) PRIMARY KEY, "
                + DatabaseHelperConstants.FARMER_LOCAL_CACHE_FARMER_ID + " CHAR(16), "
                + DatabaseHelperConstants.FARMER_LOCAL_CACHE_FIRST_NAME + " CHAR(16), "
                + DatabaseHelperConstants.FARMER_LOCAL_CACHE_MIDDLE_NAME + " CHAR(16), "
                + DatabaseHelperConstants.FARMER_LOCAL_CACHE_LAST_NAME + " CHAR(16), "
                + DatabaseHelperConstants.FARMER_LOCAL_CACHE_AGE + " INTEGER, "
                + DatabaseHelperConstants.FARMER_LOCAL_CACHE_FATHER_NAME + " CHAR(16) ");
        sqlCommand.append(" );");
        return sqlCommand.toString();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        Log.w("DatabaseHelper", "***Upgrading database from version*** "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");

        createDatabaseTables(database);
    }
}
