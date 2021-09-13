package goryunov.bpmobile_test.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoUser {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM userdata")
    fun loadAllUserDataFlow(): Flow<List<User>>
 
    @Query("SELECT * FROM UserData")
    fun getAll(): List<User>

    @Query("SELECT * FROM UserData WHERE name = :id")
    suspend fun getUserById(id: String): User


}