package edu.udcs.udromeapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import edu.udcs.udromeapp.model.database.TaskBaseHelper;
import edu.udcs.udromeapp.model.database.TaskCursorWrapper;
import edu.udcs.udromeapp.model.database.TaskDbSchema;

/**
 * Created by jyamauchi on 11/17/15.
 */
public class TaskRepository {

    private static final String TASKS_URL = "http://127.0.0.1:2403/tasks";
    private static TaskRepository sTaskRepository;

    private Context mContext;
    private SQLiteDatabase mCacheDatabase;

    private TaskRepository(Context context) {
        mContext = context.getApplicationContext();

        mCacheDatabase = new TaskBaseHelper(mContext).getWritableDatabase();
    }

    public static TaskRepository getSingleton(Context context) {
        if(sTaskRepository == null)
            sTaskRepository = new TaskRepository(context);

        return sTaskRepository;
    }

    public Task[] getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskCursorWrapper cursor = queryTasks(null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tasks.add(cursor.getTask());
            cursor.moveToNext();
        }

        cursor.close();

        return tasks.toArray(new Task[0]);
    }

    public void updateCache() {

        // This code actually gets all tasks off the server, then iterates through on a separate thread to update, add, or delete various tasks
        RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Task[] tasks = rt.getForObject(TASKS_URL, Task[].class);

        boolean flag = false;
        if(getTasks().length != tasks.length)
            flag = true;

        for (Task task : tasks)
            addToDatabase(task);

        if(flag){
            TaskCursorWrapper cursor = queryTasks(TaskDbSchema.Cols.DATECOMPLETED + " < ?",
                    new String[] {DateTimeFormat.forPattern("yyyyMMdd").print(new DateTime())});

            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                mCacheDatabase.delete(TaskDbSchema.NAME, TaskDbSchema.Cols.TASKID + " = ?",
                        new String[] {cursor.getTask().getId()});
                cursor.moveToNext();
            }
        }
    }

    private void addToDatabase(Task task) {
        TaskCursorWrapper cursor = queryTasks(TaskDbSchema.Cols.TASKID + " = ?", new String[] {task.getId()});

        if(cursor.getCount() == 0)
            addTask(task);
        else
            updateTask(task);

        cursor.close();
    }

    private void addTask(Task task) {
        ContentValues values = getContentValues(task);

        mCacheDatabase.insert(TaskDbSchema.NAME, null, values);
    }

    private void updateTask(Task task) {
        ContentValues values = getContentValues(task);

        mCacheDatabase.update(TaskDbSchema.NAME, values, TaskDbSchema.Cols.TASKID + " = ?", new String[] { task.getId() });
    }

    private ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskDbSchema.Cols.TASKID, task.getId());
        values.put(TaskDbSchema.Cols.TITLE, task.getTitle());
        values.put(TaskDbSchema.Cols.DEADLINE, task.getDeadline());
        values.put(TaskDbSchema.Cols.ISCOMPLETE, task.isComplete());
        values.put(TaskDbSchema.Cols.DATECOMPLETED, task.getDateCompleted());
        values.put(TaskDbSchema.Cols.LASTUPDATETIME, DateTimeFormat.forPattern("yyyyMMdd").print(new DateTime()));
        return values;
    }


    private TaskCursorWrapper queryTasks(String whereClause, String[] whereArgs) {
        return new TaskCursorWrapper(mCacheDatabase.query(TaskDbSchema.NAME, null, whereClause, whereArgs, null, null, null));
    }

}
