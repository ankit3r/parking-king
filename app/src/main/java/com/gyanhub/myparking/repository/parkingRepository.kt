package com.gyanhub.myparking.repository

import androidx.lifecycle.LiveData
import com.gyanhub.myparking.room.DbDao
import com.gyanhub.myparking.room.HistoryEntity
import com.gyanhub.myparking.room.ParkingEntity

class parkingRepository(private val dao: DbDao) {

    fun addParkingData(parkingEntity: ParkingEntity) {
        dao.addParking(parkingEntity)
    }

    fun getParking(): LiveData<List<ParkingEntity>> {
        return dao.getParkingData()
    }

    fun getParkingHistory(): LiveData<List<HistoryEntity>> {
        return dao.getParkingHistory()
    }

    fun remove(Cno: String) {
        dao.deleteByCarNo(Cno)
    }

    fun addParkingHistory(historyEntity: HistoryEntity) {
        dao.addParkingHistory(historyEntity)
    }
}