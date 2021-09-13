package goryunov.bpmobile_test.data.db

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "UserData")
data class User @JvmOverloads constructor(
    @PrimaryKey val name: String,
    val password: String,
    val imageProfile: Int,
    @Ignore val confirm: String = ""


)
