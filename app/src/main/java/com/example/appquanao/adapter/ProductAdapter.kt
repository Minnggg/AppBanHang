package com.example.appquanao.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appquanao.Model.ProductModel
import com.example.appquanao.Model.User
import com.example.appquanao.R
import com.example.appquanao.activities.DetailProductActivity
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class ProductAdapter (private val dataList: List<ProductModel>,private  val context1: Context) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

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
            val intent = Intent(context1,DetailProductActivity::class.java)
            intent.putExtras(bundle)
            ContextCompat.startActivity(context1,intent,bundle)
        })

        holder.tvTensanpham.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("ten",data.name)
            bundle.putString("id",data.id)
            bundle.putString("gia",data.price)
            bundle.putString("anhsanpham",data.img_url)
            val intent = Intent(context1,DetailProductActivity::class.java)
            intent.putExtras(bundle)
            ContextCompat.startActivity(context1,intent,bundle)
        })
        holder.tvGiasanpham.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("ten",data.name)
            bundle.putString("id",data.id)
            bundle.putString("gia",data.price)
            bundle.putString("anhsanpham",data.img_url)
            val intent = Intent(context1,DetailProductActivity::class.java)
            intent.putExtras(bundle)
            ContextCompat.startActivity(context1,intent,bundle)
        })

        holder.btnAddWishLish.setOnClickListener(View.OnClickListener {

        })


    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}