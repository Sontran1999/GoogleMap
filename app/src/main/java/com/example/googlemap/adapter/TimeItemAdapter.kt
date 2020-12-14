package com.example.googlemap.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.googlemap.R
import com.example.googlemap.model.direction.Routes

class TimeItemAdapter(private val onClick: (String, String, Int) -> Unit) :
    RecyclerView.Adapter<TimeItemAdapter.ViewHoder>() {
    var mRoutes: MutableList<Routes> = arrayListOf()

    class ViewHoder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var time: Button = itemView.findViewById(R.id.btn_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.time_item, parent, false)
        var viewHoder: ViewHoder = ViewHoder(view)
        return viewHoder
    }

    override fun onBindViewHolder(holder: ViewHoder, position: Int) {
        holder.time.text =
            mRoutes[position].legs?.get(0)?.distance?.text + " / " + mRoutes[position].legs?.get(0)?.duration?.text
        var origin: String? = mRoutes[position].legs?.get(0)?.startAddress
        var destination: String? = mRoutes[position].legs?.get(0)?.endAddress
        var time = mRoutes[position].legs?.get(0)?.duration?.value
        holder.time.setOnClickListener {
            if (origin != null) {
                if (destination != null) {
                    if (time != null) {
                        onClick(origin, destination, time)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mRoutes.size
    }

    fun setList(list: MutableList<Routes>) {
        this.mRoutes = list
        notifyDataSetChanged()
    }
}