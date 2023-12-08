package com.example.appquanao.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.appquanao.Model.GioHangModel
import com.example.appquanao.Model.ProductModel
import com.example.appquanao.R
import com.example.appquanao.databinding.ActivityDetailProductBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class DetailProductActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailProductBinding
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_product)
        setContentView(binding.root)
        val bundle = intent.extras
        if (bundle!=null)
        {
            Glide.with(applicationContext).load(bundle.getString("anhsanpham")).into(binding.imgAnhSanPham)
            binding.tvTen.text = bundle.get("ten").toString()
            binding.tvGia.text = bundle.get("gia").toString()
        }
        var cnt= 0
        binding.btnCong.setOnClickListener(View.OnClickListener {
            cnt = cnt+1
            binding.tvSoLuong.text = cnt.toString()
        })
        binding.btnTru.setOnClickListener(View.OnClickListener {
            if(cnt>0) {
                cnt = cnt-1
                binding.tvSoLuong.text = cnt.toString()
            }
            if(cnt ==0 ){
                var context : Context = applicationContext
                var sharedPref : SharedPreferences = context.getSharedPreferences("AppQuanAo", Context.MODE_PRIVATE)
                val database = Firebase.database
                database.getReference("giohang")
                    .child(sharedPref.getString("idNguoiDung","").toString())
                    .child(bundle?.get("id").toString())
                    .setValue(null)
            }
        })
        binding.btnThem.setOnClickListener(View.OnClickListener {
            if(binding.tvSoLuong.text.toString().toInt()!=0){
                var context : Context = applicationContext
                var sharedPref : SharedPreferences = context.getSharedPreferences("AppQuanAo", Context.MODE_PRIVATE)
                var editor : SharedPreferences.Editor = sharedPref.edit()
                var gioHangModel = GioHangModel(sharedPref.getString("idNguoiDung","").toString(),
                    bundle?.getString("anhsanpham").toString(),
                    bundle?.get("ten").toString(),
                    bundle?.get("gia").toString(),
                    bundle?.get("id").toString(),
                    binding.tvSoLuong.text.toString().toInt()
                )
                val database = Firebase.database
                database.getReference("giohang")
                    .child(sharedPref.getString("idNguoiDung","").toString())
                    .child(bundle?.get("id").toString())
                    .setValue(gioHangModel)
            }
            else
            {
                Toast.makeText(applicationContext,"Vui lòng chọn số lượng",Toast.LENGTH_LONG)
            }
        })
        binding.dtBtnBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })


        var context : Context = applicationContext
        var sharedPref : SharedPreferences = context.getSharedPreferences("AppQuanAo", Context.MODE_PRIVATE)
        val database = Firebase.database

        val data= bundle?.getSerializable("data",ProductModel::class.java)
        var wish_list = mutableListOf<ProductModel>()
        database.getReference("wish_list/").child(sharedPref.getString("idNguoiDung","").toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    wish_list = mutableListOf<ProductModel>()
                    for (snapshot in dataSnapshot.children) {
                        val yourObject = snapshot.getValue(ProductModel::class.java)
                        yourObject?.let {
                            wish_list.add(it)
                        }
                    }
                    if(wish_list!=null)
                    {
                        if(wish_list.contains(data))
                        {
                            binding.icHeart.setImageResource(R.drawable.is_heart)
                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                }
            })


        binding.icHeart.setOnClickListener(View.OnClickListener {
            database.getReference("wish_list/").child(sharedPref.getString("idNguoiDung","").toString()).get().addOnSuccessListener {dataSnapshot ->
                wish_list = mutableListOf<ProductModel>()
                for (snapshot in dataSnapshot.children) {
                    val yourObject = snapshot.getValue(ProductModel::class.java)
                    yourObject?.let {
                        wish_list.add(it)
                    }
                }
                if(wish_list.contains(data)){
                    binding.icHeart.setImageResource(R.drawable.heart)
                    database.getReference("wish_list/").child(sharedPref.getString("idNguoiDung","").toString()).child(data?.id.toString()).setValue(null)
                }
                else {
                    binding.icHeart.setImageResource(R.drawable.is_heart)
                    database.getReference("wish_list/").child(sharedPref.getString("idNguoiDung","").toString()).child(data?.id.toString()).setValue(data)
                }
            }.addOnFailureListener{}
        })



    }
}