package com.example.securemi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import com.example.securemi.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
var numb:String=""
class SignInActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.hide()
        supportActionBar?.hide()
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        firebaseAuth= FirebaseAuth.getInstance()
        binding.textView.setOnClickListener {
             startActivity(Intent(this, SignUpActivity::class.java))
            finish()

        }
        binding.button.setOnClickListener {
            val email=binding.emailEt.text.toString()
            val pass=binding.passET.text.toString()
            if(email.isNotEmpty() &&pass.isNotEmpty())
            {

                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if(it.isSuccessful)
                    {

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()

                    }
                    else{

                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }


            }
            else{
                Toast.makeText(this, "Empty field  are Not Allowed", Toast.LENGTH_SHORT).show()

            }
        }
    }


}