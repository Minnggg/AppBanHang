package com.example.appquanao.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appquanao.Model.ProductModel
import com.example.appquanao.R
import com.example.appquanao.adapter.ProductAdapter
import com.example.appquanao.databinding.ActivityViewProductByCategoryBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class ViewProductByCategoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewProductByCategoryBinding
    private var objectList = mutableListOf<ProductModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_product_by_category)
        setContentView(binding.root)
        val database = Firebase.database
        val bundle = intent.extras
        if (bundle!=null)
        {
            val products =  database.getReference(bundle.getString("name",""))
            products.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    objectList = mutableListOf<ProductModel>()
                    for (snapshot in dataSnapshot.children) {
                        val yourObject = snapshot.getValue(ProductModel::class.java)
                        var idx=0
                        yourObject?.let {
                            objectList.add(it)
                        }
                    }
                    val adapter : ProductAdapter = ProductAdapter(objectList,applicationContext)
                    var layoutManager = GridLayoutManager(applicationContext,2)
                    binding.rvListProductByCategory.layoutManager = layoutManager
                    binding.rvListProductByCategory.adapter = adapter
                }
                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
    }
}