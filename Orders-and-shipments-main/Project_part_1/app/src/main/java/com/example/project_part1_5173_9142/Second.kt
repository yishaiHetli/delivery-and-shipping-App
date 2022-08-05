package com.example.project_part1_5173_9142

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class Second : AppCompatActivity() {
    private val broadcastReceiver = ReceivedBroadcast()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val filter = IntentFilter("project_part1_5173_9142.ACTION")
        registerReceiver(broadcastReceiver, filter)
        val packageType = listOf("envelope", "small package", "big package")
        val adapter1 =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, packageType)
        val selectPackage = findViewById<Spinner>(R.id.selectPackage)
        selectPackage.adapter = adapter1
        var size = 0
        selectPackage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                size = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        val fragile = listOf("yes", "no")
        var fragile1 = 0
        val adapter2 = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, fragile)
        val isFragile = findViewById<Spinner>(R.id.isFragile)
        isFragile.adapter = adapter2
        isFragile.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                fragile1 = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }


        val addOrder = findViewById<Button>(R.id.addOrder)
        addOrder.setOnClickListener {
            val name = findViewById<View>(R.id.name) as EditText
            val weight = findViewById<View>(R.id.weight) as EditText
            val address = findViewById<View>(R.id.address) as EditText
            val lng = findViewById<View>(R.id.lng) as EditText
            val lat = findViewById<View>(R.id.lat) as EditText
            var isValid: Boolean
            isValid = !((name.text).toString().matches("-?\\d+(\\.\\d+)?".toRegex()))

            if ((name.text).toString() == "" || !isValid) {
                Toast.makeText(this, "the filed name not valid", Toast.LENGTH_SHORT).show()
            }
            isValid = (weight.text).toString().matches("-?\\d+(\\.\\d+)?".toRegex())
            if (!isValid) {
                Toast.makeText(
                    this,
                    "the filed weight can contain only numbers",
                    Toast.LENGTH_SHORT
                ).show()

            }
            if (isValid) {
                if ((address.text).toString().isEmpty()) {
                    isValid = false
                    Toast.makeText(this, "the filed address can't be empty", Toast.LENGTH_SHORT)
                        .show()
                }
                try {
                    (lng.text).toString().toFloat()
                } catch (e: Exception) {
                    isValid = false
                    Toast.makeText(
                        this,
                        "the filed LNG can contain only numbers",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (isValid) {
                try {
                    (lat.text).toString().toFloat()
                } catch (e: Exception) {
                    isValid = false
                    Toast.makeText(
                        this,
                        "the filed LAT can contain only numbers",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            if (isValid) {
                val database = FirebaseDatabase.getInstance().getReference("Info")
                val size1 = packageType[size]
                val fragile2 = fragile[fragile1]
                val delivery = DeliveryInfo(
                    name.text.toString(),
                    address.text.toString(),
                    size1,
                    weight.text.toString(),
                    fragile2,
                    lng.text.toString(),
                    lat.text.toString()
                )
                database.child(name.text.toString()).setValue(delivery).addOnSuccessListener {
                    name.text.clear()
                    weight.text.clear()
                    address.text.clear()
                    lng.text.clear()
                    lat.text.clear()
                    Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}