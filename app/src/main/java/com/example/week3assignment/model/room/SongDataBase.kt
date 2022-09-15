package com.example.week3assignment.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.week3assignment.model.SongData

@Database(entities = [SongData::class], version = 4, exportSchema = false)
abstract class SongDataBase: RoomDatabase() {

    abstract fun songDao(): SongDao

    companion object{
        @Volatile
        private var INSTANCE: SongDataBase? = null

        fun getDatabase(context: Context): SongDataBase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SongDataBase::class.java,
                    "Song_List"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }


}