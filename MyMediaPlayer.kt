package com.example.mymusicplayer

import android.media.MediaPlayer
import android.provider.CalendarContract.Instances
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance

class MyMediaPlayer {
    companion object {
        private var instance: MediaPlayer? = null
        var currentIndex:Int = -1
        fun getInstance(): MediaPlayer {
            if (instance == null) {
                instance = MediaPlayer()
            }
            return instance!!
        }
    }
}