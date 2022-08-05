package com.example.projectlogin.repository


import com.example.projectlogin.data.Users
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object AuthDatabase {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signIn(email: String, password: String): Task<AuthResult> {
        return mAuth.signInWithEmailAndPassword(email, password)
    }

    fun createUser(email: String, password: String): Task<AuthResult> {
        return mAuth.createUserWithEmailAndPassword(email, password)
    }

    fun setValue(user: Users): Task<Void>? {
        return FirebaseAuth.getInstance().currentUser?.let { it ->
            FirebaseDatabase.getInstance().getReference("Users")
                .child(it.uid).setValue(user)
        }
    }
}