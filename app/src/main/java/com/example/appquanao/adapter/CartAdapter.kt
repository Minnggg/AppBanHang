package com.example.appquanao.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appquanao.Model.GioHangModel
import com.example.appquanao.R
import com.example.appquanao.activities.DetailProductActivity
import com.google.firebase.Firebase
import com.google.firebase.database.database

class CartAdapter(private val dataList: List<GioHangModel>, private  val context1: Context) : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAnhsanpham: ImageView = itemView.findViewById(R.id.imgAnhSanPham)
        var tvTensanpham: TextView = itemView.findViewById(R.id.tvItemTenSanPham)
        var tvGiasanpham: TextView = itemView.findViewById(R.id.tvItemGiaSanPham)
        var btnTru : ImageButton = itemView.findViewById(R.id.item_btnTru)
        var btnCong : ImageButton = itemView.findViewById(R.id.item_btnCong)
        var tvSoluong: TextView = itemView.findViewById(R.id.item_tvSoLuong)
        var tvThanhTien: TextView = itemView.findViewById(R.id.item_thanhtien)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_card_giohang, parent, false) // Thay "item_layout" bằng layout của item của bạn
        return CartAdapter.MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartAdapter.MyViewHolder, position: Int) {
        var data = dataList[position]
        holder.tvTensanpham.text = data.name
        holder.tvGiasanpham.text = data.price+" đ"
        Glide.with(context1).load(data.img_url.toString()).into(holder.imgAnhsanpham)

        var context : Context = context1
        var sharedPref : SharedPreferences = context.getSharedPreferences("AppQuanAo", Context.MODE_PRIVATE)
        val database = Firebase.database


        var soluong = data.soluong
        holder.tvThanhTien.text ="Thành tiền: "+ String.format("%,d", data.price.toString().replace(".", "").toInt()* soluong!!)+"đ"
        holder.tvSoluong.text = soluong.toString()

        holder.btnTru.setOnClickListener(View.OnClickListener {
            soluong = soluong!! - 1
            holder.tvSoluong.text = soluong.toString()
            holder.tvThanhTien.text ="Thành tiền: "+  String.format("%,d", data.price.toString().replace(".", "").toInt()* soluong!!)+"đ"
            if(soluong!=0) {

            }
            else {
                showConfirmationDialog()
                database.getReference("giohang")
                    .child(sharedPref.getString("idNguoiDung","").toString())
                    .child(data.id.toString())
                    .setValue(null)
            }
        })
        holder.btnCong.setOnClickListener(View.OnClickListener {
            soluong = soluong?.plus(1)
            holder.tvSoluong.text = soluong.toString()
            holder.tvThanhTien.text ="Thành tiền: "+  String.format("%,d", data.price.toString().replace(".", "").toInt()* soluong!!)+"đ"

            val database = Firebase.database
            data.soluong = soluong
            database.getReference("giohang")
                .child(sharedPref.getString("idNguoiDung","").toString())
                .child(data.id.toString())
                .setValue(data)
        })
        holder.imgAnhsanpham.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("ten",data.name)
            bundle.putString("id",data.id)
            bundle.putString("gia",data.price)
            bundle.putString("anhsanpham",data.img_url)
            val intent = Intent(context1, DetailProductActivity::class.java)
            intent.putExtras(bundle)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(context1,intent,bundle)
        })
        holder.tvTensanpham.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("ten",data.name)
            bundle.putString("id",data.id)
            bundle.putString("gia",data.price)
            bundle.putString("anhsanpham",data.img_url)
            val intent = Intent(context1, DetailProductActivity::class.java)
            intent.putExtras(bundle)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(context1,intent,bundle)
        })
        holder.tvGiasanpham.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("ten",data.name)
            bundle.putString("id",data.id)
            bundle.putString("gia",data.price)
            bundle.putString("anhsanpham",data.img_url)
            val intent = Intent(context1, DetailProductActivity::class.java)
            intent.putExtras(bundle)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(context1,intent,bundle)
        })
    }
    override fun getItemCount(): Int {
        return dataList.size
    }
    fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(context1.applicationContext)
        builder.setTitle("Xác nhận")
        builder.setMessage("Bạn có muốn tiếp tục không?")

        builder.setPositiveButton("Có") { dialogInterface: DialogInterface, i: Int ->
            // Xử lý khi người dùng chọn "Có"
            dialogInterface.dismiss()
            // Thêm code xử lý khi người dùng chọn "Có" ở đây
        }

        builder.setNegativeButton("Không") { dialogInterface: DialogInterface, i: Int ->
            // Xử lý khi người dùng chọn "Không"
            dialogInterface.dismiss()
            // Thêm code xử lý khi người dùng chọn "Không" ở đây
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}