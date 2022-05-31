package com.example.makeanumber.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.makeanumber.R
import com.example.makeanumber.databinding.FragmentChooseLevelBinding
import com.example.makeanumber.domain.entity.Level
import java.lang.RuntimeException

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonListeners()
    }

    private fun setButtonListeners() {
        binding.buttonLevelEasy.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, GameFragment.newInstance(Level.EASY))
                .addToBackStack(null)
                .commit()
        }
        binding.buttonLevelHard.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, GameFragment.newInstance(Level.HARD))
                .addToBackStack(null)
                .commit()
        }
        binding.buttonLevelNormal.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, GameFragment.newInstance(Level.MEDIUM))
                .addToBackStack(null)
                .commit()
        }
        binding.buttonLevelTest.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, GameFragment.newInstance(Level.TEST))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }
    }
}