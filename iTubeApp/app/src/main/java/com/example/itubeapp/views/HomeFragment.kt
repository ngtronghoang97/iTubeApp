package com.example.itubeapp.views

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.itubeapp.MainActivity
import com.example.itubeapp.R
import com.example.itubeapp.prefmanager.SharedPrefManager

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    var pref: SharedPrefManager? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        val a = this.requireActivity() as MainActivity

        pref = SharedPrefManager(this.requireContext())

        val welcomeNote = rootView.findViewById<TextView>(R.id.welcomeNote)
        val youTubeUrl = rootView.findViewById<EditText>(R.id.youTubeUrl)

        welcomeNote.text = "Dear " + pref!!.getFULL_NAME() + ",welcome to iTube. Enjoy!"

        val playVideo = rootView.findViewById<AppCompatButton>(R.id.playVideo)
        val addPlayList = rootView.findViewById<AppCompatButton>(R.id.addPlayList)
        val viewPlayList = rootView.findViewById<AppCompatButton>(R.id.viewPlayList)

        playVideo.setOnClickListener {
            if (TextUtils.isEmpty(youTubeUrl.text.toString().trim())) {
                youTubeUrl.error = "Url Required!"
            } else {
                val url = youTubeUrl.text.toString().trim()
                a.intentToPlayer(url)
            }
        }

        addPlayList.setOnClickListener {
            if (TextUtils.isEmpty(youTubeUrl.text.toString().trim())) {
                youTubeUrl.error = "Url Required!"
            } else {
                val url = youTubeUrl.text.toString().trim()
                val userName = pref!!.getUSER_NAME()
                a.addToPlayList(userName, url)
                youTubeUrl.text.clear()
            }
        }

        viewPlayList.setOnClickListener {
            (this.activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.container, MyPlaylistFragment.newInstance())
                .commitNow()
        }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Let's handle onClick back btn
                    (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.container, LoginFragment.newInstance()).commitNow()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }
}