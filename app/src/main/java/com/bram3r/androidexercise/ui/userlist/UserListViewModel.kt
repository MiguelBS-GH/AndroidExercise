package com.bram3r.androidexercise.ui.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bram3r.androidexercise.model.User
import com.bram3r.androidexercise.model.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserListViewModel : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _userListStateProgressBar = MutableLiveData<Boolean>()
    val userListStateProgressBar: LiveData<Boolean> get() = _userListStateProgressBar

    init {
        _users.value = listOf()
    }

    fun updateUsers() {

        val userRepository: UserRepository = UserRepository.instance!!

        CoroutineScope(Dispatchers.IO).launch {

            val response = userRepository.getUserService().getUsers()

            withContext(Dispatchers.Main) {
                _userListStateProgressBar.value = true
                if (response.isSuccessful) {
                    val userList: List<User>? = response.body()
                    if (userList != null) {
                        _userListStateProgressBar.value = false
                        _users.value = userList!!
                        println(" --------------x " + _users.value + " x " + _userListStateProgressBar.value)
                    }
                } else {
                    println("Fallo al recoger la lista" + response.code().toString())
                }
            }
        }
    }
}