package com.example.contactmanagerapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.example.contactmanagerapp.databinding.ActivitySignInBinding
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase



class SignIn : AppCompatActivity() {
    lateinit var binder: ActivitySignInBinding
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binder = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binder.root)

        ViewCompat.setOnApplyWindowInsetsListener(binder.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binder.btnSignIn.setOnClickListener {
            val userName = binder.etName.text.toString()
            val password = binder.etPassword.text.toString()
            if (userName.isNotEmpty() && password.isNotEmpty()) {
                readData(userName, password)
            } else {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun readData(userName: String, password: String) {
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(userName).get().addOnSuccessListener {
            if (it.exists()) {
                val correctPass = it.child("password").value.toString()

                if (correctPass == password) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, ContactActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener {
            Toast.makeText(this, "User doesn't exists. Please signUp first.", Toast.LENGTH_SHORT).show()
        }
    }
}


