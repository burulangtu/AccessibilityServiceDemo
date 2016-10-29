package com.sunkai.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setVisibility(View.INVISIBLE);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "按钮1被点击", Toast.LENGTH_SHORT).show();
                button1.setVisibility(View.INVISIBLE);
                button1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        button2.setVisibility(View.VISIBLE);
                    }
                }, 3 * 1000);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "按钮2被点击", Toast.LENGTH_SHORT).show();
                button2.setVisibility(View.INVISIBLE);
                button2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        button1.setVisibility(View.VISIBLE);
                    }
                },3*1000);
            }
        });
    }
}
