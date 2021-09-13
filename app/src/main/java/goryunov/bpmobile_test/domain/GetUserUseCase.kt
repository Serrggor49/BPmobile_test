package goryunov.bpmobile_test.domain

import goryunov.bpmobile_test.data.db.User

class GetUserUseCase (private val repository: Repository) {

    fun getUser() : User {
        return repository.getUser()
    }

}