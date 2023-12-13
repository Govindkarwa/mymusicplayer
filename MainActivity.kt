package com.example.mymusicplayer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private var Permission_Allow_Code=123;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var start_bt:Button=findViewById(R.id.Start_bt)

        start_bt.setOnClickListener {
            isPermission(it)
        }
    }

    private fun launchActivity(){
        var i:Intent=Intent(applicationContext,SongList::class.java)
        startActivity(i)
    }

   fun isPermission(view: View){
       if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED||ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_MEDIA_AUDIO)==PackageManager.PERMISSION_GRANTED){
          launchActivity()
       }else{
          requestForPermission(view)
       }
   }

    private fun requestForPermission(view: View){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            val snack=Snackbar.make(view,"If You Want To Use This App You Have To Give Me Read Storage Permission ",Snackbar.LENGTH_INDEFINITE).setAction("Ok"){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_MEDIA_AUDIO),Permission_Allow_Code)
            }
            snack.show()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_MEDIA_AUDIO),Permission_Allow_Code)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==Permission_Allow_Code){
            if(grantResults.size==1&&grantResults[1]==PackageManager.PERMISSION_GRANTED){
                launchActivity()
            }else{
                Toast.makeText(this,"Permission Not Allowed",Toast.LENGTH_LONG).show()
            }
        }
    }
}