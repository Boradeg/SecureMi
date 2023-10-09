package com.GokulBorade.securemi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.GokulBorade.securemi.Fragment.HomeFragment
import com.GokulBorade.securemi.Fragment.UserFragment
import com.GokulBorade.securemi.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class splashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else{
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}