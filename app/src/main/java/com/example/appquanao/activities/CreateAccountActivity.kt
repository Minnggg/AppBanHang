package com.example.appquanao.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.appquanao.model.User
import com.example.appquanao.R
import com.example.appquanao.databinding.ActivityCreateAccountBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.database.database

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
    //hàm đăng kỳ
    private fun signUp(email : String, password : String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                val database = Firebase.database
                it.user?.let { it1 ->
                    database.getReference("user")
                        .child(it1.uid.toString())
                        .setValue(User(null,binding.edFullName.text.toString().trim(),binding.edPhoneNumber.text.toString().trim()))
                }
                //cập nhật hồ sơ ng dùng
                val user = Firebase.auth.currentUser
                val profileUpdates = userProfileChangeRequest {
                    displayName = binding.edFullName.text.toString().trim()
                }
                user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("TAG", "update1: "+ auth.currentUser?.displayName)
                        }
                    }

                Toast.makeText(applicationContext,"Đăng kí thành công",Toast.LENGTH_LONG).show()
                    //chuyển sang màn đăng nhập
                startActivity(Intent(this, LoginActivity::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,it.message,Toast.LENGTH_LONG).show()
            }

    }
}