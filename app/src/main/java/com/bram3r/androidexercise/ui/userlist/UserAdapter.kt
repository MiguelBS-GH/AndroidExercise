package com.bram3r.androidexercise.ui.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bram3r.androidexercise.R
import com.bram3r.androidexercise.model.User
import com.bram3r.androidexercise.model.UserRepository
import com.bram3r.androidexercise.ui.dialog.CreateUpdateUserDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.EOFException
import kotlin.properties.Delegates

class UserAdapter(users: List<User>, private val manager: FragmentManager) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    var users: List<User> by Delegates.observable(users){ _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = users[position]
        holder.bind(item, manager)

    }

    override fun getItemCount(): Int = users.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name: TextView = view.findViewById(R.id.userNameTextView)
        private val birthdate: TextView = view.findViewById(R.id.userBirthdateTextView)
        private val delete: Button = view.findViewById(R.id.userDeleteButton)
        private val update: Button = view.findViewById(R.id.userEditButton)

        fun bind(user: User, manager: FragmentManager) {
            name.text = user.name ?: "Null name"
            birthdate.text = user.birthdate

            delete.setOnClickListener {

                val userRepository: UserRepository = UserRepository.instance!!

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        userRepository.getUserService().deleteUser(user.id).request()
                    } catch (e: EOFException) {
                        println("${e.message}")
                    }
                    withContext(Dispatchers.Main){
                        UserListFragment.viewModel.updateUsers()

                    }
                }

            }

            update.setOnClickListener {
                val updateUserDialog: CreateUpdateUserDialog = CreateUpdateUserDialog(user.name, user.birthdate, user.id)
                val newUserNameEditText: EditText? = updateUserDialog.view?.findViewById(R.id.editTextUserName)
                val newBirthdateEditText: EditText? = updateUserDialog.view?.findViewById(R.id.editTextUserBirthdate)
                println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh $newUserNameEditText")
                newUserNameEditText?.setText(user.name)
                newBirthdateEditText?.setText(user.birthdate)
                updateUserDialog.show(manager, "updateUserDialog")
            }
        }
    }
}
