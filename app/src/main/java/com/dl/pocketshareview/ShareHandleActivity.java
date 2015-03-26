package com.dl.pocketshareview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by logicmelody on 15/3/25.
 */
public class ShareHandleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, FloatingViewService.class));
        finish();
    }

}
