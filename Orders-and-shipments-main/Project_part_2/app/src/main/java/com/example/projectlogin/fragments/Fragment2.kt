package com.example.projectlogin.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.SparseBooleanArray
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.projectlogin.R
import com.example.projectlogin.data.DeliveryInfo
import com.example.projectlogin.data.UserInfo
import com.example.projectlogin.data.Users
import com.example.projectlogin.repository.Database
import com.example.projectlogin.repository.FirebaseCallback


class Fragment2 : Fragment() {
    private var listInfo = arrayListOf<UserInfo>()
    private lateinit var thisContext: Context
    private var email = Users.getInstance().email
    private lateinit var listView: ListView
    private lateinit var arrayAdapter: ArrayAdapter<UserInfo>
    private lateinit var deliveredBtn: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val myView = inflater.inflate(R.layout.fragment_2, container, false)
        thisContext = container?.context as Context
        deliveredBtn = myView.findViewById(R.id.btn_delivered)
        listView = myView.findViewById(R.id.list_info)
        readInfo()
        deliveredBtn.setOnClickListener {
            deliveredItem()
        }
        return myView
    }

    private fun deliveredItem() {
        if (listInfo.isNotEmpty()) {
            val positionChecked: SparseBooleanArray = listView.checkedItemPositions
            var deleteItem = listView.count - 1
            while (deleteItem >= 0) {
                if (positionChecked.get(deleteItem)) {
                    val item = listInfo[deleteItem]
                    arrayAdapter.remove(item)
                    Database.removeUserInfoChild(item.name.toString())
                    item.status = "delivered"
                    broadcastSend("Delivery update: $item")
                    Database.addUserInfo(item)
                }
                deleteItem--
            }
            positionChecked.clear()
            arrayAdapter.notifyDataSetChanged()
            if (listView.count == 0)
                deliveredBtn.visibility = View.GONE
        }
    }
    private fun broadcastSend(data:String) {
        val intent = Intent("project_part1_5173_9142.ACTION")
        intent.putExtra("project_part1_5173_9142.EXTRA_TEXT", data)
        activity?.sendBroadcast(intent)
    }
    private fun readInfo() {
        listInfo.clear()
        Database.readUserInfo(object : FirebaseCallback {
            override fun onCallBackInfo(listOfInfo: ArrayList<DeliveryInfo>) {
            }

            override fun onCallBackUserInfo(listOfInfo: ArrayList<UserInfo>) {
                for (info in listOfInfo) {
                    if (info.email.toString() == email && info.status.toString() == "take")
                        listInfo.add(info)
                }
                if (listInfo.isNotEmpty()) {
                    arrayAdapter = ArrayAdapter(
                        thisContext, R.layout.list_view_multiple_choice, listInfo
                    )
                    listView.adapter = arrayAdapter
                    arrayAdapter.notifyDataSetChanged()
                    deliveredBtn.visibility = View.VISIBLE
                }
            }
        })
    }
}