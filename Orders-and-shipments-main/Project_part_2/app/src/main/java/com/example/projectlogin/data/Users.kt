package com.example.projectlogin.data

data class Users( var email:String? , var password:String?) {
    companion object {
        @Volatile
        @JvmStatic
        private var instant: Users? = null

        @JvmStatic
        @JvmOverloads
        fun getInstance(email: String = "default", password: String = "default")
                : Users = instant ?: synchronized(this) {
            instant ?: Users(email, password).also { instant = it }
        }
    }
}