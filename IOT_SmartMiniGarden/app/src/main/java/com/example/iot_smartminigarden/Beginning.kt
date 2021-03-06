package com.example.iot_smartminigarden;

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.iot_smartminigarden.config.Config
import kotlinx.android.synthetic.main.activity_beginning.*


class Beginning : AppCompatActivity() {
    var token: String = ""
    override fun onStart() {
        super.onStart()
        loadToken()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beginning)

        val animation: Animation
        animation = AnimationUtils.loadAnimation(applicationContext,
                R.anim.top_animation)
        imgLogo.startAnimation(animation)
        textLogo.startAnimation(AnimationUtils.loadAnimation(applicationContext,
                R.anim.bottom_animation))

        var countDownTimer = object : CountDownTimer(2000, 10) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                if (token.equals("true")) {
                    var intent = Intent(this@Beginning, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    var intent = Intent(this@Beginning, Login::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }
        countDownTimer.start()
    }

    private fun loadToken() {
        val sharedPreferences = getSharedPreferences(Config.FILE_USER, Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            token = sharedPreferences.getString(Config.FILE_USER_TOKEN_SESSION, "").toString();
            Log.d("token",token)
        }
    }
}
