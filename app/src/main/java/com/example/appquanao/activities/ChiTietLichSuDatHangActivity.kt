package com.example.appquanao.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appquanao.Model.LichSuDonHangModel
import com.example.appquanao.R
import com.example.appquanao.adapter.ItemLichSuAdapter
import com.example.appquanao.databinding.ActivityChiTietLichSuDatHangBinding

class ChiTietLichSuDatHangActivity : AppCompatActivity() {
    lateinit var binding : ActivityChiTietLichSuDatHangBinding
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chi_tiet_lich_su_dat_hang)
        setContentView(binding.root)
        val bundle = intent.extras
        val lichSu= bundle?.getSerializable("data",LichSuDonHangModel::class.java)
        binding.odBtnBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
        binding.odTen.text = "Người nhận: "+lichSu?.nguoinhan?.name
        binding.odSDT.text = "SĐT người nhận: "+lichSu?.nguoinhan?.phone
        binding.odDiaChi.text = "Địa chỉ người nhận: "+lichSu?.nguoinhan?.address?.get(0)
        binding.tvSlItem.text = lichSu?.sanpham?.size.toString() +" items"
        binding.tvSTT.text = "Order #"+bundle?.get("stt")

        var sum = 0
        for (sanpham in lichSu?.sanpham!!){
            sum = sum + sanpham.price.toString().replace(".", "").toInt()* sanpham.soluong!!
        }
        binding.tvTien.text = "Tổng giá trị đơn hàng: " + String.format("%,d",sum)+" đ"


        //cài adapter cho recyclerview
        val adapter : ItemLichSuAdapter = ItemLichSuAdapter(lichSu?.sanpham!!,applicationContext)
        var layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvItemLichSu.layoutManager = layoutManager
        binding.rvItemLichSu.adapter = adapter


    }
}