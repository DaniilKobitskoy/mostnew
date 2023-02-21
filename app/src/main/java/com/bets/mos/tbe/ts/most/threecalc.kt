package com.bets.mos.tbe.ts.most

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bets.mos.tbe.ts.most.databinding.FragmentThreecalcBinding


class threecalc : Fragment() {

    lateinit var binding: FragmentThreecalcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThreecalcBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.back3.setOnClickListener {

            val menu = menu()
            var menus = requireFragmentManager().beginTransaction()
            menus.replace(bm.mainView.id, menu)
            menus.commit()

        }
        // Set up the calculate button
        binding.calculateButton.setOnClickListener {
            val wager = binding.wagerEditText.text.toString().toDouble()
            val odds = binding.oddsEditText.text.toString().toDouble()
            val spread = binding.spreadEditText.text.toString().toDouble()

            val payout = calculatePointSpreadPayout(wager, odds, spread)

            binding.payoutTextView.text = getString(R.string.payout_result, payout)
        }
    }

    private fun calculatePointSpreadPayout(wager: Double, odds: Double, spread: Double): Double {
        val payout: Double

        val netPoints = spread + (odds / 100.0)

        if (netPoints > 0) {
            payout = wager * (netPoints / 100.0 + 1)
        } else {
            payout = wager / (-netPoints / 100.0) + wager
        }

        return payout
    }
}



