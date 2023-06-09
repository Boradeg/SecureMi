package com.example.securemi.activities

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.example.securemi.R
import com.example.securemi.dataClasses.UserTrustyDetailDataClass
import com.example.securemi.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

var longitudes=""
var latitudes=""
var id=""
var userUid:String=""
var message:String = ""

var questionList = ArrayList<UserTrustyDetailDataClass>()
class MainActivity : AppCompatActivity() {
    private lateinit var db: DatabaseReference

    private lateinit var binding:ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get num user
        //get user email
        getUSerUid()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        permissionAccess()
        getCurrentLocations()


        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController=navHostFragment!!.findNavController()
        val popupMenu= PopupMenu(this,null)
        popupMenu.inflate(R.menu.bottomnavbarmenu)
        binding.bottomBar.setupWithNavController(popupMenu.menu,navController)

    }

    private fun readNotification() {
        db=FirebaseDatabase.getInstance().getReference("USER")
        db.child(numb).get().addOnSuccessListener {
            var notif = it.child("N2").value.toString()

            Toast.makeText(this, notif.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUSerUid() {
        db=FirebaseDatabase.getInstance().getReference("USER")
        db.child(numb).get().addOnSuccessListener {
            userUid = it.child("numb").value.toString()
            Toast.makeText(this, userUid.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.statusmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.notif -> {
                Toast.makeText(this, "notif/....", Toast.LENGTH_SHORT).show()
            }
            R.id.hom -> {
                Toast.makeText(this, "ho/....", Toast.LENGTH_SHORT).show()


            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getCurrentLocations()
    {
        if(checkPermissions())
        {
            if(locationEnableds())
            {
                //final latitude and longitude
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task->
                    val location: Location?=task.result
                    if(location==null)
                    {
                        Toast.makeText(this, "null location", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this, "success location", Toast.LENGTH_SHORT).show()
                        longitudes= location.longitude.toString()
                       latitudes= location.latitude.toString()
                        message= "Plz Help Me.location: https://maps.google.com/?q=$latitudes,$longitudes"

                    }

                }
            }
            else
            {
                //setting open here
                Toast.makeText(this, "turn on location", Toast.LENGTH_SHORT).show()
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        }
        else
        {
            //req permission here
            reqPermission()
        }


    }

    private fun reqPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION
            ,android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_ACCESS_LOCATION)
    }

    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION=100
    }
    private fun checkPermissions(): Boolean {
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== PERMISSION_REQUEST_ACCESS_LOCATION)
        {
            if(grantResults.isNotEmpty()&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show()
                getCurrentLocations()
            }
            else
            {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun locationEnableds(): Boolean {
        val locationManager: LocationManager =getSystemService(Context.LOCATION_SERVICE)as LocationManager
        // val locationManager:LocationManager=getSystemService(LOCATION_SERVICE)as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }
    private fun permissionAccess() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
            {

                Toast.makeText(this, "permission granted for msg send", Toast.LENGTH_SHORT).show()
            }
            else
            {

                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS),101)
            }
        }
    }
}