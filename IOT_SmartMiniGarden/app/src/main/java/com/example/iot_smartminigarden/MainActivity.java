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
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_smartminigarden.model.Sprinkler;
import com.example.iot_smartminigarden.model.StatusInfo;
import com.example.iot_smartminigarden.model.WeatherInfo;
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

public class MainActivity extends AppCompatActivity {
    ImageView imageLight;
    Switch switchLight;
    ImageView imageWaterring;
    Switch switchWatering;
    int themeIdcurrent;
    WeatherInfo weatherInfos;
    Sprinkler sprinkler= new Sprinkler();
    Led led =new Led();
    StatusInfo statusInfos;
    MqttAndroidClient client;
    public static Retrofit retrofit;
    public InterfaceNetwork interfaceNetwork;
    TextView tvTemperatureValue;
    TextView tvHumidityValue;
    Runnable timedTask;
    int idLed;
    String statusLed;
    int idSprinkler;
    String statusSprinkler;

    public void loadWeatherInfo() {
        interfaceNetwork.getWeatherInfo().enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                weatherInfos = response.body();
                Log.d("xxxxxxxxxxxxxxxxxxx", response.body().toString());
                tvTemperatureValue.setText(String.valueOf(weatherInfos.getTemperature()));
                tvHumidityValue.setText(String.valueOf(weatherInfos.getHumidity()));
            }

            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                t.printStackTrace();
                Log.d("xxxxxxxxxxxxxxxxxxx", t.getMessage());
                tvTemperatureValue.setText("Kh??ng c?? d??? li???u");
                tvHumidityValue.setText("Kh??ng c?? d??? li???u");
            }
        });
    }

    public void loadControls() {
        interfaceNetwork.controls().enqueue(new Callback<List<StatusInfo>>() {
            @Override
            public void onResponse(Call<List<StatusInfo>> call, Response<List<StatusInfo>> response) {
                List<StatusInfo> stts = response.body();

                statusInfos = response.body().get(0);
                Log.d("xxxxxxxxxxxxxxxxxxx", response.body().toString());
                idLed = statusInfos.getId();
                led.setId(idLed);
                statusLed = statusInfos.getStatusLed();
                led.setValue(statusLed);

                if (led.getValue().equals("on")) {
                    switchLight.setChecked(true);
                    imageLight.setImageResource(R.drawable.ic_light_on);
                } else {
                    switchLight.setChecked(false);
                    imageLight.setImageResource(R.drawable.ic_light_off);
                }

                statusInfos = response.body().get(stts.size() - 1);
                Log.d("xxxxxxxxxxxxxxxxxxx", response.body().toString());
                idSprinkler = Integer.parseInt(String.valueOf(statusInfos.getId()));
                sprinkler.setId(idSprinkler);
                statusSprinkler = statusInfos.getStatusLed();
                sprinkler.setValue(statusSprinkler);
                if (sprinkler.getValue().equals("on")) {
                    switchWatering.setChecked(true);
                    imageWaterring.setImageResource(R.drawable.ic_sprinkler_on);
                } else {
                    switchWatering.setChecked(false);
                    imageWaterring.setImageResource(R.drawable.ic_sprinkler_off);
                }
            }

            @Override
            public void onFailure(Call<List<StatusInfo>> call, Throwable t) {
                t.printStackTrace();
                Log.d("xxxxxxxxxxxxxxxxxxx", t.getMessage());
                led.setId(16);
                led.setValue("off");
                sprinkler.setId(17);
                sprinkler.setValue("off");
            }
        });
    }

    private void refresh() {
        Handler handler = new Handler();
        timedTask = new Runnable() {
            @Override
            public void run() {
                loadWeatherInfo();
                handler.postDelayed(timedTask, 5000);
                // refresh();
            }
        };
        handler.post(timedTask);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //?????c ID theme ???? l??u, n???u ch??a l??u th?? d??ng R.style.MyAppTheme
        SharedPreferences locationpref = getApplicationContext()
                .getSharedPreferences(FILE_USER, MODE_PRIVATE);
        themeIdcurrent = locationpref.getInt(FILE_MODE_THEME, R.style.AppTheme);
        Log.d("themss", themeIdcurrent + "");
        //Thi???t l???p theme cho Activity
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

// load data
        loadControls();
        loadWeatherInfo();
        refresh() ;
        imageLight = findViewById(R.id.imageLight);
        switchLight = findViewById(R.id.switchLight);
        imageWaterring = findViewById(R.id.imageWatering);
        switchWatering = findViewById(R.id.switchWatering);
        tvTemperatureValue = findViewById(R.id.textTemperatureValue);
        tvHumidityValue = findViewById(R.id.textHumidityValue);

        Log.d("............",String.valueOf(led.getValue()));
        switchLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    led.setValue("on");
                    imageLight.setImageResource(R.drawable.ic_light_on);
                    interfaceNetwork.turnOnLed(led.getId(), led.getValue()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(MainActivity.this, "B???t th??nh c??ng", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "B???t kh??ng th??nh c??ng", Toast.LENGTH_SHORT).show();
                        }
                    });
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
                    countDownTimer.start();
                } else {
                    led.setValue("off");
                    imageLight.setImageResource(R.drawable.ic_light_off);
                    interfaceNetwork.turnOffLed(led.getId(), led.getValue()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(MainActivity.this, "T???t th??nh c??ng", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "T???t kh??ng th??nh c??ng", Toast.LENGTH_SHORT).show();
                        }
                    });
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
                    countDownTimer.start();
                }
            }
        });

        switchWatering.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sprinkler.setValue("on");
                    imageWaterring.setImageResource(R.drawable.ic_sprinkler_on);
                    interfaceNetwork.turnOnSprinkler(sprinkler.getId(), sprinkler.getValue()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(MainActivity.this, "B???t th??nh c??ng", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "B???t kh??ng th??nh c??ng", Toast.LENGTH_SHORT).show();
                        }
                    });
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
                    countDownTimer.start();
                } else {
                    sprinkler.setValue("off");
                    imageWaterring.setImageResource(R.drawable.ic_sprinkler_off);
                    interfaceNetwork.turnOffSprinkler(sprinkler.getId(), sprinkler.getValue()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(MainActivity.this, "T???t th??nh c??ng", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "T???t kh??ng th??nh c??ng", Toast.LENGTH_SHORT).show();
                        }
                    });
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
                    countDownTimer.start();
                }
            }
        });
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
                //Chuy???n ?????i theme
                themeIdcurrent = themeIdcurrent == R.style.AppTheme ? R.style.Theme_AppCompat : R.style.AppTheme;

                //L??u l???i theme ID
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
}

