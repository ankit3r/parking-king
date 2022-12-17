package com.gyanhub.myparking.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Parking")
data class ParkingEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val OwnerName : String,
    val CarNo : String,
    val ParkingFee : String,
    val PhoneNo : String,
    val EntryTime : String,
    val OutTime : String,
    val checkOut : Int
)