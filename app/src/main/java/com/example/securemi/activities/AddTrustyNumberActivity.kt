package com.example.securemi.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.securemi.R
import com.example.securemi.databinding.ActivityAddTrustyNumberBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


//var userTrustyNumber:String?=null
//var numArray = ArrayList<String>()
const val channelId ="channelId"

class AddTrustyNumberActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager

    private lateinit var database: DatabaseReference
    private lateinit var db: DatabaseReference
    lateinit  var dbRef:FirebaseFirestore
    val NAME="userName"
    val NUMBER="userNumber"
    val NOTIFICATION="notification"
    val COLLECTION="notes"
    val DOCUMENT="innernotes"

    private lateinit var binding:ActivityAddTrustyNumberBinding

    @SuppressLint("RemoteViewLayout")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddTrustyNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbRef= Firebase.firestore
        binding.signOutBtn.setOnClickListener {
            val contentView = RemoteViews(packageName, R.layout.activity_view_registered)
            val intent= Intent(this,MainActivity::class.java)
            var pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            createNoti()
            var builder= NotificationCompat.Builder(this, channelId)
                builder.setSmallIcon(R.drawable.helpline2)
                     .setContentTitle("Help Me")
                    .setWhen(System.currentTimeMillis())
                    .setContentText("You have one message")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
            with(NotificationManagerCompat.from(this)){
                 if (ActivityCompat.checkSelfPermission(this@AddTrustyNumberActivity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
                 ) {
                     return@setOnClickListener
                 }
                notify(1,builder.build())
            }
        }
        binding.saveBtn.setOnClickListener {
//            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
//            val r = Rington
//            eManager.getRingtone(applicationContext, notification)
//            r.play()

            if(binding.trustyNumber.text.isNotEmpty()&&binding.trustyName.text.isNotEmpty())
            {
               try {
                   writeDataToFirestore()
               }
               catch(e:Exception)
               {
                   Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
               }
            }
            else{
                binding.trustyName.error="Enter Valid Name"
                binding.trustyNumber.error="Enter Valid Number"
            }
        }
    }

    @SuppressLint("RemoteViewLayout")
    private fun createNoti() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                "first channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.description = "Test DEsc for my channel"
            notificationChannel.enableLights(true)
            notificationChannel.sound
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }



    private fun writeDataToFirestore() {
        var name=binding.trustyName.text.toString()
        var number=binding.trustyNumber.text.toString()
        val map= mutableMapOf<String,String>()
        map.put(NAME,name)
        map.put(NUMBER,number)
        dbRef.collection(FirebaseAuth.getInstance().currentUser!!.email!!).document()
            .set(map).addOnSuccessListener {
            binding.trustyNumber.text!!.clear()
            binding.trustyName.text.clear()
        }.addOnFailureListener {
        }
    }

}