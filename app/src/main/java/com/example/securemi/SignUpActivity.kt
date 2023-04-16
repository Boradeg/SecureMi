package com.example.securemi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.securemi.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    lateinit var binding:ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth=FirebaseAuth.getInstance()
        binding.textView.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }
        binding.button.setOnClickListener {
            val email=binding.emailEt.text.toString()
            val pass=binding.passET.text.toString()
            val confirmPass=binding.confirmPassEt.text.toString()
            val numb=binding.numberET.text.toString()

            if(email.isNotEmpty() &&pass.isNotEmpty() &&confirmPass.isNotEmpty())
            {
                if(pass==confirmPass)
                {  var userDataSignUp =userDataSignUp(
                    binding.emailEt.text.toString()
                    ,binding.numberET.text.toString()
                )
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                        if(it.isSuccessful)
                        {     Firebase.database.reference.child("USER")
                            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("user Detail")
                            .setValue(userDataSignUp)
                            .addOnSuccessListener {
                                Toast.makeText(this, "crete user for app", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            startActivity(Intent(this,MainActivity::class.java))

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