package com.example.contactmanagerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.example.contactmanagerapp.databinding.ActivitySignUpBinding
import com.google.firebase.database.FirebaseDatabase


class SignUp : AppCompatActivity() {
    lateinit var binder: ActivitySignUpBinding
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
         binder = ActivitySignUpBinding.inflate(layoutInflater)
         setContentView(binder.root)

        ViewCompat.setOnApplyWindowInsetsListener(binder.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
       binder.btnSignUp.setOnClickListener {
           val userName = binder.etName.text.toString()
           val email = binder.etMail.text.toString()
           val password = binder.etPassword.text.toString()
           val rePassword = binder.etRePassword.text.toString()

        if(userName.isEmpty()){
            Toast.makeText(this,"Please enter a username", Toast.LENGTH_SHORT).show()
            return@setOnClickListener

        }
           if (email.isEmpty()) {
               Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show()
               return@setOnClickListener // Stop the process
           }

           if (password.isEmpty()) {
               Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
               return@setOnClickListener // Stop the process
           }

           if (rePassword.isEmpty()) {
               Toast.makeText(this, "Please re-type your password", Toast.LENGTH_SHORT).show()
               return@setOnClickListener // Stop the process
           }

           if (password != rePassword) {
               Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
               return@setOnClickListener // Stop the process
           }
           if(!binder.checkBox.isChecked){
               binder.checkBox.buttonTintList = getColorStateList(R.color.bluish)
               Toast.makeText(this,"Please accept the terms and conditions",Toast.LENGTH_SHORT).show()
               return@setOnClickListener
           }
           Toast.makeText(this,"Validation successful! Registering user...",Toast.LENGTH_SHORT).show()

           val user= Users(userName, email , password, rePassword)
           database= FirebaseDatabase.getInstance().getReference("Users")
           database.child(userName).setValue(user).addOnSuccessListener {
               Toast.makeText(this,"User Registered Successfully",Toast.LENGTH_SHORT).show()

               val intentSignIn = Intent(this, SignIn::class.java)
               startActivity(intentSignIn)
               finish()


           }.addOnFailureListener {
               Toast.makeText(this,"Failed to register user",Toast.LENGTH_SHORT).show()
           }


       }
     binder.tvSignIn.setOnClickListener {
         intent= Intent(this,SignIn::class.java)
         startActivity(intent)
     }
    }
}