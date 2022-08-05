package com.example.projectlogin.fragments

import android.content.Context
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.projectlogin.data.DeliveryInfo
import com.example.projectlogin.R
import com.example.projectlogin.data.UserInfo
import com.example.projectlogin.data.Users
import com.example.projectlogin.repository.Database
import com.example.projectlogin.repository.FirebaseCallback
import kotlin.math.sqrt

class Fragment1 : Fragment() {

    private lateinit var thisContext: Context
    private lateinit var listView: ListView
    private lateinit var locBtn: Button
    private lateinit var takeBtn: Button
    private lateinit var myView: View
    private lateinit var arrayAdapter: ArrayAdapter<DeliveryInfo>
    private var listInfo = arrayListOf<DeliveryInfo>()
    private var email = Users.getInstance().email
    private var userLat = 0.0
    private var userLng = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_1, container, false)
        thisContext = container?.context as Context

        listView = myView.findViewById(R.id.infoListView)
        locBtn = myView.findViewById(R.id.locBtn)
        takeBtn = myView.findViewById(R.id.btn_take)

        locBtn.setOnClickListener {
            readInfo()
        }
        takeBtn.setOnClickListener {
            takeItems()
        }
        return myView
    }

    private fun takeItems() {
        val positionChecked: SparseBooleanArray = listView.checkedItemPositions
        var deleteItem = listView.count - 1
        while (deleteItem >= 0) {
            if (positionChecked.get(deleteItem)) {
                val item = listInfo[deleteItem]
                arrayAdapter.remove(item)
                Database.removeUserChild(item.name.toString())
                val userInfo = UserInfo(
                    email, "take", item.name, item.address, item.size, item.weight,
                    item.fragile, item.lng, item.lat
                )
                Database.addUserInfo(userInfo)
            }
            deleteItem--
        }
        positionChecked.clear()
        arrayAdapter.notifyDataSetChanged()
        if (listView.count == 0)
            takeBtn.visibility = View.GONE
    }

    private fun readInfo() {
        if (!setLocation())
            return
        listInfo.clear()
        Database.readInfo(object : FirebaseCallback {
            override fun onCallBackInfo(listOfInfo: ArrayList<DeliveryInfo>) {
                for (info in listOfInfo)
                    if (inRange(info.lat.toString().toDouble(), info.lng.toString().toDouble()))
                        listInfo.add(info)
                if (listInfo.isNotEmpty()) {
                    arrayAdapter = ArrayAdapter(
                        thisContext, R.layout.list_view_multiple_choice, listInfo
                    )
                    listView.adapter = arrayAdapter
                    arrayAdapter.notifyDataSetChanged()
                    takeBtn.visibility = View.VISIBLE
                }
            }

            override fun onCallBackUserInfo(listOfInfo: ArrayList<UserInfo>) {}
        })
    }


    private fun inRange(lat: Double, lng: Double): Boolean {
        val disSquare = (lat - userLat) * (lat - userLat) + (lng - userLng) * (lng - userLng)
        val disSqrt = sqrt(disSquare)
        if (disSqrt < 1) return true
        return false
    }

    private fun setLocation(): Boolean {
        val latText = myView.findViewById<EditText>(R.id.et_lat)
        val lngText = myView.findViewById<EditText>(R.id.et_lng)
        if (latText.text.isEmpty()) {
            latText.error = "lat is require"
            latText.requestFocus()
            return false
        }
        if (lngText.text.isEmpty()) {
            lngText.error = "lat is require"
            lngText.requestFocus()
            return false
        }
        try {
            userLat = latText.text.toString().toDouble()
        } catch (e: Exception) {
            latText.error = "lat should be in type of double"
            latText.requestFocus()
            return false
        }

        try {
            userLng = lngText.text.toString().toDouble()
        } catch (e: Exception) {
            lngText.error = "lng should be in type of double"
            lngText.requestFocus()
            return false
        }
        latText.text.clear()
        lngText.text.clear()
        return true
    }
}