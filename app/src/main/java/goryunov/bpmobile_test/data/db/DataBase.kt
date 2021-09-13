package goryunov.bpmobile_test.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class), version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun userDataDao(): DaoUser

}