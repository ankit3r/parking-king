package com.gyanhub.myparking

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gyanhub.myparking.databinding.ActivityVerifiedBinding
import com.gyanhub.myparking.repository.parkingRepository
import com.gyanhub.myparking.room.HistoryEntity
import com.gyanhub.myparking.room.ParkingDataBase
import com.gyanhub.myparking.room.ParkingEntity
import com.gyanhub.myparking.viewModel.Factory
import com.gyanhub.myparking.viewModel.parkingViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class VerifiedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerifiedBinding
    private var position = 0
    lateinit var viewModel: parkingViewModel
    lateinit var time: String
    val random1 = (100000..999999).shuffled().last().toInt()
    var totalFee = 0
    var totalTime: String = ""


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifiedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title="Verification"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        time =
            LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a")
            ).toString()
        binding.TxtEndTime.text = time
        viewModel = ViewModelProvider(
            this,
            Factory(
                parkingRepository(
                    ParkingDataBase.getDatabase(applicationContext).DatabaseDao()
                )
            )
        )[parkingViewModel::class.java]

        position = intent.getIntExtra("key", 0)



        viewModel.getParking().observe(this) { it1 ->

            val i = Intent()
            i.putExtra("mainKey","${it1[position].CarNo}")
            setResult(RESULT_OK,i)
            setData(it1, position)
            stringToDateTime(it1, position, time)
            addInHistoryAndRemoveParking(it1, position)
            binding.btnSend.setOnClickListener {
                sendOtp(it1, position)
            }
        }

        binding.btnOtpSubmit.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            verifiedOtp(random1)
        }
        binding.eTxtOtp.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                verifiedOtp(random1)
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })



    }

    private fun setData(list: List<ParkingEntity>, position: Int) {
        val item = list[position]
        binding.TxtOwnerName.text = item.OwnerName
        binding.TxtPhNo.text = item.PhoneNo
        binding.TxtTime.text = item.EntryTime
        binding.TxtCarNo.text = item.CarNo
        binding.TxtParkingFee.text = "${item.ParkingFee}/H"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun stringToDateTime(list: List<ParkingEntity>, position: Int, cTime: String) {
        val text = list[position].EntryTime
        val pattern = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a")
        val time01 = LocalDateTime.parse(text, pattern)
        val time02 = LocalDateTime.parse(cTime, pattern)
        val year = time02.year - time01.year
        val months = MontInNum(time02.month.toString()) - MontInNum(time01.month.toString())
        val day = time02.dayOfMonth - time01.dayOfMonth
        val hh = time02.hour - time01.hour



        if (hh < 0 || day != 0) {
            if (day <= 0) {

            } else {
                totalTime = "${day * 24 + hh} Hours"
                binding.TxtTotalTime.text = totalTime
                totalFee = (day * 24 + hh) * list[position].ParkingFee.toInt()
                binding.txtTotalParkingFee.text =
                    "$totalFee"
            }
        } else if (hh == 0) {
            totalTime = "1 Hour"
            binding.TxtTotalTime.text = totalTime
            totalFee = 1 * list[position].ParkingFee.toInt()
            binding.txtTotalParkingFee.text = "$totalFee"
        } else {
            totalTime = "$hh Hours"
            binding.TxtTotalTime.text = totalTime
            totalFee = hh * list[position].ParkingFee.toInt()
            binding.txtTotalParkingFee.text = "$totalFee"
        }


    }

    private fun sendOtp(list: List<ParkingEntity>, position: Int) {

        val massage =
            "Conform your vehicles No : ${list[position].CarNo} ,Total parking Amount: $totalFee and share OTP Only with me." +
                    "OTP is $random1"


        val intent = Intent(Intent.ACTION_VIEW)
        intent.data =
            Uri.parse("http://api.whatsapp.com/send?phone=+91${list[position].PhoneNo} + &text=$massage ")
        startActivity(intent)
        binding.btnOtpSubmit.isEnabled = true

    }

    private fun verifiedOtp(Otp: Int) {
        val str = binding.eTxtOtp.text.toString()
        if (Otp == str.toInt()) {
            binding.txtVisib.visibility = View.VISIBLE
            binding.txtVisib.text = "Verified"
            binding.txtVisib.setTextColor(Color.parseColor("#1B5E20"))
            binding.btnSave.isEnabled = true
        } else {
            binding.txtVisib.visibility = View.VISIBLE
            binding.txtVisib.text = "No Match"
            binding.txtVisib.setTextColor(Color.parseColor("#B71C1C"))
            binding.btnSave.isEnabled = false
        }

    }

    private fun MontInNum(mm: String): Int {
        return when (mm) {
            "JANUARY" -> 1
            "FEBRUARY" -> 2
            "MARCH" -> 3
            "APRIL" -> 4
            "MAY" -> 5
            "JUNE" -> 6
            "JULY" -> 7
            "AUGUST" -> 8
            "SEPTEMBER" -> 9
            "OCTOBER" -> 10
            "NOVEMBER" -> 11
            "DECEMBER" -> 11
            else -> 0
        }
    }

    fun addInHistoryAndRemoveParking(list: List<ParkingEntity>, position: Int) {
        binding.btnSave.setOnClickListener {
            viewModel.addHistory(
                HistoryEntity(
                    0,
                    list[position].OwnerName,
                    list[position].CarNo,
                    list[position].ParkingFee,
                    totalFee.toString(),
                    list[position].PhoneNo,
                    list[position].EntryTime,
                    time,
                    totalTime,
                    binding.txtTotalParkingFee.isChecked
                )
            )
            onBackPressed()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}