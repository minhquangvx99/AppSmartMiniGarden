package com.example.iot_smartminigarden;

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.iot_smartminigarden.config.Config
import com.example.iot_smartminigarden.config.Config.*
import com.example.iot_smartminigarden.retrofit.InterfaceNetwork
import com.example.iot_smartminigarden.retrofit.RetrofitResponse
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class Login : AppCompatActivity() {
    var retrofit: Retrofit? = null
    var interfaceNetwork: InterfaceNetwork? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //        innit retrofit
        MainActivity.retrofit = RetrofitResponse.getInstance().retrofit
        interfaceNetwork = MainActivity.retrofit.create(InterfaceNetwork::class.java)
        textWarning.text = ""
        btnLogin.setOnClickListener(){
            /*if (inputUsername.text.toString().trim().length>0 && inputPassword.text.toString().trim().length>0){
                doSave(inputUsername.text.toString().trim(), inputPassword.text.toString().trim(), "true");
                textWarning.setTextColor(Color.GREEN)
                textWarning.text = "Đăng nhập Thành công"
                startActivity(Intent(this@Login,MainActivity::class.java))
                finish()
            }
            else{
                textWarning.setTextColor(Color.WHITE)
                textWarning.text = "Vui lòng nhập đủ thông tin"
            }*/
            if (inputUsername.text.toString().trim().length>0 && inputPassword.text.toString().trim().length>0){
                if(inputUsername.text.toString().trim() == FILE_USER_NAME && inputPassword.text.toString().trim() == FILE_USER_PASS){
                    doSave(inputUsername.text.toString().trim(), inputPassword.text.toString().trim(), "true");
                    textWarning.setTextColor(Color.GREEN)
                    textWarning.text = "Đăng nhập Thành công"
                    startActivity(Intent(this@Login,MainActivity::class.java))
                    finish()
                } else{
                    textWarning.setTextColor(Color.RED)
                    textWarning.text = "Đăng nhập thất bại"
                    doSaveShared(FILE_USER_TOKEN_SESSION,"false");
                }
            }
            else{
                textWarning.setTextColor(Color.WHITE)
                textWarning.text = "Vui lòng nhập đủ thông tin"
            }

        }
    }
    private fun loadToken() : String? {
        val sharedPreferences = getSharedPreferences(Config.FILE_USER, Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            return sharedPreferences.getString(Config.FILE_USER_TOKEN_SESSION, "")
        }
        return null
    }
    private fun loadUser() {
        val sharedPreferences = getSharedPreferences(FILE_USER, Context.MODE_PRIVATE)
        if (sharedPreferences != null) {

        } else {

        }
    }
    private fun doSaveShared(key: String, value: String) {
        val sharedPreferences = getSharedPreferences(FILE_USER, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        // Save.
        editor.apply()
    }
    private fun doSave(username: String, pass: String, token: String) {
        val sharedPreferences = getSharedPreferences(FILE_USER, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(FILE_USER_NAME, username)
        editor.putString(FILE_USER_PASS, pass)
        editor.putString(FILE_USER_TOKEN_SESSION, token)
        // Save.
        editor.apply()
    }
}
