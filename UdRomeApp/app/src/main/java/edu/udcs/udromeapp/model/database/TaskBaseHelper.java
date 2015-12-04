package edu.udcs.udromeapp.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jyamauchi on 11/17/15.
 */
public class TaskBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "udTaskBase.db";

    public TaskBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TaskDbSchema.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TaskDbSchema.Cols.TASKID + ", " +
                TaskDbSchema.Cols.TITLE + ", " +
                TaskDbSchema.Cols.DEADLINE + ", " +
                TaskDbSchema.Cols.ISCOMPLETE + ", " +
                TaskDbSchema.Cols.DATECOMPLETED + ", " +
                TaskDbSchema.Cols.LASTUPDATETIME +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
