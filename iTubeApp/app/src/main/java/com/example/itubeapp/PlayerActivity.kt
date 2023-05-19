package com.example.itubeapp

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.example.itubeapp.utils.YouTubeConfig

class PlayerActivity : YouTubeBaseActivity() {

    var onInitializedListener: YouTubePlayer.OnInitializedListener? = null
    var youTubePlayerView: YouTubePlayerView? = null
    private var youTubeUrl: String? = null
    private var newYouTubeUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        youTubePlayerView = findViewById(R.id.youTubePlayerView)
        val backBtn = findViewById<ImageView>(R.id.playerBackBtn)

        backBtn.setOnClickListener {
            finish()
        }

        val intent = intent
        youTubeUrl = intent.getStringExtra("youTubeUrl")!!
        Log.d("strUrl", youTubeUrl.toString())

        if (youTubeUrl!!.length > 12) {
            val c = youTubeUrl!!.split(".be/".toRegex()).toTypedArray()
            val parts = c[1]
            newYouTubeUrl = parts

            Log.d("strNewURl", newYouTubeUrl!!)
        } else {
            newYouTubeUrl = youTubeUrl
        }

        onInitializedListener = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider,
                youTubePlayer: YouTubePlayer, b: Boolean
            ) {
                Log.d("onClickBtn", "Done initializing!")
                /**
                 * You can load a single video as follows
                 */
                youTubePlayer.loadVideo(newYouTubeUrl)
                /**
                 * You can add videos in an arraylist as follows
                 */
//                List<String> videoList = new ArrayList<>();
//                videoList.add("Z4HGQL_McDQ");
//                videoList.add("");
//                videoList.add("");
//                youTubePlayer.loadVideos(videoList);
                /**
                 * To load a playList just do
                 */
//                youTubePlayer.loadPlaylist("PLR8DItC4f5xv8jVcA0Hz-DofsFI_hcZBb");
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider,
                youTubeInitializationResult: YouTubeInitializationResult
            ) {
                Log.e("onClickBtn", "Failed initializing, $youTubeInitializationResult")
            }
        }

        // init YouTube Player
        youTubePlayerView!!.initialize(YouTubeConfig.API_KEY, onInitializedListener)
    }

    override fun onBackPressed() {
        finish()
    }
}
