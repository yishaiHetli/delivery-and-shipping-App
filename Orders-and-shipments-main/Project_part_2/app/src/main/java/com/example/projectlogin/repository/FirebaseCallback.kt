package com.example.projectlogin.repository

import com.example.projectlogin.data.DeliveryInfo
import com.example.projectlogin.data.UserInfo

interface FirebaseCallback {
    fun onCallBackInfo(listOfInfo: ArrayList<DeliveryInfo>)
    fun onCallBackUserInfo(listOfInfo: ArrayList<UserInfo>)
}