package com.example.mymusicplayer

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AbsSeekBar
import android.widget.ImageButton
import android.widget.TextView
import me.tankery.lib.circularseekbar.CircularSeekBar
import java.io.IOException
import java.util.concurrent.TimeUnit

class PlayingSong : AppCompatActivity() {
    lateinit var songTitle:TextView
    lateinit var startTime:TextView
    lateinit var endTime:TextView
    lateinit var timeBar:CircularSeekBar
    lateinit var nextBt:ImageButton
    lateinit var previousBt:ImageButton
    lateinit var playBt:ImageButton
    lateinit var currentSong:MyModel
    lateinit var  mediaPlayer:MediaPlayer
    lateinit var song:ArrayList<MyModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playing_song)
        startTime=findViewById(R.id.Start_time)
        endTime=findViewById(R.id.End_time)
        songTitle=findViewById(R.id.SongName_tv)
        songTitle.isSelected=true
        timeBar=findViewById(R.id.seekbar)
        nextBt=findViewById(R.id.next_bt)
        previousBt=findViewById(R.id.previous_bt)
        playBt=findViewById(R.id.play_bt)
        song = intent.getSerializableExtra("Key") as ArrayList<MyModel>
        mediaPlayer = MyMediaPlayer.getInstance()
        setResourceWithMusic()
        val thread = object: Thread(){
            override fun run(){
                while (mediaPlayer!=null){
                    runOnUiThread(java.lang.Runnable {
                        startTime.setText(convertToMMSS(mediaPlayer.currentPosition.toString()))
                        timeBar.max= mediaPlayer.duration.toFloat()
                        timeBar.progress= mediaPlayer.currentPosition.toFloat()
                        if(!mediaPlayer.isPlaying){
                            playBt.setImageResource(R.drawable.play_ic)
                        }else{
                            playBt.setImageResource(R.drawable.pause_ic)
                        }
                        Log.e("Testing","Thread obj")
                    })
                    Thread.sleep(100)
                }
            }
        }
        thread.start()
        timeBar.setOnSeekBarChangeListener(object : CircularSeekBar.OnCircularSeekBarChangeListener {
            override fun onProgressChanged(circularSeekBar: CircularSeekBar?, progress: Float, fromUser: Boolean) {
                if(mediaPlayer!=null&&fromUser){
                    mediaPlayer.seekTo(progress.toInt())
                }
            }

            override fun onStopTrackingTouch(circularSeekBar: CircularSeekBar?) {
                // TODO: Handle stop tracking touch
            }

            override fun onStartTrackingTouch(circularSeekBar: CircularSeekBar?) {
                print("Working")
            }
        })

    }
    fun setResourceWithMusic(){
        currentSong=song.get(MyMediaPlayer.currentIndex)
        songTitle.setText(currentSong.songName)
        startTime.setText("00:00")
        endTime.setText(convertToMMSS(currentSong.duration))
        playMusic()
        playBt.setOnClickListener {PlayPauseMusic()}
        nextBt.setOnClickListener { nextMusic() }
        previousBt.setOnClickListener { previousMusic() }
    }
    fun playMusic(){
        try {
            mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            mediaPlayer.reset()
            mediaPlayer.setDataSource(currentSong.path)
            mediaPlayer.prepare()
            timeBar.max= mediaPlayer.duration.toFloat()
            mediaPlayer.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    fun PlayPauseMusic(){
        if(mediaPlayer.isPlaying){
            mediaPlayer.pause()
        }else{
            mediaPlayer.start()
        }
    }
    fun nextMusic(){
        if(MyMediaPlayer.currentIndex==song.size-1){
           return
        }
        else{
        MyMediaPlayer.currentIndex++
        setResourceWithMusic() }
    }
    fun previousMusic(){
        if(MyMediaPlayer.currentIndex==0){
            return
        }
       else{ MyMediaPlayer.currentIndex--
        setResourceWithMusic() }

    }
    fun convertToMMSS(duration: String): String {
        val millis = duration.toLong()
        return String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
        )
    }
}