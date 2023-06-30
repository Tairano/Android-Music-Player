package com.example.androidmusicplayer.basicpage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.adapter.PlayAdapter
import com.example.androidmusicplayer.adapter.PlayListAdapter
import com.example.androidmusicplayer.db_controll.FavourDbHelper
import com.example.androidmusicplayer.db_controll.ListDbHelper
import com.example.androidmusicplayer.media.byteArrayToBitmap
import com.example.androidmusicplayer.struct.PlayList
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class Mine : Fragment() {
    private lateinit var helper : ListDbHelper
    private lateinit var playList : ArrayList<PlayList>
    private lateinit var recyclerView : RecyclerView
    private lateinit var listAdapter : PlayListAdapter
    private lateinit var layout : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_my_main_page, container, false)
        initPlayList(layout)
        initButton(layout)
        return layout
    }

    @SuppressLint("SetTextI18n")
    private fun initPlayList(layout: View){
        val favourBitmap : ImageView = layout.findViewById(R.id.favour_image)
        val info : TextView = layout.findViewById(R.id.favour_info)
        val favourHelper = FavourDbHelper(layout.context)
        info.text = favourHelper.query(null).size.toString() + "首"
        val play = favourHelper.getLast()
        if(play == null){
            favourBitmap.setImageResource(R.drawable.favour1)
        }
        else{
            if(play.bitmap != null)
                favourBitmap.setImageBitmap(byteArrayToBitmap(play.bitmap))
            else
                favourBitmap.setImageResource(R.drawable.favour1)
        }
        helper = ListDbHelper(layout.context)
        playList = helper.query(null)
        recyclerView = layout.findViewById(R.id.my_play_list)
        listAdapter = PlayListAdapter(playList,this)
        recyclerView.adapter = listAdapter
    }

    private fun initButton(layout: View){
        val localMusic : LinearLayout = layout.findViewById(R.id.localMusic)
        val recentPlayed : LinearLayout = layout.findViewById(R.id.recentPlayed)
        val myCollection : LinearLayout = layout.findViewById(R.id.myCollection)
        val myFriends : LinearLayout = layout.findViewById(R.id.myFriends)
        val collectionAndSupport : LinearLayout = layout.findViewById(R.id.collectionAndSupport)
        val podcasts : LinearLayout = layout.findViewById(R.id.podcasts)
        val myFavour : LinearLayout = layout.findViewById(R.id.favour)
        val create : Button = layout.findViewById(R.id.create_list)
        localMusic.setOnClickListener {
            val intent = Intent("LocalMusicActivity")
            startActivity(intent)
        }
        recentPlayed.setOnClickListener {
            val intent = Intent("RecentPlayedActivity")
            startActivity(intent)
        }
        myFavour.setOnClickListener {
            val intent = Intent("MyFavourActivity")
            startActivity(intent)
        }
        myCollection.setOnClickListener {
            val intent = Intent("MyCollectionActivity")
            startActivity(intent)
        }
        create.setOnClickListener {
            createBottomDialog()
        }
        myFriends.setOnClickListener { notRealize() }
        collectionAndSupport.setOnClickListener { notRealize() }
        podcasts.setOnClickListener { notRealize() }
    }

    private fun createBottomDialog(){
        val bottomSheetDialog = context?.let { BottomSheetDialog(it) }
        val view: View = LayoutInflater.from(context).inflate(com.example.androidmusicplayer.R.layout.create_list, null)
        bottomSheetDialog?.setContentView(view)
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(view.parent as View)
        val confirm : Button = view.findViewById(R.id.confirm)
        val cancel : Button = view.findViewById(R.id.cancel)
        val name : EditText = view.findViewById(R.id.name)
        val comment : EditText = view.findViewById(R.id.comment)
        val user : EditText = view.findViewById(R.id.user)

        confirm.setOnClickListener {
            val names = name.text.toString()
            val comments = comment.text.toString()
            val users = user.text.toString()
            if(names == "" || comments == "" || users == ""){
                Toast.makeText(activity, "请填写完整信息！。", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                val list = PlayList(names,users,comments,null,0,-1)
                if(helper?.insert(list) != -1){
                    playList = helper.query(null)
                    recyclerView = layout.findViewById(R.id.my_play_list)
                    listAdapter = PlayListAdapter(playList,this)
                    Toast.makeText(activity, "创建成功！", Toast.LENGTH_SHORT).show()
                    bottomSheetDialog?.dismiss()
                }
                else {
                    Toast.makeText(activity, "创建失败！已有歌单！", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
        }
        cancel.setOnClickListener {
            bottomSheetDialog?.dismiss()
            Toast.makeText(activity, "取消创建。", Toast.LENGTH_SHORT).show()
        }

        behavior.peekHeight = 2000
        bottomSheetDialog?.show()
    }

    private fun notRealize(){
        Toast.makeText(activity, "此功能暂未实现，敬请期待。", Toast.LENGTH_SHORT).show()
    }
}