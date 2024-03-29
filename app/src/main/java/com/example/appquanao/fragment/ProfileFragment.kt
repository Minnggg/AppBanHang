package com.example.appquanao.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.appquanao.model.User
import com.example.appquanao.R
import com.example.appquanao.activities.EditProfileActivity
import com.example.appquanao.activities.Splash
import com.example.appquanao.activities.ViewProductByCategoryActivity
import com.example.appquanao.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("AppQuanAo", Context.MODE_PRIVATE)
        initView()
        return binding.root
    }

    private fun initView() {
        // xử lý đăng xuất
        dangxuat()
        hienThiThongTinUser()
        binding.btnEdit.setOnClickListener(View.OnClickListener {
            var intent = Intent(this?.context,EditProfileActivity::class.java)
            startActivity(intent)
        })
        //chức năng address
        binding.btnAddress.setOnClickListener(View.OnClickListener {
            Toast.makeText(context,"Chức năng đang phát triển",Toast.LENGTH_LONG).show()
        })

        //chức năng hiển thị wish_list
        var sharedPref = context?.getSharedPreferences("AppQuanAo", Context.MODE_PRIVATE)
        binding.btnWishlish.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("name","wish_list/"+sharedPref?.getString("idNguoiDung","").toString())
            val intent = Intent(context, ViewProductByCategoryActivity::class.java)
            intent.putExtras(bundle)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent, bundle)
        })
    }

    private fun hienThiThongTinUser() {
        val database = Firebase.database
        database.getReference("user/"+sharedPreferences.getString("idNguoiDung","").toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    binding.pfTvName.text=user?.name.toString()
                    binding.pfTvPhone.text=user?.phone.toString()
                    binding.pfTvAddress.text = user?.address?.get(0).toString()
                }
                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
    }
    private fun dangxuat() {
        val context1 = this?.context
        binding.btnLogout.setOnClickListener(View.OnClickListener {
            var editor : SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean("dadangnhap",false)
            editor.putString("idNguoiDung","")
            editor.apply()
            Firebase.auth.signOut()
            Toast.makeText(context1,"Đăng xuất thành công",Toast.LENGTH_LONG).show()
            startActivity(Intent(context1,Splash::class.java))
        })
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}