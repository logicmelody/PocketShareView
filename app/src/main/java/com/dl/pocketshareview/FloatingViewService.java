package com.dl.pocketshareview;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


/**
 * Created by logicmelody on 15/3/25.
 */
public class FloatingViewService extends Service {

    private static final int FLOATING_VIEW_DELAY = 3000;

    private final Handler mHandler = new Handler();

    private WindowManager mWindowManager;

    private View mFloatingView;
    private Button mFloatingButton;

    private Animation mFadeInAnimation;
    private Animation mFadeOutAnimation;

    private boolean mIsAnimationRunning = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
        addFloatingView();
        setFloatingButton();
        setDummyView();
        showFloatingButton();
        closeFloatingViewAfterAWhile();
    }

    private void initialize() {
        findViews();
        mFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        mFadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        mFadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mIsAnimationRunning = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIsAnimationRunning = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mFadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mIsAnimationRunning = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIsAnimationRunning = false;
                stopSelf();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void findViews() {
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        mFloatingView = layoutInflater.inflate(R.layout.floating_view, null);
        mFloatingButton = (Button) mFloatingView.findViewById(R.id.floating_button);
    }

    private void addFloatingView() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        mWindowManager.addView(mFloatingView, params);
    }

    private void setFloatingButton() {
        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFloatingButton();
            }
        });
    }

    private void setDummyView() {
        View dummyView = mFloatingView.findViewById(R.id.dummy_view);
        dummyView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        hideFloatingButton();
                        return true;
                }
                return false;
            }
        });
    }

    private void showFloatingButton() {
        if(mIsAnimationRunning) return;
        mFloatingButton.setVisibility(View.VISIBLE);
        mFloatingButton.startAnimation(mFadeInAnimation);
    }

    private void hideFloatingButton() {
        if(mIsAnimationRunning) return;
        mFloatingButton.setVisibility(View.GONE);
        mFloatingButton.startAnimation(mFadeOutAnimation);
    }

    private void closeFloatingViewAfterAWhile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(FLOATING_VIEW_DELAY);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            hideFloatingButton();
                        }
                    });
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
