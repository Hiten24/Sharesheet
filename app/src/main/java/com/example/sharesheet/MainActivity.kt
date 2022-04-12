package com.example.sharesheet

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sharesheet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apps = handleIntent(this)

        val appsAdapter = AppsAdapter()

        binding.recyclerview.apply {
            adapter = appsAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 4)
        }

        appsAdapter.differ.submitList(apps)

        /*binding.share.setOnClickListener {
            handleIntent(this)
        }

        binding.systemShareSheet.setOnClickListener {
            systemShareSheet()
        }*/
    }

    private fun systemShareSheet() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
//            type = "image"
        }

        val resolveInfos = packageManager.queryIntentActivities(sendIntent, 0)
        Log.d("shareAppInfo", resolveInfos.toString())

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun handleIntent(context: Context): MutableList<ResolveInfo> {
        val packageManager = packageManager
        val mainIntent = Intent(Intent.ACTION_SEND, null)
        mainIntent.type = "text/plain"
        val resolveInfos = packageManager.queryIntentActivities(mainIntent, 0)


        Log.d("shareAppInfo", resolveInfos.toString())
        return resolveInfos
    }
}