package com.example.projectlogin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import com.example.projectlogin.R
import com.example.projectlogin.data.Users
import com.example.projectlogin.repository.AuthDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var bar: ProgressBar
    private lateinit var emailEdit: EditText
    private lateinit var passwordEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emailEdit = findViewById(R.id.email)
        passwordEdit = findViewById(R.id.password)
        bar = findViewById(R.id.barSignIn)

        findViewById<TextView>(R.id.register).setOnClickListener {

            startActivity(Intent(this, RegisterUser::class.java))
        }
        findViewById<Button>(R.id.signIn).setOnClickListener {
            userLogin()
        }
    }

    private fun userLogin() {
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
        AuthDatabase.signIn(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                bar.visibility = View.GONE
                val myIntent = Intent(this, ProfileActivity::class.java)
                Users.getInstance(email, password)
                myIntent.putExtra("email", email)
                startActivity(myIntent)
            } else {
                bar.visibility = View.GONE
                Toast.makeText(
                    this,
                    "Failed to login! please check your credential",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

