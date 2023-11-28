package com.example.appquanao.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.appquanao.R
import com.google.firebase.FirebaseApp

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // Delay để hiển thị Splash và sau đó chuyển sang màn hình chính
        Handler().postDelayed({
            // Tạo Intent để chuyển từ màn hình Splash sang màn hình chính (MainActivity)
            val mainIntent = Intent(this, LoginActivity::class.java)
            startActivity(mainIntent)
            finish() // Đóng SplashActivity sau khi chuyển màn hình
        }, 3000)
    }
}