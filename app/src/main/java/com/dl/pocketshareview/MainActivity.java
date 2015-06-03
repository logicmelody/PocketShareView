package com.dl.pocketshareview;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String FLOATING_VIEW_DIALOGFRAGMENT = "floating_view_dialogfragment";

    private Button mShowUpButton;
    private Button mShowDialogFragmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowUpButton = (Button) findViewById(R.id.show_up_button);
        mShowUpButton.setOnClickListener(this);
        mShowDialogFragmentButton = (Button) findViewById(R.id.show_dialogfragment_button);
        mShowDialogFragmentButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.show_up_button:
                startService(new Intent(this, FloatingViewService.class));
                break;
            case R.id.show_dialogfragment_button:
                Bundle bundle = new Bundle();
                bundle.putLong(FloatingViewDialogFragment.KEY_DELETED_TASK_ID, 13L);
                FloatingViewDialogFragment floatingViewDialogFragmentnew = new FloatingViewDialogFragment();
                floatingViewDialogFragmentnew.setArguments(bundle);
                floatingViewDialogFragmentnew.show(getFragmentManager(), FLOATING_VIEW_DIALOGFRAGMENT);
                break;
        }
    }
}
