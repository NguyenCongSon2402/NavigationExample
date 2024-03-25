package net.braniumacademy.chapter14.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import net.braniumacademy.chapter14.databinding.FragmentDoneBinding
import net.braniumacademy.chapter14.databinding.FragmentRegisterBinding

class DoneFragment : Fragment() {
    private lateinit var binding: FragmentDoneBinding
    private val args: DoneFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoneBinding.inflate(inflater, container, false)
        binding.btnConfirm.setOnClickListener {
            val action = RegisterFragmentDirections.actionGlobalRegisterFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textMessageDone.text = getString(args.messageId)
    }
}