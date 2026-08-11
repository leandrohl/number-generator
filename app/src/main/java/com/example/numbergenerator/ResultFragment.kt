package com.example.numbergenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.numbergenerator.databinding.FragmentResultBinding
import kotlinx.coroutines.launch
import kotlin.random.Random


class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DrawViewModel by activityViewModels()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            lifecycleScope.launch {
                viewModel.uiState.collect { uiState ->
                    tvDrawNumber.text = getString(R.string.numero_do_sorteio, uiState.currentDrawNumber.toString())
                    clearLastDrawNumbers()

                    uiState.drawNumbers.forEach { drawNumber ->
                        generateDrawNumberTextView(drawNumber = drawNumber)
                    }
                }
            }

        }
    }


    fun FragmentResultBinding.generateDrawNumberTextView(drawNumber: Int) {
        val drawNumberTextView = TextView(requireContext()).apply {
            id = View.generateViewId()
            text = drawNumber.toString()
            setTextAppearance(R.style.TextAppearance_RobotoMono_Overline)
            textSize = 48f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.content_brand))
        }

        root.addView(drawNumberTextView)
        flowResultNumbersHelper.referencedIds = flowResultNumbersHelper.referencedIds.plus(drawNumberTextView.id)
    }

    private fun FragmentResultBinding.clearLastDrawNumbers() {
        flowResultNumbersHelper.referencedIds.forEach {
            root.removeView(root.findViewById(it))
        }
    }
}