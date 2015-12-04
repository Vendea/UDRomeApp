package edu.udcs.udromeapp.model.database;

/**
 * Created by jyamauchi on 11/17/15.
 */
public class TaskDbSchema {

    public static final String NAME = "udCacheTask";

    public static final class Cols {
        public static final String TASKID = "taskid";
        public static final String TITLE = "title";
        public static final String DEADLINE = "deadline";
        public static final String ISCOMPLETE = "iscomplete";
        public static final String DATECOMPLETED = "datecompleted";
        public static final String LASTUPDATETIME = "lastupdate";
    }

}
