package com.bram3r.androidexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.bram3r.androidexercise.databinding.ActivityMainBinding
import com.bram3r.androidexercise.ui.dialog.CreateUpdateUserDialog
import com.bram3r.androidexercise.ui.userlist.UserListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        var fragmentManagerConstant: FragmentManager? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun newUser(view: View) {

        val newUserDialog = CreateUpdateUserDialog(null, null, null)
        fragmentManagerConstant = supportFragmentManager
        newUserDialog.show(fragmentManagerConstant!!, "newUserDialog")
    }

    fun showList(view: View) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, UserListFragment()).commit()
    }
}