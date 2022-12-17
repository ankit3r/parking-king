package com.gyanhub.myparking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gyanhub.myparking.R
import com.gyanhub.myparking.interfaces.onClick
import com.gyanhub.myparking.room.HistoryEntity


class historyAdapter(val context: Context, val list : List<HistoryEntity>):RecyclerView.Adapter<historyAdapter.HisRcViewHolder>()  {

    class HisRcViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val OwnerName: TextView = itemView.findViewById(R.id.txtName)
        val CarNo: TextView = itemView.findViewById(R.id.txtVno)
        val ParkingFee: TextView = itemView.findViewById(R.id.txtParkingfee)
        val TotalFee: TextView = itemView.findViewById(R.id.txtTotalFee)
        val ParkingTime: TextView = itemView.findViewById(R.id.txtEntrytime)
        val OutTime: TextView = itemView.findViewById(R.id.txtOutTime)
        val OwnerPhNo: TextView = itemView.findViewById(R.id.txtWhno)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HisRcViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.h_rc_item_view, parent, false)
        return HisRcViewHolder(view)
    }

    override fun onBindViewHolder(holder: HisRcViewHolder, position: Int) {
        val item = list[position]
        holder.OwnerName.text = item.OwnerName
        holder.CarNo.text = item.CarNo
        holder.ParkingFee.text = "${item.ParkingFee}/h"
        holder.ParkingTime.text = item.EntryTime
        holder.OwnerPhNo.text = item.PhoneNo
        holder.TotalFee.text = "${item.TotalParkingFee} payed"
        holder.OutTime.text = item.OutTime
    }

    override fun getItemCount(): Int {
        return list.size
    }
}