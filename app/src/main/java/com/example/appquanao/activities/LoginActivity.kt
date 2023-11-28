package com.example.appquanao.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.appquanao.R
import com.example.appquanao.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.lgTvCreate.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,CreateAccountActivity::class.java))
        })
        binding.lgTvReset.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,ForgotPasswordActivity::class.java))
        })
        binding.lgBtnContinue.setOnClickListener(View.OnClickListener {
            val email = binding.lgTvEmail.text.toString().trim()
            val password = binding.lgTvPassword.text.toString().trim()
            if(email!=null && password !=null){
                signIn(email,password)
            }
        })

    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Tài khoản mật khẩu sai,xin thử lại.",Toast.LENGTH_LONG)
            }
    }

}