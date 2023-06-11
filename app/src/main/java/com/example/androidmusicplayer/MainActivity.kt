package com.example.androidmusicplayer

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.androidmusicplayer.basicpage.Community
import com.example.androidmusicplayer.basicpage.Concern
import com.example.androidmusicplayer.basicpage.Mine
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import android.Manifest
import android.util.Log
import android.widget.Toast
import com.example.androidmusicplayer.db_controll.FavourDbHelper
import com.example.androidmusicplayer.db_controll.ListDbHelper
import com.example.androidmusicplayer.db_controll.LocalPlayDbHelper
import com.example.androidmusicplayer.db_controll.PlayDbHelper
import com.example.androidmusicplayer.db_controll.RecentDbHelper
import com.example.androidmusicplayer.media.TAG
import com.example.androidmusicplayer.media.getAllMp3Files

val fragments = arrayOf(
    "我的" to Mine(),
    "关注" to Concern(),
    "社区" to Community()
)

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var service: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPage()
    }

    override fun onDestroy() {
        stopService(service)
        super.onDestroy()
    }

    private fun initPage(){
        viewPager2 = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        val viewAdapter = MyViewPager2Adapter(this)
        viewPager2.adapter = viewAdapter
        TabLayoutMediator(tabLayout, viewPager2) {tab, position ->
            tab.text = fragments[position].first
        }.attach()
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val requestCode = 1
        ActivityCompat.requestPermissions(this, permissions, requestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                // 请求代码匹配，处理存储权限请求结果
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
                    Log.d(TAG,"??????")
                    getAllMp3Files(this)
                } else {
                    Toast.makeText(this, "未获得存储权限，无法扫描本机文件。", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private inner class MyViewPager2Adapter(fm : AppCompatActivity) : FragmentStateAdapter(fm) {
        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position].second
    }
}