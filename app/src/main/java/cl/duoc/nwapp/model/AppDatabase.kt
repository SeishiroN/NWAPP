package cl.duoc.nwapp.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Datos::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun datosDao(): DatosDao
}

