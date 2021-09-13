package goryunov.bpmobile_test.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import goryunov.bpmobile_test.data.RepositoryImpl
import goryunov.bpmobile_test.data.db.DataBase
import goryunov.bpmobile_test.data.db.User
import goryunov.bpmobile_test.domain.AddUserUseCase
import kotlinx.coroutines.flow.collect

import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var context: Application

    @Inject
    lateinit var repositoryImpl: RepositoryImpl

    var liveData = MutableLiveData<String>()

    fun registerUser(name: String, password: String, imageProfile: Int, confirm: String) {

        val addUserUseCaseFlow = AddUserUseCase(repositoryImpl)

        viewModelScope.launch {
            val db: DataBase = Room.databaseBuilder(context, DataBase::class.java, "database").build()
            addUserUseCaseFlow.addUser(User(name, password, imageProfile, confirm ), db).collect {
                liveData.postValue(it)
            }
        }
    }

}