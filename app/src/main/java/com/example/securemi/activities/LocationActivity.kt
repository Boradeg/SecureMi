package com.example.securemi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.securemi.databinding.ActivityLocationBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class locationActivity : AppCompatActivity() {
    lateinit var db:DatabaseReference
   var link:String?=null
    private lateinit var binding:ActivityLocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityLocationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
            db = FirebaseDatabase.getInstance().getReference("USER")
            db.child(numb).child("NOTIFICATION").get().addOnSuccessListener {
                var notif = it.child("n2").child("userNumber").value.toString()
                binding.webview.loadUrl("$notif")
                binding.webview.settings.javaScriptEnabled=true
                binding.webview.webViewClient= WebViewClient()
                }
            }
}