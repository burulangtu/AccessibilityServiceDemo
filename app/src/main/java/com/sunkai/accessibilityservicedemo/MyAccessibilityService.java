package com.sunkai.accessibilityservicedemo;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import static com.sunkai.accessibilityservicedemo.Config.listen;

/**
 * Created by sunkai on 2016/10/29.
 */

public class MyAccessibilityService extends AccessibilityService {

    private boolean search = false;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, 1000);
            if (search && listen) {
                Log.e("SK", "定时任务");
               searchButton();
            }
        }
    };

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        handler.post(runnable);
        listen = true;
        Log.e("SK", "打开辅助服务");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e("SK", "event:" + event.getEventType());
        //获取当前activity的类名:
        String currentWindowActivity = event.getClassName().toString();
        Log.e("SK", "currentWindowActivity:" + currentWindowActivity);
        if (currentWindowActivity.equals(Config.TARGET_ACTIVITY)) {
            search = true;
        } else {
            search = false;
        }
    }

    private void searchButton() {
        searchViewById(Config.BUTTON_ID_1);
        searchViewById(Config.BUTTON_ID_2);
    }

    private void searchViewById(String id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                for (AccessibilityNodeInfo nodeInfo : accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id)) {
                    if (nodeInfo.isVisibleToUser()) {
                        if (nodeInfo.isClickable()) {// 这里并不能
                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);
                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SELECT);
                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    } else {
                        Log.e("SK", "hidden");
                    }
                }

            }

        }
    }

    @Override
    public void onInterrupt() {
        Log.e("SK", "关闭辅助服务");
        handler.removeCallbacks(runnable);
    }
}
