package com.example.bliss.leylabanchaewa_pset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bliss on 11/20/17.
 */

public class TodoDatabase extends SQLiteOpenHelper {

    private static final String db_name = null;

    // private static variable called instance
    private static TodoDatabase instance;


    // private constructor
    private TodoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabasedb) {
        sqLiteDatabasedb.execSQL("create table todos (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, completed INTEGER);");
        sqLiteDatabasedb.execSQL("insert into todos (title, completed) values ('test1', 1) ");
        sqLiteDatabasedb.execSQL("insert into todos (title, completed) values ('test2', 0) ");
        sqLiteDatabasedb.execSQL("insert into todos (title, completed) values ('test3', 1) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "todos");
        onCreate(db);
    }

    // public static method returning instance value
    public static TodoDatabase getInstance(Context context) {
        if (instance == null ) {
            instance = new TodoDatabase(context.getApplicationContext(), db_name, null, 1);
        }

        return instance;
    }

    public Cursor selectAll() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM  todos", null);
        return cursor;
    }

    public void insert(String title, int completed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("title",title);
        contentValues.put("completed", completed);
        db.insert("todos", "null", contentValues);
    }

    public void update (long id, int check) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("completed", check);
        db.update("todos", contentValues, "_id=" + id, null);
    }

    public void delete (long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("todos", "_id=" + id, null);
    }
}

