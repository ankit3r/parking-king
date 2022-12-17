package com.gyanhub.myparking


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyanhub.myparking.adapter.MainAdapter
import com.gyanhub.myparking.databinding.ActivityMainBinding
import com.gyanhub.myparking.interfaces.onClick
import com.gyanhub.myparking.repository.parkingRepository
import com.gyanhub.myparking.room.ParkingDataBase
import com.gyanhub.myparking.room.ParkingEntity
import com.gyanhub.myparking.viewModel.Factory
import com.gyanhub.myparking.viewModel.parkingViewModel

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), onClick {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: parkingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Parking"
        viewModel = ViewModelProvider(
            this,
            Factory(
                parkingRepository(
                    ParkingDataBase.getDatabase(applicationContext).DatabaseDao()
                )
            )
        )[parkingViewModel::class.java]
        binding.rcHome.layoutManager = LinearLayoutManager(this)

        viewModel.getParking().observe(this) {
            val adapter = MainAdapter(this, it, this)
            binding.rcHome.adapter = adapter
        }

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, AddParkingActivity::class.java))
        }
    }

    override fun onClicks(position: Int, list: List<ParkingEntity>) {
        val i = Intent(this, VerifiedActivity::class.java)
        i.putExtra("key", position)
        startActivityForResult(i, 101)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            alertDialog(data!!.getStringExtra("mainKey").toString())
        }
    }

    fun remove(vNo: String) {
        Toast.makeText(this, "vNo : $vNo", Toast.LENGTH_SHORT).show()
        viewModel.remove(vNo)

    }

    private fun alertDialog(vNo:String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Save History")
        builder.setMessage("are you sure?")

        builder.setPositiveButton("Yes") { dialog, which ->
           remove(vNo)
        }

        builder.setNegativeButton("No") { dialog, which ->
            Toast.makeText(
                applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT
            ).show()
        }


        builder.show()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.btnHistory ->{
                startActivity(Intent(this,HistroyActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}