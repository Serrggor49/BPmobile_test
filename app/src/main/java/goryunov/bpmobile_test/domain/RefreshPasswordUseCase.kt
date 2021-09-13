package goryunov.bpmobile_test.domain

import goryunov.bpmobile_test.data.RepositoryImpl
import kotlinx.coroutines.flow.Flow

class RefreshPasswordUseCase(private val repository: RepositoryImpl) {

    suspend fun refreshPasswordFlow(oldPassword: String, newPassword: String, confirm: String) : Flow<String> {
        return repository.refreshPassword(oldPassword, newPassword, confirm)
    }

}