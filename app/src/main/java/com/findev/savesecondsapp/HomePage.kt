package com.findev.savesecondsapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.findev.savesecondsapp.databinding.ActivityHomePageBinding
import com.hover.sdk.actions.HoverAction
import com.hover.sdk.api.Hover


class HomePage : AppCompatActivity(),Hover.DownloadListener{
    private lateinit var binding: ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //startActivityForResult(Intent(this, PermissionActivity.class), 0);
        Log.d("easy app break", "here: here")

        Hover.initialize(applicationContext);
        Log.d("easy app break", "here: after")


    }

    override fun onError(message: String) {
//		Toast.makeText(this, "Error while attempting to download actions, see logcat for error", Toast.LENGTH_LONG).show();
        Log.d("easy app break", "Error: $message")
    }

    override fun onSuccess(actions: ArrayList<HoverAction?>) {
//		Toast.makeText(this, "Successfully downloaded " + actions.size() + " actions", Toast.LENGTH_LONG).show();
        Log.d("easy app break", "Successfully downloaded " + actions.size + " actions")
    }
}