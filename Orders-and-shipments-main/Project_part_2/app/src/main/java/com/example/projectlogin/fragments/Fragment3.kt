package com.example.projectlogin.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.projectlogin.R
import com.example.projectlogin.data.DeliveryInfo
import com.example.projectlogin.data.UserInfo
import com.example.projectlogin.data.Users
import com.example.projectlogin.repository.Database
import com.example.projectlogin.repository.FirebaseCallback


class Fragment3 : Fragment() {
    private var listInfo = arrayListOf<UserInfo>()
    private lateinit var thisContext: Context
    private var email = Users.getInstance().email
    private lateinit var listView: ListView
    private lateinit var arrayAdapter: ArrayAdapter<UserInfo>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView = inflater.inflate(R.layout.fragment_3, container, false)
        thisContext = container?.context as Context
        listView = myView.findViewById(R.id.list_view_delivered)
        readInfo()
        return myView
    }

    private fun readInfo() {
        listInfo.clear()
        Database.readUserInfo(object : FirebaseCallback {
            override fun onCallBackInfo(listOfInfo: ArrayList<DeliveryInfo>) {
            }

            override fun onCallBackUserInfo(listOfInfo: ArrayList<UserInfo>) {
                for (info in listOfInfo) {
                    if (info.email.toString() == email && info.status.toString() == "delivered")
                        listInfo.add(info)
                }
                if (listInfo.isNotEmpty()) {
                    arrayAdapter = ArrayAdapter(
                        thisContext, android.R.layout.simple_list_item_1, listInfo
                    )
                    listView.adapter = arrayAdapter
                    arrayAdapter.notifyDataSetChanged()
                }
            }
        })
    }
}