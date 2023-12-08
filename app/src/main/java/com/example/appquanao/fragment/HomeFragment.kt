package com.example.appquanao.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appquanao.Model.ProductModel
import com.example.appquanao.R
import com.example.appquanao.activities.CartActivity
import com.example.appquanao.activities.DetailProductActivity
import com.example.appquanao.activities.SearchActivity
import com.example.appquanao.activities.ViewProductByCategoryActivity
import com.example.appquanao.adapter.ProductAdapter
import com.example.appquanao.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class HomeFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private var objectList = mutableListOf<ProductModel>()
    private var wishlist = mutableListOf<ProductModel>()

    private lateinit var binding :FragmentHomeBinding
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        val context1 = this?.context

        sharedPreferences = requireActivity().getSharedPreferences("AppQuanAo", Context.MODE_PRIVATE)
        context1?.let { getDataFromFirebase(it) }


        binding.btnCart.setOnClickListener(View.OnClickListener {
            startActivity(Intent(context,CartActivity::class.java))
        })


        binding.seeAllTablet.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("name","product/tablet")
            val intent = Intent(context, ViewProductByCategoryActivity::class.java)
            intent.putExtras(bundle)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent, bundle)
        })
        binding.seeAllLaptop.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("name","product/laptop")
            val intent = Intent(context, ViewProductByCategoryActivity::class.java)
            intent.putExtras(bundle)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent, bundle)
        })
        binding.seeAllSmartPhone.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("name","product/smartphone")
            val intent = Intent(context, ViewProductByCategoryActivity::class.java)
            intent.putExtras(bundle)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent, bundle)
        })

        binding.btnSearch.setOnClickListener(View.OnClickListener {
            startActivity(Intent(context,SearchActivity::class.java))
        })

        return binding.root
    }
    private fun getDataFromFirebase(context: Context) {
        val database = Firebase.database
        ActionViewFlipper(database)
        initWishlish(context,database)
        initViewLaptop(context,database)
        initViewSmartphone(context,database)
        initTablet(context,database)
    }

    private fun initWishlish(context: Context, database: FirebaseDatabase) {
        val wishlistData =  database.getReference("wishlist/"+sharedPreferences.getString("idnguoidung",""))
        wishlistData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                wishlist = mutableListOf<ProductModel>()
                for (snapshot in dataSnapshot.children) {
                    val yourObject = snapshot.getValue(ProductModel::class.java)
                    yourObject?.let {
                        wishlist.add(it)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }


    private fun initTablet(context: Context,database: FirebaseDatabase) {
        val tablet =  database.getReference("product/tablet")
        tablet.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                objectList = mutableListOf<ProductModel>()
                for (snapshot in dataSnapshot.children) {
                    val yourObject = snapshot.getValue(ProductModel::class.java)
                    var idx=0
                    yourObject?.let {
                        objectList.add(it)
                    }
                }
                val adapter : ProductAdapter = ProductAdapter(objectList.reversed().take(10),context)
                var layoutManager = LinearLayoutManager(context)
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                binding.rvTablet.layoutManager = layoutManager
                binding.rvTablet.adapter = adapter
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
    private fun initViewSmartphone(context: Context,database: FirebaseDatabase) {
        val smartphone =  database.getReference("product/smartphone")
        smartphone.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                objectList = mutableListOf<ProductModel>()
                for (snapshot in dataSnapshot.children) {
                    val yourObject = snapshot.getValue(ProductModel::class.java)
                    var idx=0
                    yourObject?.let {
                        objectList.add(it)
                    }
                }
                val adapter : ProductAdapter = ProductAdapter(objectList.take(10),context)
                var layoutManager = LinearLayoutManager(context)
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                binding.rvSmartPhone.layoutManager = layoutManager
                binding.rvSmartPhone.adapter = adapter
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    private fun initViewLaptop(context: Context,database :FirebaseDatabase) {
        val laptop =  database.getReference("product/laptop")
        laptop.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                objectList = mutableListOf()

                for (snapshot in dataSnapshot.children) {
                    val yourObject = snapshot.getValue(ProductModel::class.java)
                    yourObject?.let {
                        objectList.add(it)

                    }
                }
                val adapter : ProductAdapter = ProductAdapter(objectList.reversed().take(10),context)
                var layoutManager = LinearLayoutManager(context)
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                binding.rvLaptop.layoutManager = layoutManager
                binding.rvLaptop.adapter = adapter
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
    private fun ActionViewFlipper(database :FirebaseDatabase) {
        val context1 = this?.context

        val newProduct =  database.getReference("new_product")
        newProduct.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                objectList = mutableListOf()

                for (snapshot in dataSnapshot.children) {
                    val yourObject = snapshot.getValue(ProductModel::class.java)
                    yourObject?.let {
                        val imageView = ImageView(context)
                        if (context1 != null) {
                            Glide.with(context1).load(it.img_url.toString().trim()).into(imageView)
                        }
                        binding.vfHome.addView(imageView)
                    }
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
        binding.vfHome.flipInterval = 3000
        binding.vfHome.isAutoStart = true
        binding.vfHome.inAnimation = AnimationUtils.loadAnimation(context1,R.anim.slider_home_in)
        binding.vfHome.outAnimation = AnimationUtils.loadAnimation(context1,R.anim.slider_home_out)
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


