package com.example.appquanao.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appquanao.Model.GioHangModel
import com.example.appquanao.R
class ItemLichSuAdapter(private val dataList: List<GioHangModel>, private  val context1: Context) : RecyclerView.Adapter<ItemLichSuAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAnhsanpham: ImageView = itemView.findViewById(R.id.ls_imgAnhSanPham)
        var tvTensanpham: TextView = itemView.findViewById(R.id.ls_tvItemTenSanPham)
        var tvGiasanpham: TextView = itemView.findViewById(R.id.ls_tvItemGiaSanPham)
        var tvSoluong: TextView = itemView.findViewById(R.id.ls_item_tvSoLuong)
        var tvThanhTien: TextView = itemView.findViewById(R.id.ls_item_thanhtien)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemLichSuAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_card_lichsu, parent, false) // Thay "item_layout" bằng layout của item của bạn
        return ItemLichSuAdapter.MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemLichSuAdapter.MyViewHolder, position: Int) {
        var data = dataList[position]
        holder.tvTensanpham.text = data.name
        holder.tvGiasanpham.text = data.price+" đ"
        Glide.with(context1).load(data.img_url.toString()).into(holder.imgAnhsanpham)
        var context : Context = context1
        var soluong = data.soluong
        holder.tvThanhTien.text ="Thành tiền: "+ String.format("%,d", data.price.toString().replace(".", "").toInt()* soluong!!)+"đ"
        holder.tvSoluong.text = soluong.toString()
    }
    override fun getItemCount(): Int {
        return dataList.size
    }

}