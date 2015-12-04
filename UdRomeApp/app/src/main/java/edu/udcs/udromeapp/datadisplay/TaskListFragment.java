package edu.udcs.udromeapp.datadisplay;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import edu.udcs.udromeapp.R;
import edu.udcs.udromeapp.model.Task;
import edu.udcs.udromeapp.model.TaskRepository;

/**
 * Created by jyamauchi on 11/12/15.
 */
public class TaskListFragment extends Fragment {

    ListView mTaskList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task_list, container, false);

        mTaskList = (ListView)v.findViewById(R.id.task_list);
        mTaskList.setAdapter(new TaskAdapter(TaskRepository.getSingleton(getActivity()).getTasks()));

        return v;
    }

    class TaskAdapter extends BaseAdapter {

        Task[] tasks;

        public TaskAdapter(Task[] tasks) {
            this.tasks = tasks;
        }

        @Override
        public int getCount() {
            return tasks.length;
        }

        @Override
        public Object getItem(int position) {
            return tasks[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.task_list_item, parent, false);

            ((TextView) convertView.findViewById(R.id.task_list_item_text)).setText(tasks[position].toString());

            if(tasks[position].isComplete())
            ((TextView) convertView.findViewById(R.id.task_list_item_status)).setText("Complete");
            else
                ((TextView) convertView.findViewById(R.id.task_list_item_status)).setText("Due " + tasks[position].getDeadlineDate());

            return convertView;
        }
    }
}
