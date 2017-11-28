package com.uhuru.dashboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dorian on 14/04/15.
 */
class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SaveData";
    private static final String TABLE_NAME = "notifications";
    private static final String KEY_ID = "id";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_MODULE = "module";
    private static final String KEY_VERBOSE = "verbose";
    private static final String KEY_INCIDENT = "incident";
    private static final String KEY_COMMENT = "comment";
    private static final String[] COLONNES = { KEY_ID, KEY_TIMESTAMP, KEY_MODULE, KEY_VERBOSE,
            KEY_INCIDENT, KEY_COMMENT };

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {

        String CREATION_TABLE_EXEMPLE = "CREATE TABLE notifications ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "timestamp TEXT," + "module TEXT, "
                + "verbose INTEGER, " + "incident TEXT, " + "comment TEXT)";

        arg0.execSQL(CREATION_TABLE_EXEMPLE);
        Log.i("SQLite DB", "Creation");

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(arg0);
        Log.i("SQLite DB", "Upgrade");


    }

    public void deleteOne(SaveData savedata) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, // table
                "id = ?", new String[] { String.valueOf(savedata.getId()) });
        db.close();
        Log.i("SQLite DB : Delete : ", savedata.toString());

    }

    public SaveData showOne(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLONNES, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        if (cursor != null)
            cursor.moveToFirst();
        SaveData savedata = new SaveData();
        savedata.setTimestamp(cursor.getString(0));
        savedata.setModule(cursor.getString(1));
        savedata.setVerbose(Integer.parseInt(cursor.getString(2)));
        savedata.setIncident(cursor.getString(3));
        // log
        Log.i("SQLite DB", "Show one  : id=  " + id + " " + savedata.toString());
        db.close();
        return savedata;
    }

    public List<SaveData> showAll() {
        List<SaveData> savedatas = new LinkedList<SaveData>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        SaveData savedata = null;
        if (cursor.moveToFirst()) {
            do {
                savedata = new SaveData();
                savedata.setId(Integer.parseInt(cursor.getString(0)));
                savedata.setTimestamp(cursor.getString(1));
                savedata.setModule(cursor.getString(2));
                savedata.setVerbose(Integer.parseInt(cursor.getString(3)));
                savedata.setIncident(cursor.getString(4));
                savedata.setComment(cursor.getString(5));
                savedatas.add(savedata);
            } while (cursor.moveToNext());
        }
        db.close();
        //Log.i("SQLite DB : Show All  : ", savedatas.toString());
        return savedatas;
    }

    public void addOne(SaveData savedata) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIMESTAMP, savedata.getTimestamp());
        values.put(KEY_MODULE, savedata.getModule());
        values.put(KEY_VERBOSE, savedata.getVerbose());
        values.put(KEY_INCIDENT, savedata.getIncident());
        values.put(KEY_COMMENT, savedata.getComment());
        // insertion
        db.insert(TABLE_NAME, // table
                null, values);

        db.close();
        Log.i("SQLite DB", "Add one  : id=  " + savedata.getId() + " " + savedata.toString());
    }

    public int updateOne(SaveData savedata) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIMESTAMP, savedata.getTimestamp());
        values.put(KEY_MODULE, savedata.getModule());
        values.put(KEY_VERBOSE, savedata.getVerbose());
        values.put(KEY_INCIDENT, savedata.getIncident());
        values.put(KEY_COMMENT, savedata.getComment());
        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(savedata.getId()) });

        db.close();
        Log.i("SQLite DB", "Update one  : id=  "+savedata.getId() + " " + savedata.toString());

        return i;
    }

}
