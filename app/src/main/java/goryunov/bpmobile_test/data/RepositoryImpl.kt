package goryunov.bpmobile_test.data

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import goryunov.bpmobile_test.data.db.DaoUser
import goryunov.bpmobile_test.data.db.DataBase
import goryunov.bpmobile_test.data.db.User
import goryunov.bpmobile_test.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton


@Singleton
class RepositoryImpl : Repository {

    val NOT_CONFIRM = "Пароли не совпадают"
    val BAD_PASSWORD_NAME = "Пароль должен состоять только из цифр и букв латинского алфавита"
    val USER_EXIST = "Пользователь с таким именем уже существует"
    val USER_DONT_EXIST = "Неправильные логин или пароль"
    val PASSWORD_DONT_EXIST = "Неправильный пароль"
    val EMPTY_PASSWORD_FIELD = "Задайте пароль"
    val EMPTY_USER_NAME_FIELD = "Придумайте логин"
    val SUCCESSFUL = "OK"
    val REFRESH_SUCCESSFUL = "Пароль успешно изменен"

    lateinit var userProfile: User
    lateinit var dataBaseS: DataBase

    override fun getUser(): User {
        return userProfile
    }

    suspend fun userExist(name: String, daoUser: DaoUser): Boolean {
        var exist = false

        val job = CoroutineScope(Dispatchers.IO).launch {
            val users: List<User> = daoUser.getAll()
            for (user in users) {
                if (user.name == name) {
                    exist = true
                }
            }
        }
        job.join()
        return exist
    }

    override suspend fun addUser(user: User, dataBase: DataBase): Flow<String> = flow {
        dataBaseS = dataBase
        if (user.name.length < 1) emit(EMPTY_USER_NAME_FIELD)
        else if (user.password.length < 1) emit(EMPTY_PASSWORD_FIELD)
        else if (user.password != user.confirm) emit(NOT_CONFIRM)
        else if (user.password == user.confirm && (!checkPassword(user.password))) emit(
            BAD_PASSWORD_NAME
        )
        else {
            if (!userExist(user.name, dataBase.userDataDao())) { // если юзер есть в бд
                val daoUser = dataBase.userDataDao()
                daoUser.insertUser(User(user.name, user.password, user.imageProfile))
                userProfile = user
                emit(SUCCESSFUL)
            } else {
                emit(USER_EXIST)
            }
        }
    }


    override fun logIn(user: User, dataBase: DataBase): Flow<String> =
        flow {
            dataBaseS = dataBase

            if (!userExist(user.name, dataBase.userDataDao())) { // если такого юзера не существует
                emit(USER_DONT_EXIST)
            } else {
                if (user.password != dataBase.userDataDao().getUserById(user.name).password)
                    emit(USER_DONT_EXIST)
                else {
                    userProfile = dataBase.userDataDao().getUserById(user.name)
                    emit(SUCCESSFUL)
                }

            }
        }


    override suspend fun refreshPassword(
        oldPassword: String,
        newPassword: String,
        confirm: String
    ): Flow<String> = flow {
        if (oldPassword == userProfile.password) {
            if (newPassword != confirm) emit (NOT_CONFIRM)  // пароли не совпадают
            else if (!checkPassword(newPassword)) emit (BAD_PASSWORD_NAME) // пароль не подходит
            else {
                val daoUser = dataBaseS.userDataDao()
                daoUser.insertUser(User(userProfile.name, newPassword, userProfile.imageProfile))
                emit (REFRESH_SUCCESSFUL)
            }
        } else emit (PASSWORD_DONT_EXIST)
    }


    fun checkPassword(password: String): Boolean {
        return password.filter { it in 'A'..'Z' || it in 'a'..'z' || it in '0'..'9' }.length == password.length
    }



}