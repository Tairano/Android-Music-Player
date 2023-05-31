package com.example.androidmusicplayer.activity

import android.icu.lang.UCharacter.BidiPairedBracketType.OPEN
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.fragment.AlbumFragment
import com.example.androidmusicplayer.fragment.FolderFragment
import com.example.androidmusicplayer.fragment.SingerFragment
import com.example.androidmusicplayer.fragment.SingleFragment
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
        var adapter = LocalMusicActivityAdapter(this)
        val goBack : Button = findViewById(R.id.go_back)
        val option : Button = findViewById(R.id.option)
        registerForContextMenu(option)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = fragments[position].first
        }.attach()
        goBack.setOnClickListener {
            finish()
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val inflator = MenuInflater(this)
        inflator.inflate(R.menu.local_music_menu, menu)

        menu!!.add(1, OPEN, 1, "打开")

        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.scan -> Toast.makeText(this, "此功能暂未实现，敬请期待。", Toast.LENGTH_SHORT).show()
        }
        return super.onContextItemSelected(item)
    }

    private inner class LocalMusicActivityAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm){
        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position].second
    }
}