package com.gyanhub.myparking.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("History")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val OwnerName: String,
    val CarNo: String,
    val ParkingFee: String,
    val TotalParkingFee: String,
    val PhoneNo: String,
    val EntryTime: String,
    val OutTime: String,
    val totalTime: String,
    val payOrNot: Boolean

)
