package com.example.hkdimsum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.get
import java.util.ArrayList

class YumchaAdpater(private val courseList: ArrayList<DimsumData>) :
    RecyclerView.Adapter<YumchaAdpater.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val dataView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycleview_rowitem,
            parent, false
        )
        return MyViewHolder(dataView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val getdatabaseInfo = courseList[position]

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

        return courseList.size
    }

}