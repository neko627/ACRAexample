package com.acra_example.crash;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import org.acra.config.CoreConfiguration;
import org.acra.data.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;
import org.acra.sender.ReportSenderFactory;
import org.json.JSONException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CrashReportSenderFactory implements ReportSenderFactory {
    private final String TAG = "ACRACrashReport";

    private String crashLogTxt = "";
    private File file = null;
    private FileWriter fw = null;
    private BufferedWriter bw = null;

    @Override
    public ReportSender create(@NonNull Context context, @NonNull CoreConfiguration config) {
        return new CrashReportSender();
    }

    @Override
    public boolean enabled(@NonNull CoreConfiguration config) {
        return true;
    }

    // 비정상 종료후 호출됨
    public class CrashReportSender implements ReportSender {
        @Override
        public void send(@NonNull Context context, @NonNull CrashReportData errorContent) throws ReportSenderException {
            Log.d(TAG, "앱 비정상 종료됨");


            // Log File Save
            try {
                crashLogTxt = errorContent.toJSON(); // crash data 를 string 으로
                Log.d(TAG, "crashLogTxt : " + crashLogTxt.substring(0, 100));
                //errorContent.get("STACK_TRACE"); // error log

            } catch (JSONException e) {
                Log.d(TAG, "Empty CrashLog");
            }

            file = new File(String.valueOf(context.getFileStreamPath("CrashLog.txt")));
            try {

                if (!file.exists()) {
                    file.createNewFile();
                }

                fw = new FileWriter(file);
                bw = new BufferedWriter(fw);

                bw.write(crashLogTxt);
                bw.newLine();

                bw.flush();

                fw.close();
                bw.close();

                Log.d(TAG, "CrashLog Save");

            } catch (IOException e) {
                Log.d(TAG, "CrashLog Create Fail");
            }

        } // send


    }

}