package net.braniumacademy.chapter14.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import net.braniumacademy.chapter14.MyApplication
import net.braniumacademy.chapter14.R
import net.braniumacademy.chapter14.databinding.FragmentRegisterBinding
import net.braniumacademy.chapter14.ui.afterTextChanged
import net.braniumacademy.chapter14.ui.viewmodel.NewUserViewModel
import net.braniumacademy.chapter14.ui.viewmodel.NewUserViewModelFactory

class RegisterFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentRegisterBinding
    private var avatarId = 0
    private var anchorView: BottomNavigationView? = null
    private val viewModel: NewUserViewModel by viewModels {
        val repository =
            (requireActivity().application as MyApplication).repository
        NewUserViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        anchorView = requireActivity().findViewById(R.id.bottom_nav)
        setUpAddButtonState()
        addActionListener()
        observeViewState()
        return binding.root
    }

    private fun setUpAddButtonState(state: Boolean = false) {
        binding.btnRegisterAdd.isEnabled = state
    }

    private fun addActionListener() {
        // button clear
        binding.btnRegisterClear.setOnClickListener {
            clearTexts()
            resetAvatarImageStates()
        }

        // button add
        binding.btnRegisterAdd.setOnClickListener {
            val email = binding.editRegisterEmail.text.toString()
            val fullName = binding.editRegisterFullName.text.toString()
            viewModel.addNewUser(fullName, email, avatarId)
        }

        // avatar image
        binding.include.image1.setOnClickListener(this)
        binding.include.image2.setOnClickListener(this)
        binding.include.image3.setOnClickListener(this)
        binding.include.image4.setOnClickListener(this)
        binding.include.image5.setOnClickListener(this)
        binding.include.image6.setOnClickListener(this)

        // edit text
        binding.editRegisterFullName.afterTextChanged {
            viewModel.registerInfoChanged(
                binding.editRegisterEmail.text.toString(),
                binding.editRegisterFullName.text.toString(),
                avatarId
            )
        }
        binding.editRegisterEmail.afterTextChanged {
            viewModel.registerInfoChanged(
                binding.editRegisterEmail.text.toString(),
                binding.editRegisterFullName.text.toString(),
                avatarId
            )
        }
    }

    private fun observeViewState() {
        viewModel.registerFormState.observe(viewLifecycleOwner) {
            if (!it.isCorrect) {
                if (it.emailError != null) {
                    binding.editRegisterEmail.error = getString(it.emailError)
                }
                if (it.fullNameError != null) {
                    binding.editRegisterFullName.error = getString(it.fullNameError)
                }
                if (it.avatarError != null) {
                    val snackBar = Snackbar.make(binding.root, it.avatarError, Snackbar.LENGTH_LONG)
                    snackBar.anchorView = anchorView
                    snackBar.show()
                }
                setUpAddButtonState(false)
            } else {
                setUpAddButtonState(true)
            }
        }

        viewModel.savedState.observe(viewLifecycleOwner) {
            if (it) {
                avatarId = 0
                clearTexts()
                resetAvatarImageStates()
                val messageId = R.string.message_register_success
                val action = RegisterFragmentDirections
                    .actionRegisterFragmentToDoneFragment(messageId)
                findNavController().navigate(action)
            } else {
                val snackBar = Snackbar.make(
                    binding.root, R.string.error_email_being_used, Snackbar.LENGTH_LONG
                )
                snackBar.anchorView = anchorView
                snackBar.show()
            }
        }
    }

    private fun clearTexts() {
        binding.editRegisterEmail.text?.clear()
        binding.editRegisterFullName.text?.clear()
    }

    private fun resetAvatarImageStates(state: Boolean = false) {
        binding.include.image1.isSelected = state
        binding.include.image2.isSelected = state
        binding.include.image3.isSelected = state
        binding.include.image4.isSelected = state
        binding.include.image5.isSelected = state
        binding.include.image6.isSelected = state
    }

    override fun onClick(v: View?) {
        v?.let {
            val oldState = it.isSelected
            resetAvatarImageStates()
            it.isSelected = !oldState
            avatarId = if (it.isSelected) {
                getDrawableIdFromViewId(it.id)
            } else {
                0
            }
            viewModel.registerInfoChanged(
                binding.editRegisterEmail.text.toString(),
                binding.editRegisterFullName.text.toString(),
                avatarId
            )
        }
    }

    private fun getDrawableIdFromViewId(viewId: Int): Int {
        return when (viewId) {
            R.id.image1 -> R.drawable.avatar_1_raster
            R.id.image2 -> R.drawable.avatar_2_raster
            R.id.image3 -> R.drawable.avatar_3_raster
            R.id.image4 -> R.drawable.avatar_4_raster
            R.id.image5 -> R.drawable.avatar_5_raster
            R.id.image6 -> R.drawable.avatar_6_raster
            else -> 0
        }
    }
}