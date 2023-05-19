package com.example.itubeapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itubeapp.MainActivity
import com.example.itubeapp.R
import com.example.itubeapp.model.PlayListModel

class AdapterPlayList (var dataModelItemsModel : List<PlayListModel>, var mContext: Context) :
    RecyclerView.Adapter<AdapterPlayList.RVHolder?>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View = layoutInflater.inflate(R.layout.item_rv, parent, false)
        return RVHolder(listItem)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RVHolder, position: Int) {
        holder.tvUrl.text = dataModelItemsModel[position].youTubeUrl

        val a = (mContext as MainActivity)
        holder.lnLayoutHolder.setOnClickListener {
            a.intentToPlayer(dataModelItemsModel[position].youTubeUrl)
        }
    }

    override fun getItemCount(): Int {
        return dataModelItemsModel.size
    }

    class RVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUrl: TextView = itemView.findViewById<View>(R.id.tvUrl) as TextView
        var lnLayoutHolder: LinearLayout = itemView.findViewById<View>(R.id.lnLayout) as LinearLayout
    }
}