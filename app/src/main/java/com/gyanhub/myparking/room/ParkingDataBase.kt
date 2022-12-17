package com.gyanhub.myparking.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ParkingEntity::class,HistoryEntity::class], version =2)
abstract class ParkingDataBase: RoomDatabase() {
    abstract fun DatabaseDao(): DbDao
    companion object {

        @Volatile
        private var INSTANCE: ParkingDataBase? = null

        fun getDatabase(context: Context): ParkingDataBase {
            synchronized(this) {
                if (INSTANCE == null) {
                    synchronized(this) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ParkingDataBase::class.java,
                            "Parking_db"
                        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}