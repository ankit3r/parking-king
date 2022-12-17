package com.gyanhub.myparking.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gyanhub.myparking.repository.parkingRepository
import com.gyanhub.myparking.room.HistoryEntity
import com.gyanhub.myparking.room.ParkingEntity

class parkingViewModel(private val repository: parkingRepository) : ViewModel() {
    fun addParking(parkingEntity: ParkingEntity) {
        repository.addParkingData(parkingEntity)
    }
    fun getParking(): LiveData<List<ParkingEntity>> {
        return repository.getParking()
    }
    fun getParkingHistory(): LiveData<List<HistoryEntity>> {
        return repository.getParkingHistory()
    }
    fun remove(Vno : String){
        repository.remove(Vno)
    }
    fun addHistory(historyEntity: HistoryEntity){
        repository.addParkingHistory(historyEntity)
    }
}