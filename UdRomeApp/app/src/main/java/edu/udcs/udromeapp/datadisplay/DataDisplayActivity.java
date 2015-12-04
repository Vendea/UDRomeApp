package edu.udcs.udromeapp.datadisplay;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.udcs.udromeapp.LandingFragment;
import edu.udcs.udromeapp.R;

public class DataDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);

        if(getSupportFragmentManager().getBackStackEntryCount() == 0)
            getSupportFragmentManager().beginTransaction().add(R.id.data_fragment_holder, new TaskListFragment()).commit();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
