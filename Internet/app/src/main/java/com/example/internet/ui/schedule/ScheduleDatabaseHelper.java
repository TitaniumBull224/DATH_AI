package com.example.internet.ui.schedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

public class ScheduleDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "schedule.db";
    private static final int DATABASE_VERSION = 1;

    public ScheduleDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE schedule (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, dateBegin INTEGER, dateEnd INTEGER, times TEXT, description TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // handle database upgrade
    }

    public List<Schedule> getSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM schedule", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String dateBegin = cursor.getString(2);
                String dateEnd = cursor.getString(3);
                String times = cursor.getString(4);
                String description = cursor.getString(5);
                Schedule schedule = new Schedule(id, name, dateBegin, dateEnd, times, description);
                schedules.add(schedule);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return schedules;
    }

    public void createSchedule(Schedule schedule) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", schedule.getName());
        values.put("dateBegin", schedule.getDateBegin());
        values.put("dateEnd", schedule.getDateEnd());
        values.put("times", schedule.getTimes());
        values.put("description", schedule.getDescription());
        db.insert("schedule", null, values);
    }

    public void updateSchedule(Schedule schedule) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", schedule.getName());
        values.put("dateBegin", schedule.getDateBegin());
        values.put("dateEnd", schedule.getDateEnd());
        values.put("times", schedule.getTimes());
        values.put("description", schedule.getDescription());
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(schedule.getId())};
        db.update("schedule", values, whereClause, whereArgs);
    }

    public void deleteSchedule(Schedule schedule) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = {String.valueOf(schedule.getId())};
        db.delete("schedule", whereClause, whereArgs);
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("schedule", null, null);
    }
}
