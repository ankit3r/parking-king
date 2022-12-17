package com.gyanhub.myparking.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gyanhub.myparking.repository.parkingRepository

class Factory(private val repository: parkingRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return parkingViewModel(repository) as T
    }
}