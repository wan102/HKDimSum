package com.example.hkdimsum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hkdimsum.ui.dashboard.DimsumData
import com.squareup.picasso.Picasso

class YumchaAdapter(private val restaurantList: ArrayList<DimsumData>) :
    RecyclerView.Adapter<YumchaAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val dataView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycleview_rowitem,
            parent, false
        )
        return MyViewHolder(dataView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val getdatabaseInfo = restaurantList[position]

        holder.name.text = getdatabaseInfo.name
        holder.address.text = getdatabaseInfo.address
        val imageTarget = getdatabaseInfo.img
        Picasso.get().load(imageTarget).into(holder.img)
    }

    class MyViewHolder(dataView: View) : RecyclerView.ViewHolder(dataView) {

        val img: ImageView = dataView.findViewById(R.id.cr_image)
        val name: TextView = dataView.findViewById(R.id.cr_name)
        val address: TextView = dataView.findViewById(R.id.cr_address)

    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }
}