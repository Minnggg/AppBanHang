package com.example.appquanao.activities

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appquanao.model.ProductModel
import com.example.appquanao.R
import com.example.appquanao.adapter.ProductAdapter
import com.example.appquanao.databinding.ActivityViewProductByCategoryBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class ViewProductByCategoryActivity : AppCompatActivity() {
    lateinit var searchView: SearchView
    lateinit var adapter : ProductAdapter


    lateinit var binding: ActivityViewProductByCategoryBinding
    private var objectList = mutableListOf<ProductModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_product_by_category)
        setContentView(binding.root)
        val database = Firebase.database
        val bundle = intent.extras


        setSupportActionBar(binding.toolbarSearchCategory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (bundle!=null)
        {
            val products =  database.getReference(bundle.getString("name",""))
            products.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    objectList = mutableListOf<ProductModel>()
                    for (snapshot in dataSnapshot.children) {
                        val yourObject = snapshot.getValue(ProductModel::class.java)
                        yourObject?.let {
                            objectList.add(it)
                        }
                    }
                    adapter = ProductAdapter(objectList,applicationContext)
                    var layoutManager = GridLayoutManager(applicationContext,2)
                    binding.rvListProductByCategory.layoutManager = layoutManager
                    binding.rvListProductByCategory.adapter = adapter
                }
                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // hoặc thực hiện hành động mong muốn
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if(!searchView.isIconified)
        {
            searchView.isIconified = true
            return
        }
        super.onBackPressed()
    }



}