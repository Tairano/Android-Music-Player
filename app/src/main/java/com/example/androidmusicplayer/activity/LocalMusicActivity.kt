package com.example.androidmusicplayer.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.fragment.AlbumFragment
import com.example.androidmusicplayer.fragment.FolderFragment
import com.example.androidmusicplayer.fragment.SingerFragment
import com.example.androidmusicplayer.fragment.SingleFragment
import com.example.androidmusicplayer.media.TAG
import com.example.androidmusicplayer.media.getAllMp3Files
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


val fragments = arrayOf(
    "单曲" to SingleFragment(),
    "歌手" to SingerFragment(),
    "专辑" to AlbumFragment(),
    "文件夹" to FolderFragment()
)

class LocalMusicActivity : AppCompatActivity() {
    private lateinit var viewPager : ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_music)
        initPage()
    }

    private fun initPage() {
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        val adapter = LocalMusicActivityAdapter(this)
        val goBack : Button = findViewById(R.id.go_back)
        val option : Button = findViewById(R.id.option)
        val search : Button = findViewById(R.id.search)
        val popMenu = PopupMenu(this,option)
        popMenu.inflate(R.menu.local_music_menu)
        popMenu.setOnMenuItemClickListener{ item ->
            when( item.itemId ){
                R.id.scan ->{
                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    val requestCode = 1
                    ActivityCompat.requestPermissions(this, permissions, requestCode)
                    true
                }
                else -> false
            }
        }
        registerForContextMenu(option)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = fragments[position].first
        }.attach()
        goBack.setOnClickListener { finish() }
        option.setOnClickListener { popMenu.show() }
        search.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
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
                    Toast.makeText(this, "扫描中，请不要点击屏幕导致无响应！", Toast.LENGTH_SHORT).show()
                    getAllMp3Files(this)
                    Toast.makeText(this, "扫描完成。请重新打开本页面。", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "未获得存储权限，无法扫描本机文件。", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private inner class LocalMusicActivityAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm){
        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position].second
    }
}