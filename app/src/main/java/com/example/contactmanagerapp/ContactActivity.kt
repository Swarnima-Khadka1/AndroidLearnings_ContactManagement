package com.example.contactmanagerapp

import android.app.Dialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.example.contactmanagerapp.databinding.ActivityContactBinding
import android.widget.Toast
import android.widget.Button
import com.google.firebase.database.FirebaseDatabase


class ContactActivity : AppCompatActivity() {
    lateinit var dialog: Dialog
    lateinit var binder: ActivityContactBinding
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binder = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binder.root)

        ViewCompat.setOnApplyWindowInsetsListener(binder.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binder.btnAdd.setOnClickListener {
            val name = binder.etName.text.toString()
            val mail = binder.etMail.text.toString()
            val phone = binder.etPhone.text.toString()

            if(name.isEmpty()){
                Toast.makeText(this,"Please enter a username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            if (mail.isEmpty()) {
                Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Stop the process
            }

            if (phone.isEmpty()) {
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Stop the process
            }

           val contact= ContactList(name, mail , phone)
            database= FirebaseDatabase.getInstance().getReference("Contacts")
            database.child(phone).setValue(contact).addOnSuccessListener {
                    dialog= Dialog(this)
                    dialog.setContentView(R.layout.customise_dialog)
                    dialog.setCancelable(false)
                    dialog.show()

                val btnOk= dialog.findViewById<Button>(R.id.btnAdd)
                btnOk.setOnClickListener {
                 Toast.makeText(this, "Contact Added Successfully", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }.addOnFailureListener {
                Toast.makeText(this,"Failed to add contact",Toast.LENGTH_SHORT).show()
            }
        }
    }
}