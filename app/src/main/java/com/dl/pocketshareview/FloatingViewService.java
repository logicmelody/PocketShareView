package com.dl.pocketshareview;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


/**
 * Created by logicmelody on 15/3/25.
 */
public class FloatingViewService extends Service {

    private static final int FLOATING_VIEW_DELAY = 3000;

    private WindowManager mWindowManager;
    private View mFloatingView;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
        setFloatingView();
        setDummyView();
        closeFloatingViewAfterAWhile();
    }

    private void initialize() {
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        mFloatingView = layoutInflater.inflate(R.layout.floating_view, null);
    }

    private void setFloatingView() {
        Button floatingButton = (Button) mFloatingView.findViewById(R.id.floating_button);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSelf();
            }
        });

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        mWindowManager.addView(mFloatingView, params);
    }

    private void setDummyView() {
        View dummyView = mFloatingView.findViewById(R.id.dummy_view);
        dummyView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        stopSelf();
                        return true;
                }
                return false;
            }
        });
    }

    private void closeFloatingViewAfterAWhile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(FLOATING_VIEW_DELAY);
                    stopSelf();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mFloatingView != null) {
            mWindowManager.removeView(mFloatingView);
        }
    }

}
