package com.example.androidmusicplayer.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.fragment.AlbumFragment
import com.example.androidmusicplayer.fragment.FolderFragment
import com.example.androidmusicplayer.fragment.SingerFragment
import com.example.androidmusicplayer.fragment.SingleFragment
import com.example.androidmusicplayer.struct.LocalFileManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.io.File


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
        val manager = LocalFileManager()
        manager.searchLoadMusic(this)
        val intent = intent
        intent.putExtra("fileList",manager.fileList)
        intent.putExtra("localMusic",manager.list)
        intent.putExtra("singerList",manager.singerList)
    }

    private fun initPage() {
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        val adapter = LocalMusicActivityAdapter(this)
        val goBack : Button = findViewById(R.id.go_back)
        val option : Button = findViewById(R.id.option)
        val popMenu = PopupMenu(this,option)
        popMenu.inflate(R.menu.local_music_menu)
        popMenu.setOnMenuItemClickListener{ item ->
            when( item.itemId ){
                R.id.scan ->{
                    Toast.makeText(this, "此功能暂未实现，敬请期待。", Toast.LENGTH_SHORT).show()
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
        goBack.setOnClickListener {
            finish()
        }
        option.setOnClickListener {
            popMenu.show()
        }
    }



    private inner class LocalMusicActivityAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm){
        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position].second
    }
}