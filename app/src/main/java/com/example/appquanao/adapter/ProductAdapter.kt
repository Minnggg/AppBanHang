package com.example.appquanao.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appquanao.Model.GioHangModel
import com.example.appquanao.Model.ProductModel
import com.example.appquanao.R
import com.example.appquanao.activities.DetailProductActivity
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class ProductAdapter (private var dataList: List<ProductModel>,private  val context1: Context) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() , Filterable {

    private var dataOld = dataList
    // Tạo ViewHolder cho mỗi item trong RecyclerView
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var btnAddWishLish : ImageButton = itemView.findViewById(R.id.btnAddWishList)
        var imgAnhsanpham: ImageView = itemView.findViewById(R.id.imgAnh)
        var tvTensanpham: TextView = itemView.findViewById(R.id.tvTenSanPham)
        var tvGiasanpham: TextView = itemView.findViewById(R.id.tvGiaSanPham)
    }

    // Tạo ViewHolder từ layout của mỗi item và kết nối với MyViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_cardview, parent, false) // Thay "item_layout" bằng layout của item của bạn
        return MyViewHolder(view)
    }

    // Gán dữ liệu vào ViewHolder
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var context : Context = context1
        var sharedPref : SharedPreferences = context.getSharedPreferences("AppQuanAo", Context.MODE_PRIVATE)
        val database = Firebase.database


        val data = dataList[position]
        holder.tvTensanpham.text = data.name
        holder.tvGiasanpham.text = data.price+" đ"
        Glide.with(context1).load(data.img_url.toString()).into(holder.imgAnhsanpham)

        holder.imgAnhsanpham.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("ten",data.name)
            bundle.putString("id",data.id)
            bundle.putString("gia",data.price)
            bundle.putString("anhsanpham",data.img_url)
            bundle.putSerializable("data",data)
            val intent = Intent(context1,DetailProductActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtras(bundle)
            ContextCompat.startActivity(context1,intent,bundle)
        })
        holder.tvTensanpham.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("ten",data.name)
            bundle.putString("id",data.id)
            bundle.putString("gia",data.price)
            bundle.putString("anhsanpham",data.img_url)
            bundle.putSerializable("data",data)

            val intent = Intent(context1,DetailProductActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtras(bundle)
            ContextCompat.startActivity(context1,intent,bundle)
        })
        holder.tvGiasanpham.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("ten",data.name)
            bundle.putString("id",data.id)
            bundle.putString("gia",data.price)
            bundle.putSerializable("data",data)
            bundle.putString("anhsanpham",data.img_url)
            val intent = Intent(context1,DetailProductActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtras(bundle)
            ContextCompat.startActivity(context1,intent,bundle)
        })

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
                            holder.btnAddWishLish.setImageResource(R.drawable.is_heart)
                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        holder.btnAddWishLish.setOnClickListener(View.OnClickListener {
            database.getReference("wish_list/").child(sharedPref.getString("idNguoiDung","").toString()).get().addOnSuccessListener {dataSnapshot ->
                wish_list = mutableListOf<ProductModel>()
                for (snapshot in dataSnapshot.children) {
                    val yourObject = snapshot.getValue(ProductModel::class.java)
                    yourObject?.let {
                        wish_list.add(it)
                    }
                }
                if(wish_list.contains(data)){
                    holder.btnAddWishLish.setImageResource(R.drawable.heart)
                    database.getReference("wish_list/").child(sharedPref.getString("idNguoiDung","").toString()).child(data.id.toString()).setValue(null)

                }
                else {
                    holder.btnAddWishLish.setImageResource(R.drawable.is_heart)
                    database.getReference("wish_list/").child(sharedPref.getString("idNguoiDung","").toString()).child(data.id.toString()).setValue(data)
                }
            }.addOnFailureListener{}
            })
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    // Triển khai phương thức getFilter của Filterable
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val strSearch : String = constraint.toString()
                if(strSearch.isEmpty()){
                    dataList = dataOld
                }
                else
                {
                    var list = mutableListOf<ProductModel>()
                    for(item in dataOld){
                        if(item.name.toString().toLowerCase().contains(strSearch.toLowerCase()))
                        {
                            list.add(item)
                        }
                    }
                    dataList = list

                }
                val results = FilterResults()
                results.values = dataList
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataList = results?.values as List<ProductModel>
                notifyDataSetChanged()
            }
        }
    }
}