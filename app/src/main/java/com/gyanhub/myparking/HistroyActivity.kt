package com.gyanhub.myparking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyanhub.myparking.adapter.historyAdapter
import com.gyanhub.myparking.databinding.ActivityHistroyBinding
import com.gyanhub.myparking.repository.parkingRepository
import com.gyanhub.myparking.room.ParkingDataBase
import com.gyanhub.myparking.viewModel.Factory
import com.gyanhub.myparking.viewModel.parkingViewModel

class HistroyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistroyBinding
    lateinit var viewModel: parkingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistroyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "History"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(
            this,
            Factory(
                parkingRepository(
                    ParkingDataBase.getDatabase(applicationContext).DatabaseDao()
                )
            )
        )[parkingViewModel::class.java]
        binding.hisRcView.layoutManager = LinearLayoutManager(this)
        viewModel.getParkingHistory().observe(this){
            val ad = historyAdapter(this,it)
            binding.hisRcView.adapter = ad
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}