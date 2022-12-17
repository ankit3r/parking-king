package com.gyanhub.myparking.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DbDao {

    @Insert
    fun addParking(parkingEntity: ParkingEntity)

    @Query("SELECT * FROM Parking")
    fun getParkingData(): LiveData<List<ParkingEntity>>


    @Query("DELETE FROM Parking WHERE CarNo = :Cno")
    fun deleteByCarNo(Cno: String)


    @Insert
    fun addParkingHistory(historyEntity: HistoryEntity)


    @Query("SELECT * FROM History")
    fun getParkingHistory(): LiveData<List<HistoryEntity>>
}