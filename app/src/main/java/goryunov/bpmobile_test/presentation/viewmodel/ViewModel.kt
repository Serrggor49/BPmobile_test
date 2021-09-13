package goryunov.bpmobile_test.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import goryunov.bpmobile_test.data.RepositoryImpl
import goryunov.bpmobile_test.data.db.DataBase
import goryunov.bpmobile_test.data.db.User
import goryunov.bpmobile_test.domain.LogInUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var context: Application

    @Inject
    lateinit var repositoryImpl: RepositoryImpl

    var liveDataAnswer = MutableLiveData<String>()

    fun logInFlow(name: String, password: String) {

        val loginUseCaseFlow = LogInUseCase(repositoryImpl)
        val db: DataBase = Room.databaseBuilder(context, DataBase::class.java, "database").build()

        viewModelScope.launch {
            loginUseCaseFlow.logInFlow(User(name, password, 0), db).collect {
                liveDataAnswer.postValue(it)
            }
        }



    }
}