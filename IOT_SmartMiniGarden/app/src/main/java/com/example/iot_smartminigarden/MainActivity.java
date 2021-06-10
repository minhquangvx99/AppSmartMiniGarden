package com.example.iot_smartminigarden;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.iot_smartminigarden.model.Sprinkler;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.example.iot_smartminigarden.model.Led;
import com.example.iot_smartminigarden.retrofit.InterfaceNetwork;
import com.example.iot_smartminigarden.retrofit.RetrofitResponse;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.iot_smartminigarden.config.Config.*;

public class MainActivity extends AppCompatActivity{
    ImageView imageLight;
    Switch switchLight;
    ImageView imageWaterring;
    Switch switchWatering;
    int themeIdcurrent;
    List<Led> leds;
    List<Sprinkler> sprinklers;
    MqttAndroidClient client;
    public static Retrofit retrofit;
    public InterfaceNetwork interfaceNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Đọc ID theme đã lưu, nếu chưa lưu thì dùng R.style.MyAppTheme
        SharedPreferences locationpref = getApplicationContext()
                .getSharedPreferences(FILE_USER, MODE_PRIVATE);
        themeIdcurrent = locationpref.getInt(FILE_MODE_THEME,R.style.AppTheme);
        Log.d("themss",themeIdcurrent+"");
        //Thiết lập theme cho Activity
        setTheme(themeIdcurrent);
        setContentView(R.layout.activity_main);

//        Service broadcast
        Intent mService = new Intent(this, MainService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(mService);
        } else {
            startService(mService);
        }
//        innit retrofit
        retrofit = RetrofitResponse.getInstance().getRetrofit();
        interfaceNetwork = retrofit.create(InterfaceNetwork.class);

        imageLight = findViewById(R.id.imageLight);
        switchLight = findViewById(R.id.switchLight);


        Led led = new Led(111,1); // value 1 on, 0 off
        if(led.getValue() == 1){
            switchLight.setChecked(true);
            imageLight.setImageResource(R.drawable.ic_light_on);
        }else {
            switchLight.setChecked(false);
            imageLight.setImageResource(R.drawable.ic_light_off);
        }
        switchLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    led.setValue(1);
                    imageLight.setImageResource(R.drawable.ic_light_on);
                    CountDownTimer countDownTimer = new CountDownTimer(1000, 20) {
                        @Override
                        public void onTick(long l) {
                            switchLight.setEnabled(false);
                        }

                        @Override
                        public void onFinish() {
                            switchLight.setEnabled(true);
                        }
                    };
                    countDownTimer.start() ;
                } else {
                    led.setValue(0);
                    imageLight.setImageResource(R.drawable.ic_light_off);
                    CountDownTimer countDownTimer = new CountDownTimer(1000, 20) {
                        @Override
                        public void onTick(long l) {
                            switchLight.setEnabled(false);
                        }

                        @Override
                        public void onFinish() {
                            switchLight.setEnabled(true);
                        }
                    };
                    countDownTimer.start() ;
                }
                leds.forEach((e) -> {
                    Log.d("tag", e.toString());
                });
            }
        });

        imageWaterring = findViewById(R.id.imageWatering);
        switchWatering = findViewById(R.id.switchWatering);


        Sprinkler sprinkler = new Sprinkler(111,1); // value 1 on, 0 off
        if(led.getValue() == 1){
            switchWatering.setChecked(true);
            imageWaterring.setImageResource(R.drawable.ic_sprinkler_on);
        }else {
            switchWatering.setChecked(false);
            imageWaterring.setImageResource(R.drawable.ic_sprinkler_off);
        }
        switchWatering.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    led.setValue(1);
                    imageWaterring.setImageResource(R.drawable.ic_sprinkler_on);
                    CountDownTimer countDownTimer = new CountDownTimer(1000, 20) {
                        @Override
                        public void onTick(long l) {
                            switchWatering.setEnabled(false);
                        }

                        @Override
                        public void onFinish() {
                            switchWatering.setEnabled(true);
                        }
                    };
                    countDownTimer.start() ;
                } else {
                    led.setValue(0);
                    imageWaterring.setImageResource(R.drawable.ic_sprinkler_off);
                    CountDownTimer countDownTimer = new CountDownTimer(1000, 20) {
                        @Override
                        public void onTick(long l) {
                            switchWatering.setEnabled(false);
                        }

                        @Override
                        public void onFinish() {
                            switchWatering.setEnabled(true);
                        }
                    };
                    countDownTimer.start() ;
                }
                sprinklers.forEach((e) -> {
                    Log.d("tag", e.toString());
                });
            }
        });

        leds = new ArrayList<Led>();
        sprinklers = new ArrayList<Sprinkler>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changeDarkTheme:
                //Chuyển đổi theme
                themeIdcurrent = themeIdcurrent == R.style.AppTheme ?  R.style.Theme_AppCompat :R.style.AppTheme;

                //Lưu lại theme ID
                SharedPreferences locationpref = getApplicationContext()
                        .getSharedPreferences(FILE_USER, MODE_PRIVATE);
                SharedPreferences.Editor spedit = locationpref.edit();
                spedit.putInt(FILE_MODE_THEME, themeIdcurrent);
                spedit.apply();
                recreate();

                break;
            case R.id.logout:
                doSaveShared(FILE_USER_TOKEN_SESSION, "false");
                startActivity(new Intent(this, Login.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doSaveShared(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_USER, this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        // Save.
        editor.apply();
    }

    public void timer(View view) {

    }
}

