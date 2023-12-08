package com.example.appquanao.activities

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appquanao.Model.ProductModel
import com.example.appquanao.R
import com.example.appquanao.adapter.ProductAdapter
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class SearchActivity : AppCompatActivity() {
    lateinit var searchView: SearchView
    lateinit var adapter : ProductAdapter
    private var listSp  = mutableListOf<ProductModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        val myToolbar: Toolbar = findViewById(R.id.toolbar_search)
        val rvKetqua: RecyclerView = findViewById(R.id.rvKetqua)
        setSupportActionBar(myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val database = Firebase.database
        val tablet =  database.getReference("product/tablet")
        tablet.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listSp = mutableListOf<ProductModel>()
                for (snapshot in dataSnapshot.children) {
                    val yourObject = snapshot.getValue(ProductModel::class.java)
                    var idx=0
                    yourObject?.let {
                        listSp.add(it)
                    }
                }
                adapter = ProductAdapter(listSp,applicationContext)
                var layoutManager = GridLayoutManager(applicationContext,2)
                rvKetqua.layoutManager = layoutManager
                rvKetqua.adapter = adapter
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })



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