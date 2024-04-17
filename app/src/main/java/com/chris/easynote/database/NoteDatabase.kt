package com.chris.easynote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chris.easynote.model.Note

@Database(entities = [Note::class], version = 1)
abstract class  NoteDatabase: RoomDatabase() {

    abstract fun getNoteDao() : NoteDao

    companion object{
        @Volatile // Ensures that changes made by one threads immediately visible to other threads
        private var instance: NoteDatabase? = null // Hold the note database or null
        private val LOCK = Any() // to Sync the database creation process, ensure only one code can be
        // executed at one time

        // Invoke here to allows you to create an instance of note database by calling notedb context,
        // only one is created
        operator fun invoke(context: Context) = instance?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_db"
            ).build()
    }

}