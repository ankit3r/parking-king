package com.gyanhub.myparking

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.gyanhub.myparking.databinding.ActivityAddParkingBinding
import com.gyanhub.myparking.repository.parkingRepository
import com.gyanhub.myparking.room.ParkingDataBase
import com.gyanhub.myparking.room.ParkingEntity
import com.gyanhub.myparking.viewModel.Factory
import com.gyanhub.myparking.viewModel.parkingViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddParkingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddParkingBinding
    lateinit var viewModel: parkingViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddParkingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Add Parking"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(
            this,
            Factory(
                parkingRepository(
                    ParkingDataBase.getDatabase(applicationContext).DatabaseDao()
                )
            )
        )[parkingViewModel::class.java]

        binding.btnAddTime.setOnClickListener {
            val time =
                LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a")
                )
            binding.eTxtTime.setText(time.toString())
        }
        binding.btnAdd.setOnClickListener {
            if (binding.eTxtOwnerName.text.isEmpty()) {
                binding.eTxtOwnerName.error = "Enter Required"
                return@setOnClickListener
            } else if (binding.eTxtCarNo.text.isEmpty()) {
                binding.eTxtCarNo.error = "Enter Required"
                return@setOnClickListener
            } else if (binding.eTxtPhNo.text.isEmpty() || binding.eTxtPhNo.text.length < 10 || binding.eTxtPhNo.text.length != 10) {
                binding.eTxtPhNo.error = "Enter Required"
                return@setOnClickListener
            } else if (binding.eTxtTime.text.isEmpty()) {
                binding.eTxtTime.error = "Enter Required"
                return@setOnClickListener
            } else if (binding.eTxtOwnerName.text.isEmpty()) {
                binding.eTxtOwnerName.error = "Enter Required"
                return@setOnClickListener
            } else if (binding.eTxtParkingFee.text.isEmpty()) {
                binding.eTxtParkingFee.error = "Enter Required"
                return@setOnClickListener
            }
            viewModel.addParking(
                ParkingEntity(
                    0,
                    binding.eTxtOwnerName.text.toString(),
                    binding.eTxtCarNo.text.toString(),
                    binding.eTxtParkingFee.text.toString(),
                    binding.eTxtPhNo.text.toString(),
                    binding.eTxtTime.text.toString(),
                    "",
                    0
                )
            )
            Toast.makeText(this, "Vehicles add in parking successfully", Toast.LENGTH_SHORT).show()

            binding.eTxtOwnerName.setText("")
            binding.eTxtCarNo.setText("")
            binding.eTxtParkingFee.setText("")
            binding.eTxtPhNo.setText("")
            binding.eTxtTime.setText("")
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}