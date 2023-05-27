package com.example.androidmusicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

val fragments = arrayOf(
    "我的" to Mine(),
    "关注" to Concern(),
    "社区" to Community()
)

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPage()
    }

    private fun initPage(){
        viewPager2 = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        val adapter = MyViewPager2Adapter(this)
        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager2) {tab, position ->
            tab.text = fragments[position].first
        }.attach()
    }

    private inner class MyViewPager2Adapter(fm : AppCompatActivity) : FragmentStateAdapter(fm) {
        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position].second
    }
}