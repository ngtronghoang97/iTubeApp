package com.example.itubeapp.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itubeapp.MainActivity
import com.example.itubeapp.R
import com.example.itubeapp.adapter.AdapterPlayList

class MyPlaylistFragment : Fragment() {

    companion object {
        fun newInstance() = MyPlaylistFragment()
    }

    private var adapterPlayList : AdapterPlayList? = null
    private var itemsRecycler : RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_my_playlist, container, false)

        val backBtn = rootView.findViewById<ImageView>(R.id.backBtn)
        backBtn.setOnClickListener {
            (this.activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }

        val a = this.requireActivity() as MainActivity

        /**
         * Init adapter and recyclerView
         * Set data to UI
         */
        itemsRecycler = rootView.findViewById(R.id.rvPlayList)

        val layoutManager = LinearLayoutManager(this@MyPlaylistFragment.context)
        itemsRecycler!!.layoutManager = layoutManager

        adapterPlayList = AdapterPlayList(a.readPlayListData(), this@MyPlaylistFragment.requireContext())
        itemsRecycler!!.adapter = adapterPlayList

        // Check if data is empty
        val noDataTv = rootView.findViewById<TextView>(R.id.noData)
        if (a.readPlayListData().isEmpty()) {
            noDataTv.visibility = View.VISIBLE
            itemsRecycler!!.visibility = View.GONE
        } else {
            itemsRecycler!!.visibility = View.VISIBLE
            noDataTv.visibility = View.GONE
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
                        .replace(R.id.container, HomeFragment.newInstance()).commitNow()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }
}