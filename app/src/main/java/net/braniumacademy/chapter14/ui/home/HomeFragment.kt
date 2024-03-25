package net.braniumacademy.chapter14.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import net.braniumacademy.chapter14.MyApplication
import net.braniumacademy.chapter14.R
import net.braniumacademy.chapter14.data.model.Profile
import net.braniumacademy.chapter14.databinding.FragmentHomeBinding
import net.braniumacademy.chapter14.ui.adapter.UserAdapter
import net.braniumacademy.chapter14.ui.viewmodel.UserViewModel
import net.braniumacademy.chapter14.ui.viewmodel.UserViewModelFactory

class HomeFragment : Fragment(), MenuProvider {
    private lateinit var adapter: UserAdapter
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: UserViewModel by activityViewModels {
        val repository =
            (requireActivity().application as MyApplication).repository
        UserViewModelFactory(repository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        requireActivity().actionBar?.title = getString(R.string.title_home)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        setUpRecyclerView()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecyclerView() {
        adapter = UserAdapter(menuListener = object : OptionMenuClickListener {
            override fun update(user: Profile) {
            }

            override fun delete(user: Profile) {
            }

            override fun viewDetail(id: Int) {
            }
        },
            clickListener = object : OnClickListener {
                override fun onCLick(id: Int) {
                }
            }
        )
        binding.recyclerUsers.adapter = adapter
        val itemDecoration = DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL
        )
        binding.recyclerUsers.addItemDecoration(itemDecoration)
        viewModel.users.observe(viewLifecycleOwner) {
            adapter.updateData(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.main_menu_item_profile -> {
                val action = HomeFragmentDirections
                    .actionHomeFragmentToProfileFragment()
                findNavController().navigate(action)
                true
            }

            else -> false
        }
    }

    interface OnClickListener {
        fun onCLick(id: Int)
    }

    interface OptionMenuClickListener {
        fun update(user: Profile)
        fun delete(user: Profile)
        fun viewDetail(id: Int)
    }
}