package com.noobcode.otpview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var otpView = findViewById<OTPView>(R.id.otpView)

        otpView.setOTPListener(object : OTPView.OTPListener {
            override fun onOTPCompleted(otp: String) {
                Toast.makeText(this@MainActivity, "OTP Completed", Toast.LENGTH_LONG).show()
            }

        })

    }
}