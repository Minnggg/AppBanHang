package com.example.appquanao.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.appquanao.model.User
import com.example.appquanao.R
import com.example.appquanao.databinding.ActivityEditProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class EditProfileActivity : AppCompatActivity() {

    lateinit var binding : ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_profile)
        setContentView(binding.root)
        val sharedPreferences = applicationContext.getSharedPreferences("AppQuanAo", Context.MODE_PRIVATE)
        val database = Firebase.database
        database.getReference("user/"+sharedPreferences.getString("idNguoiDung","").toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    binding.edHovaten.setText(user?.name.toString())
                    binding.edSDT.setText(user?.phone.toString())
                    binding.edDiachi.setText(user?.address?.get(0).toString())
                }
                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
        binding.btnXacNhan.setOnClickListener(View.OnClickListener {
            database.getReference("user").child(sharedPreferences.getString("idNguoiDung","").toString())
                .child("name").setValue(binding.edHovaten.text.toString().trim())
            database.getReference("user").child(sharedPreferences.getString("idNguoiDung","").toString())
                .child("phone").setValue(binding.edSDT.text.toString().trim())
            database.getReference("user").child(sharedPreferences.getString("idNguoiDung","").toString())
                .child("address").child("0").setValue(binding.edDiachi.text.toString().trim())
            Toast.makeText(applicationContext,"Thay đổi thành công",Toast.LENGTH_LONG).show()
            onBackPressed()
        })


    }
}