package com.example.projectlogin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.projectlogin.R
import com.example.projectlogin.data.Users
import com.example.projectlogin.repository.AuthDatabase

class RegisterUser : AppCompatActivity() {
    private lateinit var bar: ProgressBar
    private lateinit var emailEdit: EditText
    private lateinit var passwordEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
        emailEdit = findViewById(R.id.emailRegister)
        passwordEdit = findViewById(R.id.passwordRegister)
        bar = findViewById(R.id.barRegister)
        findViewById<Button>(R.id.registerUser).setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email: String = emailEdit.text.toString().trim()
        val password: String = passwordEdit.text.toString().trim()
        if (email.isEmpty()) {
            emailEdit.error = "email is required"
            emailEdit.requestFocus()
            return
        }
        if (password.isEmpty()) {
            passwordEdit.error = "password is required"
            passwordEdit.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEdit.error = "please provide a valid email"
            emailEdit.requestFocus()
            return
        }
        if (password.length < 6) {
            passwordEdit.error = "please provide a valid password "
            passwordEdit.requestFocus()
            return
        }
        bar.visibility = View.VISIBLE
        AuthDatabase.createUser(email, password).addOnCompleteListener { it1 ->
            if (it1.isSuccessful) {
                val user = Users.getInstance(email, password)
                AuthDatabase.setValue(user)?.addOnCompleteListener { it2 ->
                    if (it2.isSuccessful) {
                        Toast.makeText(
                            this,
                            "User been register successfully",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Failed to register try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    bar.visibility = View.GONE
                }

            } else {
                Toast.makeText(
                    this,
                    "Failed to register try again",
                    Toast.LENGTH_LONG
                ).show()
                bar.visibility = View.GONE
            }
        }
    }
}


