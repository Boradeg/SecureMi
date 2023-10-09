package com.GokulBorade.securemi.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import com.GokulBorade.securemi.databinding.ActivityLocationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class locationActivity : AppCompatActivity() {
    lateinit var db: DatabaseReference
    private lateinit var binding: ActivityLocationBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLocationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        db = FirebaseDatabase.getInstance().getReference("Users2")
//        db.child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
//            numb = it.child("numb").value.toString()
////            Toast.makeText(this, it.child("numb").value.toString(), Toast.LENGTH_SHORT)
////                .show()
//        }
//
//        db = FirebaseDatabase.getInstance().getReference("Users")
//        db.child(numb.toString()).child("NOTIFICATION").get().addOnSuccessListener {
//            var ltd = it.child("n2").child("Latitudes").value.toString()
//            var longi =it.child("n2").child("Longitude").value.toString()
//            binding.webview.loadUrl("https://maps.google.com/?q=$ltd,$longi")
//            binding.webview.settings.javaScriptEnabled = true
//            binding.webview.webViewClient = WebViewClient()
//        }

    }
}
