package com.example.numbergenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.example.numbergenerator.databinding.FragmentNumberConfigBinding

class NumberConfigFragment : Fragment() {

    private var _binding: FragmentNumberConfigBinding? = null
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
        _binding = FragmentNumberConfigBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            swtNotRepeatNumbers.setOnCheckedChangeListener { _, isChecked ->
                swtNotRepeatNumbers.trackTintList = getColorStateList(
                    requireContext(),
                    if (isChecked) R.color.background_brand else R.color.content_tertiary
                )

                viewModel.setShouldRepeatNumbers(shouldRepeatNumbers = !isChecked)
            }

            etAmountNumbers.addTextChangedListener { text ->
                viewModel.setDrawAmountNumber(drawAmountNumber = text.toString().toIntOrNull() ?: 0)
            }

            etInitialLimit.addTextChangedListener { text ->
                viewModel.setInitialLimit(initialLimit = text.toString().toIntOrNull() ?: 0)
            }

            etFinalLimit.addTextChangedListener { text ->
                viewModel.setFinalLimit(finalLimit = text.toString().toIntOrNull() ?: 0)
            }
        }
    }

}