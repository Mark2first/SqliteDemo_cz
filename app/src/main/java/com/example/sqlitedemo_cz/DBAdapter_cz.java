package com.example.sqlitedemo_cz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBAdapter_cz {
    private static final String DB_NAME_CZ = "people_cz.db";
    private static final String DB_TABLE_CZ = "peopleinfo_cz";
    private static final int DB_VERSION_CZ = 1;

    public static final String KEY_ID_CZ = "_id";
    public static final String KEY_NAME_CZ = "name_cz";
    public static final String KEY_AGE_CZ = "age_cz";
    public static final String KEY_HEIGHT_CZ = "height_cz";

    private SQLiteDatabase db_cz;
    private final Context context_cz;
    private DBOpenHelper_cz dbOpenHelper_cz;

    private static class DBOpenHelper_cz extends SQLiteOpenHelper{

        public DBOpenHelper_cz(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private static final String DB_CREATE_CZ = "create table " + DB_TABLE_CZ + "("
                + KEY_ID_CZ + " integer primary key autoincrement, "
                + KEY_NAME_CZ + " text not null, " + KEY_AGE_CZ + " integer, " + KEY_HEIGHT_CZ +" float);";

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DB_CREATE_CZ);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_CZ);
            onCreate(_db);
        }
    }

    public long insert_cz(People_cz people_cz){
        ContentValues newValues_cz = new ContentValues();
        newValues_cz.put(KEY_NAME_CZ,people_cz.Name_cz);
        newValues_cz.put(KEY_AGE_CZ,people_cz.Age_cz);
        newValues_cz.put(KEY_HEIGHT_CZ,people_cz.Height_cz);
        return db_cz.insert(DB_TABLE_CZ,null,newValues_cz);
    }

    public long updateOneData(String id, People_cz people_cz){
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_NAME_CZ,people_cz.Name_cz);
        updateValues.put(KEY_AGE_CZ,people_cz.Age_cz);
        updateValues.put(KEY_HEIGHT_CZ,people_cz.Height_cz);
        return db_cz.update(DB_TABLE_CZ,updateValues,KEY_ID_CZ + " = " + id,null);
    }

    public long deleteAllData(){
        return db_cz.delete(DB_TABLE_CZ,null,null);
    }

    public long deleteOneData(long id){
        return db_cz.delete(DB_TABLE_CZ,KEY_ID_CZ+" = "+id,null);
    }

    public People_cz[] queryAllData(){
        Cursor results = db_cz.query(DB_TABLE_CZ,new String[]{KEY_ID_CZ,KEY_NAME_CZ,KEY_AGE_CZ,KEY_HEIGHT_CZ},
                null,null,null,null,null);
        return ConvertToPeople(results);
    }
    public People_cz[] queryOneData(long id){
        Cursor results =  db_cz.query(DB_TABLE_CZ, new String[] { KEY_ID_CZ, KEY_NAME_CZ, KEY_AGE_CZ, KEY_HEIGHT_CZ},
                KEY_ID_CZ + "=" + id, null, null, null, null);
        return ConvertToPeople(results);
    }
    private People_cz[] ConvertToPeople(Cursor cursor){ //显示所有
        int resultCounts = cursor.getCount();
        if(resultCounts == 0||!cursor.moveToFirst())
            return null;
        People_cz[] peoples_cz = new People_cz[resultCounts];
        for(int i=0;i<resultCounts;i++){
            peoples_cz[i]=new People_cz();
            peoples_cz[i].ID = cursor.getInt(0);
            peoples_cz[i].Name_cz = cursor.getString(cursor.getColumnIndex(KEY_NAME_CZ));
            peoples_cz[i].Age_cz = cursor.getString(cursor.getColumnIndex(KEY_AGE_CZ));
            peoples_cz[i].Height_cz = cursor.getString(cursor.getColumnIndex(KEY_HEIGHT_CZ));
            cursor.moveToNext();
        }return peoples_cz;
    }

    public DBAdapter_cz(Context _context){
        context_cz = _context;
    }
    public void open() throws SQLiteException {
        dbOpenHelper_cz = new DBOpenHelper_cz(context_cz,DB_NAME_CZ,null,DB_VERSION_CZ);
        try {
            db_cz = dbOpenHelper_cz.getWritableDatabase();
        }catch (SQLiteException ex){
            db_cz = dbOpenHelper_cz.getReadableDatabase();
        }
    }
    public void close(){
        if(db_cz!=null){
            db_cz.close();
            db_cz=null;
        }
    }
}
