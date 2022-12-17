package com.gyanhub.myparking.interfaces

import com.gyanhub.myparking.room.ParkingEntity

interface onClick {
    fun onClicks(position:Int,list: List<ParkingEntity>)
}