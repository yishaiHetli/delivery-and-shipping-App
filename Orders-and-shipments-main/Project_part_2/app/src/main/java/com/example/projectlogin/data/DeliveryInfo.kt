package com.example.projectlogin.data

data class DeliveryInfo
    (val name:String? = null,val address:String? = null,val size:String? = null
     ,val weight:String? = null,val fragile:String? = null,var lng:String? = null,var lat:String? = null) {
    override fun toString(): String {
        return "Name: $name, Address: $address, Size : $size," +
                " Weight: $weight, Fragile: $fragile, " +
                "Location in: Lat: $lat, Lng: $lng "
    }
}