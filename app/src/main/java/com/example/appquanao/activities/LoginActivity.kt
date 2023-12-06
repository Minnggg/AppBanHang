package com.example.appquanao.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        setContentView(binding.root)

        var context : Context = applicationContext
        var sharedPref : SharedPreferences = context.getSharedPreferences("AppQuanAo", Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = sharedPref.edit()

//        //check xem đã đăng nhập chưa
        if (sharedPref.getBoolean("dadangnhap",false))
        {
            startActivity(Intent(this, MainActivity::class.java))
        }
        // check xem có đang được lưu tkhoan matkhau hay khong
        if(sharedPref.getBoolean("luutk",false)){
            val tk  = sharedPref.getString("taikhoan","1")
            val mk = sharedPref.getString("matkhau","1")
            binding.lgTvEmail.setText(tk)
            binding.lgTvPassword.setText(mk)
            binding.checkbox.toggle()
        }
        else {
            binding.lgTvEmail.setText("")
            binding.lgTvPassword.setText("")
        }


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
                var context : Context = applicationContext
                var sharedPref : SharedPreferences = context.getSharedPreferences("AppQuanAo", Context.MODE_PRIVATE)
                var editor : SharedPreferences.Editor = sharedPref.edit()
                editor.putBoolean("dadangnhap",true)
                if(binding.checkbox.isChecked){
                    editor.putBoolean("luutk",true)
                    editor.putString("taikhoan",binding.lgTvEmail.text.toString().trim())
                    editor.putString("matkhau",binding.lgTvPassword.text.toString().trim())
                    editor.apply()
                }
                else {
                    editor.putBoolean("luutk",false)
                    editor.apply()
                }
                editor.putString("idNguoiDung",it.user?.uid)
                editor.apply()
                Toast.makeText(applicationContext,"Đăng nhập thành công.",Toast.LENGTH_LONG).show()

                startActivity(Intent(this, MainActivity::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Tài khoản mật khẩu sai,xin thử lại.",Toast.LENGTH_LONG).show()
            }
    }

}