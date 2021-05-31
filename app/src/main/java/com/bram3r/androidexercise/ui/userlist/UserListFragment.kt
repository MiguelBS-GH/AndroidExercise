package com.bram3r.androidexercise.ui.userlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bram3r.androidexercise.databinding.UserListFragmentBinding
import java.lang.Error


class UserListFragment : Fragment() {

    private var _binding: UserListFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        lateinit var viewModel: UserListViewModel
    }

    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UserListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserListViewModel::class.java)

        viewModel.userListStateProgressBar.observe(viewLifecycleOwner, Observer {
            binding.userListrogressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        adapter = UserAdapter(viewModel.users.value!!, parentFragmentManager)

        viewModel.users.observe(viewLifecycleOwner, Observer {
            try {
                adapter.users = it
            } catch (e: Error) {
                println("${e.message}")
            }
        })

        viewModel.updateUsers()
        binding.recyclerUser.layoutManager = LinearLayoutManager(context)
        binding.recyclerUser.adapter = adapter
    }
}