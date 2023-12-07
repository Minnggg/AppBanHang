package com.example.appquanao.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appquanao.Model.LichSuDonHangModel
import com.example.appquanao.Model.GioHangModel
import com.example.appquanao.Model.User
import com.example.appquanao.R
import com.example.appquanao.adapter.LichSuDatHangAdapter
import com.example.appquanao.databinding.FragmentReceiptBinding
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
 * Use the [ReceiptFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReceiptFragment : Fragment() {
    var user = User()
    var listOfGio = mutableListOf<GioHangModel>()
    lateinit var database: FirebaseDatabase
    private var ListDonHang = mutableListOf<LichSuDonHangModel>()
    lateinit var sharedPreferences :SharedPreferences
    lateinit var binding : FragmentReceiptBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database = Firebase.database
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_receipt, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("AppQuanAo", Context.MODE_PRIVATE)
        val context1 = this?.context

        initView(context1)
        return binding.root
    }

    private fun initView(context1: Context?) {

        database.getReference("lichsudathang/"+sharedPreferences.getString("idNguoiDung","").toString())
            .child("sodon").get().addOnSuccessListener {
                if(it.value != null){
                    if(it.value.toString().toInt() == 0 ){
                    }
                    else
                    {
                        binding.imgOrders.imageAlpha = 0
                        binding.textNoOrders.isVisible = false
                        //lay list lich su
                        for (i in 1 until it.value.toString().toInt()+1 ){
                             user = User()
                             listOfGio = mutableListOf<GioHangModel>()
                            //lay nguoi nhan
                            database.getReference("lichsudathang/"+sharedPreferences.getString("idNguoiDung","").toString()).child(i.toString())
                                .child("nguoinhan").get().addOnSuccessListener {us ->
                                    user = us.getValue(User::class.java)!!

                                    //lay danh sach san pham
                                    database.getReference("lichsudathang/"+sharedPreferences.getString("idNguoiDung","").toString()).child(i.toString())
                                        .child("sanpham").addValueEventListener(object : ValueEventListener {
                                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                                listOfGio = mutableListOf<GioHangModel>()
                                                for (snapshot in dataSnapshot.children) {
                                                    val yourObject = snapshot.getValue(GioHangModel::class.java)
                                                    yourObject?.let {gh->
                                                        listOfGio.add(gh)
                                                    }
                                                }
                                                ListDonHang.add(LichSuDonHangModel(user,listOfGio))

                                                if(i==it.value.toString().toInt()){
                                                    var adapter = LichSuDatHangAdapter(ListDonHang.reversed(),context1!!)
                                                    var layoutManager = LinearLayoutManager(context)
                                                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                                                    binding.rvReciept.layoutManager = layoutManager
                                                    binding.rvReciept.adapter = adapter
                                                }
                                            }
                                            override fun onCancelled(databaseError: DatabaseError) {
                                            }
                                        })

                                }
                        }
                    }
                }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReceiptFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReceiptFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}