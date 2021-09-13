package goryunov.bpmobile_test.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import goryunov.bpmobile_test.data.RepositoryImpl
import goryunov.bpmobile_test.data.db.User
import goryunov.bpmobile_test.domain.GetUserUseCase
import goryunov.bpmobile_test.domain.RefreshPasswordUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var context: Application

    @Inject
    lateinit var repositoryImpl: RepositoryImpl

    var liveData = MutableLiveData<User>()
    var liveDataNewPasswordAnswer = MutableLiveData<String>()


    fun init() {
        val getUserUseCase = GetUserUseCase(repositoryImpl)
        liveData.postValue(getUserUseCase.getUser())
    }


    fun refreshPassword(oldPassword: String, newPassword: String, confirmPassword: String) {
        val refreshPasswordUseCaseFlow = RefreshPasswordUseCase(repositoryImpl)
        viewModelScope.launch {
            refreshPasswordUseCaseFlow.refreshPasswordFlow(
                oldPassword,
                newPassword,
                confirmPassword
            ).collect {
                liveDataNewPasswordAnswer.postValue(it)
            }
        }
    }

}