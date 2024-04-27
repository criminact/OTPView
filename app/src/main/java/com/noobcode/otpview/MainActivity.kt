package com.noobcode.otpview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), OTPView.OTPListener {

    lateinit var otpView: OTPView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        otpView = findViewById(R.id.otpView)

        otpView.setOTPListener(this@MainActivity)

    }

    override fun onOTPCompleted(otp: String) {
        if(otp == "123456"){
            otpView.showSuccess()
        }else{
            otpView.showFailure()
        }
    }
}