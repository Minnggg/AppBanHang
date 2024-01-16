package com.example.appquanao.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.appquanao.model.LichSuDonHangModel
import com.example.appquanao.R
import com.example.appquanao.activities.ChiTietLichSuDatHangActivity

class LichSuDatHangAdapter(private val dataList: List<LichSuDonHangModel>, private val context1: Context) : RecyclerView.Adapter<LichSuDatHangAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNumOrder : TextView = itemView.findViewById(R.id.tvNumOrder)
        var tvNumProduct: TextView = itemView.findViewById(R.id.tvNumProduct)
        var item : CardView = itemView.findViewById(R.id.cvItem)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LichSuDatHangAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lichsudathang, parent, false) // Thay "item_layout" bằng layout của item của bạn
        return LichSuDatHangAdapter.MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: LichSuDatHangAdapter.MyViewHolder, position: Int) {
        val data = dataList[position]
        holder.tvNumOrder.text =  "Order#"+(dataList.size-position).toString()
        holder.tvNumProduct.text = data.sanpham?.size.toString()+" items"
        holder.item.setOnClickListener(View.OnClickListener {
            var intent = Intent(context1,ChiTietLichSuDatHangActivity::class.java)
            var bundle = Bundle()
            bundle.putSerializable("data",data)
            bundle.putString("stt",(dataList.size-position).toString())
            intent.putExtras(bundle)
            ContextCompat.startActivity(context1,intent,bundle)
        })
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}