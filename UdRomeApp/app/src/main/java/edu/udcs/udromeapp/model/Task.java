package edu.udcs.udromeapp.model;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by jyamauchi on 11/12/15.
 */
public class Task {

    String title;
    String id;
    int deadline;
    boolean complete;
    int dateCompleted;

    int lastUpdate;

    public Task(String title, String id, int deadline, boolean complete, int dateCompleted, int lastUpdate) {
        this.title = title;
        this.id = id;
        this.deadline = deadline;
        this.complete = complete;
        this.dateCompleted = dateCompleted;
        this.lastUpdate = lastUpdate;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return getTitle();
    }

    public String getId() {
        return id;
    }

    public int getDeadline() {
        return deadline;
    }

    public String getDeadlineDate() {
        return DateTimeFormat.forPattern("MM/dd/yyyy").print(DateTimeFormat.forPattern("yyyyMMdd").parseDateTime(Integer.toString(deadline)));
    }

    public boolean isComplete() {
        return complete;
    }

    public int getDateCompleted() {
        return dateCompleted;
    }

    public int getLastUpdate() {
        return lastUpdate;
    }
}
