package com.example.appquanao.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.appquanao.R
import com.example.appquanao.databinding.ActivityCreateAccountBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding : ActivityCreateAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_account)
        setContentView(binding.root)
        initView()
    }
    private fun initView() {
        binding.caBtnContinue.setOnClickListener(View.OnClickListener {
            val email = binding.caTvEmail.text.toString().trim()
            val password = binding.caTvPassword.text.toString().trim()
            if(email!=null && password !=null){
                signUp(email,password)
            }
        })
    }
    private fun signUp(email : String, password : String) {
        var check = false
        auth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                check = true
                Toast.makeText(applicationContext,"Thử lại",Toast.LENGTH_LONG)
                startActivity(Intent(this, LoginActivity::class.java))
            }
            .addOnFailureListener {
            }
        if (check == false) {
            Toast.makeText(applicationContext,"Thử lại",Toast.LENGTH_LONG)
        }
    }
}