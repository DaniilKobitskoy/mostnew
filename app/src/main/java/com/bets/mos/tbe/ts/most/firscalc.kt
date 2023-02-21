package com.bets.mos.tbe.ts.most

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.children
import com.bets.mos.tbe.ts.most.databinding.FragmentFirscalcBinding


class firscalc : Fragment() {
   lateinit var binding: FragmentFirscalcBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFirscalcBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("StringFormatInvalid")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
binding.back2.setOnClickListener {

    val menu = menu()
    var menus = requireFragmentManager().beginTransaction()
    menus.replace(bm.mainView.id, menu)
    menus.commit()

}
        // Set up the calculate button
        binding.calculateButton.setOnClickListener {
            val wager = binding.wagerEditText.text.toString().toDouble()

            // Get the odds from each selection and calculate the multiplier
            val multiplier = binding.selectionsLayout.children.mapNotNull { view ->
                if (view is ViewGroup) {
                    val oddsEditText = view.findViewById<EditText>(R.id.oddsEditText)
                    oddsEditText.text.toString().toDoubleOrNull()?.let { odds ->
                        odds / 100.0 + 1
                    }
                } else {
                    null
                }
            }.reduceOrNull(Double::times)

            if (multiplier == null) {
                // If there are no valid odds entered, display an error message
                binding.payoutTextView.text = getString(R.string.error_message)
            } else {
                // Calculate the total payout and display the result
                val payout = wager * multiplier
                binding.payoutTextView.text = getString(R.string.payout_result, payout)
            }
        }
    }
}
//    companion object {
//
//        fun newInstance() =
//            firscalc()
//    }
//}