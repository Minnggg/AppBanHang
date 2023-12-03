package com.example.appquanao.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appquanao.Model.GioHangModel
import com.example.appquanao.Model.ProductModel
import com.example.appquanao.R
import com.example.appquanao.adapter.ProductAdapter
import com.example.appquanao.databinding.ActivityCartBinding
import com.example.appquanao.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class CartActivity : AppCompatActivity() {
    private var ListGioHang = mutableListOf<GioHangModel>()
    private lateinit var binding : ActivityCartBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart)
        setContentView(binding.root)

        val database = Firebase.database
        getListGioHang(applicationContext,database)
    }

    private fun getListGioHang(context: Context, database: FirebaseDatabase) {
        var sharedPref : SharedPreferences = context.getSharedPreferences("AppQuanAo", Context.MODE_PRIVATE)
        val giohang =  database.getReference("giohang/"+sharedPref.getString("idNguoiDung","").toString())
        giohang.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                ListGioHang = mutableListOf<GioHangModel>()
                for (snapshot in dataSnapshot.children) {
                    val yourObject = snapshot.getValue(GioHangModel::class.java)
//                    Log.d("TAG", "soluong: "+yourObject.toString())
                    yourObject?.let {
                        ListGioHang.add(it)
                    }
                }
                Log.d("TAG", "soluong1: "+ListGioHang.size)
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
}