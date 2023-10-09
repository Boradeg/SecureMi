package com.GokulBorade.securemi.activities

import android.content.Intent
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.GokulBorade.securemi.Fragment.UserFragment
import com.GokulBorade.securemi.databinding.ActivityCall2Binding

class CallActivity2 : AppCompatActivity() {
    private lateinit var binding:ActivityCall2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCall2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val r = RingtoneManager.getRingtone(applicationContext, notification)
       // val ss:String = intent.getStringExtra("name").toString()
//        if(ss!="" && ss!=null){
//            binding.callerName.text=ss
//        }

        r.play()
        binding.callAcceptButton.setOnClickListener {
                r.stop()
                startActivity(Intent(this, callActivity1::class.java))

        }
        binding.callRejectButton.setOnClickListener {
            r.stop()
            startActivity(Intent(this, UserFragment::class.java))
        }
    }
}