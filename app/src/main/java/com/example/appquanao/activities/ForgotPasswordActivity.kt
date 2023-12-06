package com.example.appquanao.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.appquanao.R
import com.example.appquanao.databinding.ActivityForgotPasswordBinding
import com.example.appquanao.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val binding : ActivityForgotPasswordBinding = DataBindingUtil.setContentView(this,R.layout.activity_forgot_password)
        auth = Firebase.auth

        binding.btnSendEmail.setOnClickListener(View.OnClickListener {
            auth.sendPasswordResetEmail(binding.fgpwEmail.text.toString().trim())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this,LoginActivity::class.java))
                        Toast.makeText(applicationContext,"Đã gửi email reset",Toast.LENGTH_LONG).show()
                    }
                }
        })
    }

}