package com.acra_example;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "ACRACrashReport";

    private String logPath = "", crashLog = "";
    private File file = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLogExist();

    }

    public void crashButton(View view) {
        // Crash 발생 코드!! @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        String crashString = null;
        crashString.length();
    }

    // log check
    public void checkLogExist() {
        //String crashLog = BaseInfo.getCrashLog();
        logPath = getApplicationContext().getFileStreamPath("CrashLog").getPath() + ".txt";
        file = new File(logPath);
        int data;
        char ch;

        try {
            FileReader fr = new FileReader(file);
            while ((data = fr.read()) != -1) {
                ch = (char) data;
                crashLog += ch;
            }
            fr.close();
            SharedPreferences sp = getApplicationContext().getSharedPreferences("ACRA", 0);
            SharedPreferences.Editor editor = sp.edit();

            editor.putString("crashLog", crashLog);
            editor.commit();

        } catch (FileNotFoundException e) {
            Log.d(TAG, "not found log file");
        } catch (IOException e) {
            Log.d(TAG, "log read error");
        }

        if (!crashLog.equals("")) {
            Log.d(TAG, "저장된 로그 있음");
            Log.d(TAG, crashLog.substring(0, 100) + "...");

        } else {
            Log.d(TAG, "저장된 로그 없음");
        }
    }
}
