package com.genix.wordmemoriser.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SetsDatabase extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "nameOfSets.db";
    private static final String TABLE_NAME = "sets_table";
    private static final String COL_0 = "ID";
    private static final String COL_1 = "name";

    public SetsDatabase(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db){
        String query = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_1 + " TEXT)";
        db.execSQL(query);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        String query = "DROP IF TABLE EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean addSet(String setName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, setName);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    public Cursor getItemId(String setName){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + COL_0 + " FROM " + TABLE_NAME + " WHERE " + COL_1 + " = '" + setName + "'";
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + COL_1 + " = '" + newName + "' WHERE "
                + COL_0 + " = '" +  id + "' AND " + COL_1 + " = '" + oldName + "'";
        db.execSQL(query);
    }

    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_0 + " = '" + id + "' AND "
                + COL_1 + " = '" + name + "'";
        db.execSQL(query);
    }

    public boolean hasInside(String setName){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COL_0 + " FROM " + TABLE_NAME + " WHERE " + COL_1
                + " = '" + setName + "'";
        Cursor data = db.rawQuery(query, null);

        int dataId = -1;

        if(data.moveToNext())
            dataId = data.getInt(0);

        if(dataId == -1)
            return false;
        else
            return true;
    }

}
