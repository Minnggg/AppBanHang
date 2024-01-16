package com.example.appquanao.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.appquanao.R
import com.example.appquanao.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_admin)
        setContentView(binding.root)

        binding.Them.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,ThemActivity::class.java))
        })
        binding.Xoa.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,XoaActivity::class.java))
        })
        binding.Sua.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,SuaActivity::class.java))
        })
    }

}