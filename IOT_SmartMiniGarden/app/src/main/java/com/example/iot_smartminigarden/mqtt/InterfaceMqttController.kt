package com.example.iot_smartminigarden.mqtt

interface InterfaceMqttController {
    //    Hàm để đẩy lên mqtt
    fun pub(toppic: String?, content: String?)

    //    Hàm đăng ký nhận những thay đổi từ mqtt
    fun sub(toppic: String?)
}
