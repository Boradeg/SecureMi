package com.example.securemi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.securemi.R
import com.example.securemi.dataClasses.UserTrustyDetailDataClass
import com.example.securemi.databinding.ActivityAddTrustyNumberBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Document

var userTrustyNumber:String?=null
var numArray = ArrayList<String>()


class AddTrustyNumberActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var db: DatabaseReference
    lateinit  var dbRef:FirebaseFirestore


    val NAME="userName"
    val NUMBER="userNumber"
    val COLLECTION="notes"
    val DOCUMENT="innernotes"
    private lateinit var binding:ActivityAddTrustyNumberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddTrustyNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbRef= Firebase.firestore
        binding.saveBtn.setOnClickListener {

            for(i in numArray)
            {
                Toast.makeText(this,i, Toast.LENGTH_SHORT).show()

            }


            if(binding.trustyNumber.text.isNotEmpty()&&binding.trustyName.text.isNotEmpty())
            {
               // writeData()
                writeDataToFirestore()
                readData()
            }
            else{

                binding.trustyName.error="Enter Valid Name"
                binding.trustyNumber.error="Enter Valid Number"
            }

            //readData()
        }





    }

    private fun writeDataToFirestore() {
        var e=9
        var name=binding.trustyName.text.toString()
        var number=binding.trustyNumber.text.toString()
        val map= mutableMapOf<String,String>()
        map.put(NAME,name)
        map.put(NUMBER,number)
        dbRef.collection(numb).document().set(map).addOnSuccessListener {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
            binding.trustyNumber.text.clear()
            binding.trustyName.text.clear()
        }.addOnFailureListener {
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()

        }
    }

    private fun readData() {

        db=FirebaseDatabase.getInstance().getReference("USER")
        db.child(FirebaseAuth.getInstance().currentUser!!.uid).child("Trusty Detail").get().addOnSuccessListener{
            userTrustyNumber=it.child("userNumber").value.toString()
            // var userTrustyNum=it.child("userName").value.toString()
            Toast.makeText(this, "userTrustyNumber added successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun writeData() {

        database=Firebase.database.reference.child("USER")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("Trusty Detail")
        var userName=binding.trustyName.text.toString()
        var userNumber=binding.trustyNumber.text.toString()

        val userTrustyDetail= UserTrustyDetailDataClass(userName,userNumber)
        database.setValue(userTrustyDetail).addOnCompleteListener{
            Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show()



       binding.trustyNumber.text.clear()
       binding.trustyName.text.clear()
        }

//        Toast.makeText(this, numArray[0], Toast.LENGTH_SHORT).show()
//        Toast.makeText(this, numArray[1], Toast.LENGTH_SHORT).show()


    }
}