package com.bakel.epp.bakel;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class DB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "QuizGameDataBase";
    public static final String SCORE_TABLE = "top_score_table";

    public static final String ID = "_id";
    public static final String TOP_SCORE_VALUE = "score_value";
    private Context context;


    public DB(Context context, String name, CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + SCORE_TABLE + "("
                    + ID + " INTEGER PRIMARY KEY," + TOP_SCORE_VALUE + " TEXT)";
            db.execSQL(CREATE_PRODUCTS_TABLE);
        } catch (Exception e) {

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SCORE_TABLE);
        onCreate(db);
    }

    public void addValue(String curentValue) {

        ArrayList<Integer> list = getValue();
        if (list.size() >= 5) {
            if (list.get(4) > Integer.parseInt(curentValue)) {
                Toast.makeText(context, "You are not in TOP 5, try again.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TOP_SCORE_VALUE, curentValue);////////////////////////
        db.insert(SCORE_TABLE, null, values);
        db.close();

    }

    public ArrayList<Integer> getValue() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        String query = "Select * FROM " + SCORE_TABLE;
        //  String value = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String str = null;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            //Integer.parseInt(cursor.getString(0));


            do {
                list.add(Integer.parseInt(cursor.getString(1)));
            } while (cursor.moveToNext());
        }


        Collections.sort(list);
        Collections.reverse(list);
//        if (list.size() >= 5) {
        while (list.size() > 5) {
            list.remove(5);
        }

        //Log.e("list.size() = ", Integer.toString(list.size()));
        //      }
        db.close();
        return list;
    }

}
