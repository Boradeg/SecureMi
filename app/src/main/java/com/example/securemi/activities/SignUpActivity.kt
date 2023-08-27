package com.example.securemi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import com.example.securemi.dataClasses.UserDataSignUp
import com.example.securemi.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
//var emailMsg:String ?= null



class SignUpActivity : AppCompatActivity() {
    var userEmail:String?=null
    lateinit var binding:ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        supportActionBar?.hide()
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth=FirebaseAuth.getInstance()
        binding.textView.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        binding.button.setOnClickListener {
             val email=binding.emailEt.text.toString()
             userEmail=binding.emailEt.text.toString()
            val pass=binding.passET.text.toString()
            val confirmPass=binding.confirmPassEt.text.toString()
            numb=binding.numberET.text.toString()
            if(email.isNotEmpty() &&pass.isNotEmpty() &&confirmPass.isNotEmpty())
            {
                if(pass==confirmPass)
                {  var userDataSignUp = UserDataSignUp(
                    binding.emailEt.text.toString()
                    ,binding.numberET.text.toString()
                )
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                        if(it.isSuccessful)
                        {     Firebase.database.reference.child("USER")
                            .child(numb)
                           // .child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .setValue(userDataSignUp)
                            .addOnSuccessListener {
                                Toast.makeText(this, "crete user for app", Toast.LENGTH_SHORT)
                                    .show()
                              //  emailMsg=binding.emailEt.text.toString()

                            }
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()

                        }
                        else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                }
                else{
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Empty field  are Not Allowed", Toast.LENGTH_SHORT).show()

            }
        }
    }

}