package com.example.projectlogin.repository

import com.example.projectlogin.data.DeliveryInfo
import com.example.projectlogin.data.UserInfo
import com.google.firebase.database.FirebaseDatabase

object Database {
    private val database = FirebaseDatabase.getInstance()
    private val referenceUser = database.getReference("Info")
    private val referenceUserInfo = database.getReference("UserInfo")
    fun readInfo(callback: FirebaseCallback) {
        referenceUser.get().addOnCompleteListener { task ->
            var listOfInfo: ArrayList<DeliveryInfo> = arrayListOf()
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    listOfInfo = result.children.map { snapShot ->
                        snapShot.getValue(DeliveryInfo::class.java)!!
                    } as ArrayList<DeliveryInfo>
                }
            }
            callback.onCallBackInfo(listOfInfo)
        }
    }
    fun readUserInfo(callback: FirebaseCallback) {
        referenceUserInfo.get().addOnCompleteListener { task ->
            var listOfInfo: ArrayList<UserInfo> = arrayListOf()
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    listOfInfo = result.children.map { snapShot ->
                        snapShot.getValue(UserInfo::class.java)!!
                    } as ArrayList<UserInfo>
                }
            }
            callback.onCallBackUserInfo(listOfInfo)
        }
    }
    fun removeUserChild(name: String) {
        referenceUser.child(name).removeValue()
    }
    fun removeUserInfoChild(name: String) {
        referenceUserInfo.child(name).removeValue()
    }
    fun addUserInfo(userInfo: UserInfo) {
        referenceUserInfo.child(userInfo.name.toString()).setValue(userInfo)
            .addOnSuccessListener {
            }.addOnFailureListener {
            }
    }
}
