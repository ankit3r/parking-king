package com.gyanhub.myparking.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gyanhub.myparking.R
import com.gyanhub.myparking.VerifiedActivity
import com.gyanhub.myparking.interfaces.onClick
import com.gyanhub.myparking.room.ParkingEntity

class MainAdapter(val context: Context,val list : List<ParkingEntity>,val onclicks : onClick):RecyclerView.Adapter<MainAdapter.RcViewHolder>() {

    class RcViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val OwnerName: TextView = itemView.findViewById(R.id.txtOwnerName)
        val CarNo: TextView = itemView.findViewById(R.id.txtCarNo)
        val ParkingFee: TextView = itemView.findViewById(R.id.txtParkingFee)
        val ParkingTime: TextView = itemView.findViewById(R.id.txtEntryTime)
        val OwnerPhNo: TextView = itemView.findViewById(R.id.txtPhNo)
        val btn : Button = itemView.findViewById(R.id.btnGo)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RcViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rc_home_view_item, parent, false)
        return RcViewHolder(view)
    }

    override fun onBindViewHolder(holder: RcViewHolder, position: Int) {
        val item = list[position]
        holder.OwnerName.text = item.OwnerName
        holder.CarNo.text = item.CarNo
        holder.ParkingFee.text = "${item.ParkingFee}/h"
        holder.ParkingTime.text = item.EntryTime
        holder.OwnerPhNo.text = item.PhoneNo
        holder.btn.setOnClickListener{
           onclicks.onClicks(position,list)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}