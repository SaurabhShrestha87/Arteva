package com.arteva.user.video.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.arteva.user.Constant.MEDIA_URL
import com.arteva.user.databinding.ActivityVideoPlayerBinding
import com.arteva.user.model.Video
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.util.Util

class VideoPlayerActivity : Activity() {

    private val TAG: String = "VideoPlayerActivity"
    private lateinit var simpleExoPlayer: ExoPlayer
    private lateinit var binding: ActivityVideoPlayerBinding
    private lateinit var video: Video

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        video = intent.getParcelableExtra("video")!!
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.videoTitle.text = video.name
    }

    private fun initializePlayer() {
        val mediaDataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(this)
        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(MEDIA_URL + video.video_url))
        Log.e(TAG, "initializePlayer: ${MEDIA_URL + video.video_url.replace(" ", "%20")}")
        val mediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)

        simpleExoPlayer = ExoPlayer.Builder(this)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()

        simpleExoPlayer.addMediaSource(mediaSource)

        simpleExoPlayer.playWhenReady = true
        binding.playerView.player = simpleExoPlayer
        binding.playerView.requestFocus()
    }

    private fun releasePlayer() {
        simpleExoPlayer.release()
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) initializePlayer()
    }

    public override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23) initializePlayer()
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) releasePlayer()
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) releasePlayer()
    }

}