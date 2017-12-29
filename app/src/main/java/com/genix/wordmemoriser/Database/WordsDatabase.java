package com.genix.wordmemoriser.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.genix.wordmemoriser.Activities.ManageSets;

public class WordsDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "words.db";
    private String TABLE_NAME;
    private static final String COL_0 = "ID";
    private static final String COL_1 = "word1";
    private static final String COL_2 = "word2";

    public WordsDatabase(Context context, String name){
        super(context, DATABASE_NAME, null, 1);
        TABLE_NAME = name + "_table";
    }

    public void onCreate(SQLiteDatabase db) {
        //NOT SURE IF THIS IS NEEDED, BUT WORKS BETTER WITHOUT
//        String query = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + COL_1 + " TEXT, " + COL_2 + " TEXT)";
//        db.execSQL(query);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP IF TABLE EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean addWords(String word1, String word2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, word1);
        contentValues.put(COL_2, word2);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1) //inserted incorrectly
            return false;
        else
            return true;
    }

    public Cursor getDataFromTable(String tableName){
        tableName += "_table";
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + tableName;
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    public Cursor getItemIdFromTable(String word1, String word2, String tableName){
        tableName += "_table";
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + COL_0 + " FROM " + tableName + " WHERE " + COL_1 + " = '"
                + word1 + "' AND " + COL_2 + " = '" + word2 + "'";
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    public void updateWords(int id, String oldWord1, String newWord1, String oldWord2, String newWord2){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + COL_1 + " = '" + newWord1 + "', "
                + COL_2 + " = '" + newWord2 + "' WHERE " + COL_0 + " = '" +  id + "' AND "
                + COL_1 + " = '" + oldWord1 + "' AND " + COL_2 + " = '" + oldWord2 + "'";
        db.execSQL(query);
    }

    public void deleteWords(int id, String word1, String word2){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_0 + " = '" + id + "' AND "
                + COL_1 + " = '" + word1 + "' AND " + COL_2 + " = '" + word2 + "'" ;
        db.execSQL(query);
    }

    public void deleteTable(String tableName){
        tableName += "_table";
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(query);
    }

    public void createTable(String tableName){
        tableName += "_table";
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "CREATE TABLE " + tableName + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_1 + " TEXT, " + COL_2 + " TEXT)";
        db.execSQL(query);
    }

    public void changeTableName(String oldTableName, String newTableName){
        oldTableName += "_table";
        newTableName += "_table";
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "ALTER TABLE " + oldTableName + " RENAME TO " + newTableName;
        db.execSQL(query);
    }
}
