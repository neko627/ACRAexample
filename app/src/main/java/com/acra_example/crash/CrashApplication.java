package com.acra_example.crash;

import android.app.Application;
import android.content.Context;

import com.acra_example.R;

import org.acra.ACRA;
import org.acra.annotation.AcraCore;
import org.acra.annotation.AcraToast;

@AcraToast(resText = R.string.crash_toast)
@AcraCore(reportSenderFactoryClasses = CrashReportSenderFactory.class) // Log 전송 클래스
public class CrashApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // The following line triggers the initialization of ACRA
        ACRA.init(this);

    }

}
