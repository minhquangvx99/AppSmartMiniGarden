package com.example.iot_smartminigarden;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class  MainService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        innitFore();
    }
    public void innitFore(){
        NotificationSmartMiniGarden.createChannel(this,"MainService","main_service", NotificationManager.IMPORTANCE_HIGH);
        startForeground(1, NotificationSmartMiniGarden.callNotifyService(this,"MainService","Khu vườn nhỏ thông minh","IOT"));
    }

}

