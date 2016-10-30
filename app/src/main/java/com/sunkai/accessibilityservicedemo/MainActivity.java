package com.sunkai.accessibilityservicedemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AccessibilityManager accessibilityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Looper.getMainLooper().setMessageLogging(new Printer() {
            @Override
            public void println(String x) {
                Log.e("Looper", x);
            }
        });
        accessibilityManager = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);
        setContentView(R.layout.activity_main);
        accessibilityManager.addAccessibilityStateChangeListener(new AccessibilityManager.AccessibilityStateChangeListener() {
            @Override
            public void onAccessibilityStateChanged(boolean b) {
                startServer(b);
            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startServer(accessibilityManager.isEnabled());
            }
        });

        if (accessibilityManager.isEnabled()) {
            findViewById(R.id.button1).setVisibility(View.GONE);
        }
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.listen = !Config.listen;
                updateButton2();
            }
        });
        updateButton2();
    }

    private void updateButton2() {
        String str = "停止监听";
        if (!Config.listen) {
            str = "开启监听 ";
        }
        ((Button) findViewById(R.id.button2)).setText(str);
    }

    private void startServer(boolean b) {
        if (b) {
            finish();
        } else {
            try {
                //打开系统设置中辅助功能
                Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "开启服务", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
