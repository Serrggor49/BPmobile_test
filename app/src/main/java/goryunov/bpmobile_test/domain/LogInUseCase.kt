package goryunov.bpmobile_test.domain

import goryunov.bpmobile_test.data.db.DataBase
import goryunov.bpmobile_test.data.db.User
import kotlinx.coroutines.flow.Flow

class LogInUseCase (private val repository : Repository) {

    fun logInFlow (user: User, dataBase: DataBase): Flow<String> {
        return repository.logIn(user, dataBase)
    }

}