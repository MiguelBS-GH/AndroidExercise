package com.bram3r.androidexercise.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.bram3r.androidexercise.R
import com.bram3r.androidexercise.databinding.NewUserDialogBinding
import com.bram3r.androidexercise.model.User
import com.bram3r.androidexercise.model.UserRepository
import com.bram3r.androidexercise.ui.userlist.UserListFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.EOFException
import java.time.LocalDateTime

class CreateUpdateUserDialog(private val name: String?, private val birthDate: String?, private val idUser: Int?): DialogFragment() {

    private var _binding: NewUserDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = NewUserDialogBinding.inflate(LayoutInflater.from(context))

        /**/

        val builder = AlertDialog.Builder(requireActivity())
        builder.apply {
            setView(binding.root)
            if (tag == "newUserDialog") {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    binding.editTextUserBirthdate.setText(LocalDateTime.now().toString())
                } else {
                    binding.editTextUserBirthdate.setText("2020-01-01T00:00:00")
                }
                setPositiveButton(R.string.create_user_button_accept) { _, _ ->
                    val user = User("${binding.editTextUserName.text}", "${binding.editTextUserBirthdate.text}", 0)

                    val userRepository: UserRepository = UserRepository.instance!!

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            userRepository.getUserService().createUser(user).request()
                        } catch (e: EOFException) {
                            println("${e.message}")
                        }
                        withContext(Dispatchers.Main) {
                            UserListFragment.viewModel.updateUsers()
                        }
                    }
                }
            } else if (tag == "updateUserDialog") {
                binding.editTextUserName.setText(name)
                binding.editTextUserBirthdate.setText(birthDate)
                setPositiveButton("Actualizar") { _, _ ->
                    val user= User("${binding.editTextUserName.text}", "${binding.editTextUserBirthdate.text}", idUser!!)
                    val userRepository: UserRepository = UserRepository.instance!!

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            userRepository.getUserService().updateUser(user).request()
                        } catch (e: EOFException) {
                            println("${e.message}")
                        }
                        withContext(Dispatchers.Main) {
                            UserListFragment.viewModel.updateUsers()
                        }
                    }
                }
            }

            setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
        }

        return builder.create()
    }

}