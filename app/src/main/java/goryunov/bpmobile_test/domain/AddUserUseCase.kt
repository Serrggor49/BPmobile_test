package goryunov.bpmobile_test.domain

import goryunov.bpmobile_test.data.db.DataBase
import goryunov.bpmobile_test.data.db.User
import kotlinx.coroutines.flow.Flow

class AddUserUseCase(private val repository: Repository) {

    suspend fun addUser(user: User, dataBase: DataBase): Flow<String> {
        return repository.addUser(user, dataBase)
    }

}