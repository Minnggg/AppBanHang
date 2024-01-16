package com.example.appquanao.admin

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appquanao.R
import com.example.appquanao.adapter.ProductAdapter
import com.example.appquanao.databinding.ActivityThemBinding
import com.example.appquanao.model.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

class ThemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThemBinding
    private var storage =  Firebase.storage
    private lateinit var uri:Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_them)
        setContentView(binding.root)
        storage = FirebaseStorage.getInstance()
        binding.btnThem.setOnClickListener(View.OnClickListener {
            storage.getReference("Image").child(System.currentTimeMillis().toString()).putFile(uri).addOnSuccessListener { taskSnapshot ->
                 taskSnapshot.metadata!!.reference!!.downloadUrl
                     .addOnSuccessListener {
                         binding.imgUrl.setText(it.toString())
                     }
            }
            if(binding.type.text.toString().length!=0
                &&binding.name.text.toString().length!=0
                &&binding.price.text.toString().length!=0
                &&binding.retailer.text.toString().length!=0
                &&binding.url.text.toString().length!=0
                &&binding.imgUrl.text.toString().length!=0){
                val database = Firebase.database
                val temp =  database.getReference("product/"+binding.type.text.toString()).get().addOnSuccessListener {
                    var idx = 0
                    for (snapshot in it.children) {
                        val yourObject = snapshot.getValue(ProductModel::class.java)
                        yourObject?.let {
                            idx = idx+1
                        }
                    }
                    them(idx)
                    Toast.makeText(this,"Thêm sản phẩm thành công",Toast.LENGTH_LONG).show()
                }.addOnFailureListener{
                }
            }
            else{
                Toast.makeText(this,"Vui lòng nhập đủ thông tin sản phẩm",Toast.LENGTH_LONG).show()
            }
        })
        val galleryImage = registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.img.setImageURI(it)
                if (it != null) {
                    uri = it
                }
            })
        binding.btnChonAnh.setOnClickListener(View.OnClickListener {
            galleryImage.launch("image/*")
        })
    }

    private fun them(idx: Int) {
        val sanpham = ProductModel(binding.retailer.text.toString(),binding.imgUrl.text.toString(),
            binding.name.text.toString(),binding.price.text.toString(),
            binding.url.text.toString(),binding.type.text.toString(),
            binding.type.text.toString()+idx.toString())
        Firebase.database.reference.child("product").child(binding.type.text.toString()).child(idx.toString()).setValue(sanpham)
    }
}