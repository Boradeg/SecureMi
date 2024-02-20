package com.GokulBorade.securemi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.GokulBorade.securemi.Fragment.UserFragment
import com.GokulBorade.securemi.databinding.ActivityCall1Binding


class callActivity1 : AppCompatActivity() {
    private lateinit var binding: ActivityCall1Binding
    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var isRunning: Boolean = false
    private val handler = Handler(Looper.getMainLooper())
    private val timerRunnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                elapsedTime = System.currentTimeMillis() - startTime
                updateTimerText(elapsedTime)
                handler.postDelayed(this, 1000)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCall1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val name:String = intent.getStringExtra("name").toString()
        val num=intent.getStringExtra("number")
        binding.callerName.text=name
        binding.callerNumber.text=num
        binding.callEndButton.setOnClickListener {
          startActivity(Intent(this, MainActivity::class.java))
          finish()
       }
          startTimer()
    }
    private fun startTimer() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime
            isRunning = true
            handler.postDelayed(timerRunnable, 0)
        }
    }

    private fun stopTimer() {
        if (isRunning) {
            isRunning = false
            handler.removeCallbacks(timerRunnable)
        }
    }

    private fun updateTimerText(time: Long) {
        val seconds = (time / 1000 % 60).toInt()
        val minutes = (time / (1000 * 60) % 60).toInt()
        val hours = (time / (1000 * 60 * 60)).toInt()

        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        binding.callTime.text = formattedTime
    }


}
