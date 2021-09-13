package goryunov.bpmobile_test.domain

import goryunov.bpmobile_test.data.db.DataBase
import goryunov.bpmobile_test.data.db.User
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun addUser(user: User, dataBase: DataBase) : Flow<String>
    suspend fun refreshPassword(oldPassword: String, newPassword: String, confirm: String) : Flow<String>
    fun getUser() : User

    fun logIn(user: User, dataBase: DataBase) : Flow<String>

}