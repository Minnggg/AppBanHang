package com.example.appquanao.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.appquanao.R
import com.example.appquanao.databinding.ActivityAdminBinding
import com.example.appquanao.databinding.ActivityXoaBinding
import com.google.firebase.Firebase
import com.google.firebase.database.database

class XoaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityXoaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_xoa)
        setContentView(binding.root)

        binding.btnXoa.setOnClickListener(View.OnClickListener {
            if(binding.edIdSanpham.text.toString().trim().length!=0&&binding.edTypeSanpham.text.toString().trim().length!=0){
                var database = Firebase.database.reference
                database.child("product").child(binding.edTypeSanpham.text.toString().trim()).child(binding.edIdSanpham.text.toString().trim()).setValue(null)
                Toast.makeText(this, "Xóa thành công",Toast.LENGTH_LONG).show()

            }
            else
            {
                Toast.makeText(this, "Vui lòng nhập ID sản phẩm",Toast.LENGTH_LONG).show()
            }
        })

    }
}