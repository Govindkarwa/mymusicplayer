package com.example.mymusicplayer

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class SongList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        var list:RecyclerView=findViewById(R.id.list_song)
        val empty_tv:TextView=findViewById(R.id.Empty_tv)
        val dataList= ArrayList<MyModel>()
        val projection= arrayOf(MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.DATA,MediaStore.Audio.Media.DURATION)
        val selection:String=MediaStore.Audio.Media.IS_MUSIC+"!=0"
        var cursor: Cursor? =contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null)
        if (cursor != null) {
            while (cursor.moveToNext()){
                var songData:MyModel=MyModel(R.drawable.ic_music,cursor.getString(0),cursor.getString(1),cursor.getString(2))
                if(File(songData.path).exists()){
                    dataList.add(songData)
                }
            }
        }
        if(dataList.size==0){
            empty_tv.isVisible=true
        }else{
        val myAdapter=SongListAdapter(dataList,this)
        var layoutManager=LinearLayoutManager(applicationContext)
        list.layoutManager=layoutManager
        list.adapter=myAdapter }

    }
}