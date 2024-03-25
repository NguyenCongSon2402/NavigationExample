package net.braniumacademy.chapter14.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import net.braniumacademy.chapter14.MyApplication
import net.braniumacademy.chapter14.R
import net.braniumacademy.chapter14.databinding.FragmentLoginBinding
import net.braniumacademy.chapter14.ui.afterTextChanged
import net.braniumacademy.chapter14.ui.viewmodel.ProfileViewModel
import net.braniumacademy.chapter14.ui.viewmodel.ProfileViewModelFactory

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: ProfileViewModel by activityViewModels {
        val repository =
            (requireActivity().application as MyApplication).profileRepository
        ProfileViewModelFactory(repository)
    }
    private lateinit var navController: NavController
    private lateinit var savedStateHandle: SavedStateHandle
    private var isButtonLoginClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding
            .inflate(inflater, container, false)
        setUpActions()
        setUpObserve()
        return binding.root
    }

    private fun setUpActions() {
        binding.btnLogin.setOnClickListener {
            val email = binding.editLoginEmail.text.toString()
            val password = binding.editLoginPassword.text.toString()
            viewModel.login(email, password)
            isButtonLoginClicked = true
        }

        binding.btnLoginCancel.setOnClickListener {
            savedStateHandle[LOGIN_SUCCESSFUL] = false
            navController.popBackStack()
        }

        binding.editLoginEmail.afterTextChanged {
            val email = binding.editLoginEmail.text.toString()
            val password = binding.editLoginPassword.text.toString()
            viewModel.loginDataChanged(email, password)
        }

        binding.editLoginPassword.afterTextChanged {
            val email = binding.editLoginEmail.text.toString()
            val password = binding.editLoginPassword.text.toString()
            viewModel.loginDataChanged(email, password)
        }
    }

    private fun setUpObserve() {
        viewModel.user.observe(viewLifecycleOwner) {
            if (isButtonLoginClicked) {
                isButtonLoginClicked = false
                if (it == null) {
                    closeSortKeyboard()
                    showError()
                } else {
                    savedStateHandle[LOGIN_SUCCESSFUL] = true
                    navController.popBackStack()
                }
            }
        }

        viewModel.loginFormState.observe(viewLifecycleOwner) {
            if (it.emailError != null) {
                binding.editLoginEmail.error = getString(it.emailError)
                binding.btnLogin.isEnabled = false
            } else if (it.passwordError != null) {
                binding.editLoginPassword.error = getString(it.passwordError)
                binding.btnLogin.isEnabled = false
            } else if (it.isCorrect) {
                binding.btnLogin.isEnabled = true
            }
        }
    }

    private fun showError() {
        val snackbar = Snackbar.make(
            binding.root,
            R.string.err_login_failed,
            Snackbar.LENGTH_LONG
        )
        snackbar.show()
    }

    private fun closeSortKeyboard() {
        val imm = requireActivity()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedStateHandle = navController.previousBackStackEntry!!.savedStateHandle
        savedStateHandle[LOGIN_SUCCESSFUL] = false
    }

    companion object {
        const val LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL"
    }
}