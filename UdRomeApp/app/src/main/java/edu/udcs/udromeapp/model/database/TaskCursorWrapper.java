package edu.udcs.udromeapp.model.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import edu.udcs.udromeapp.model.Task;

/**
 * Created by jyamauchi on 11/17/15.
 */
public class TaskCursorWrapper extends CursorWrapper {

    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask() {
        String id = getString(getColumnIndex(TaskDbSchema.Cols.TASKID));
        String title = getString(getColumnIndex(TaskDbSchema.Cols.TITLE));
        int deadline = getInt(getColumnIndex(TaskDbSchema.Cols.DEADLINE));
        boolean complete = getInt(getColumnIndex(TaskDbSchema.Cols.ISCOMPLETE)) != 0;
        int dateCompleted = getInt(getColumnIndex(TaskDbSchema.Cols.DATECOMPLETED));
        int lastUpdate = getInt(getColumnIndex(TaskDbSchema.Cols.LASTUPDATETIME));

        return new Task(title, id, deadline, complete, dateCompleted, lastUpdate);
    }

}
