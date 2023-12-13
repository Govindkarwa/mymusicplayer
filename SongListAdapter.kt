package com.example.mymusicplayer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongListAdapter(private val mList: ArrayList<MyModel>, private val context:Context): RecyclerView.Adapter<SongListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {
        val view:View=LayoutInflater.from(context).inflate(R.layout.song_list_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemViewModel =mList[position]
        holder.img.setImageResource(itemViewModel.img)
        holder.songName.setText(itemViewModel.songName)
        holder.songName.setOnClickListener {
            MyMediaPlayer.getInstance().reset()
            MyMediaPlayer.currentIndex=position
            var i:Intent=Intent(context,PlayingSong::class.java)
            i.putExtra("Key",mList)
            i.putExtra("Index",position)
            context.startActivity(i)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var img:ImageView=itemView.findViewById(R.id.music_ic)
        var songName:TextView=itemView.findViewById(R.id.songName)
    }
}
