package com.example.contactmanagerapp


import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler
import android.os.Looper
import android.content.Intent


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val logoImage= findViewById<ImageView>(R.id.ivLogo)

        logoImage.alpha =0f
        logoImage.animate().alpha(1f).setDuration(1500).start()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }, 2000)



    }
}