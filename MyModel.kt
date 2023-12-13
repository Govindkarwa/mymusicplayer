package com.example.mymusicplayer

import java.io.Serializable

data class MyModel(val img:Int,val songName:String, val path:String,val duration:String):Serializable {}