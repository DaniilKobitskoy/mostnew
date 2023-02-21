package com.bets.mos.tbe.ts.most

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bets.mos.tbe.ts.most.databinding.FragmentSimpleInterestBinding


class SimpleInterestFragment : Fragment() {

    private lateinit var binding: FragmentSimpleInterestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSimpleInterestBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("StringFormatInvalid")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.back.setOnClickListener {

            val menu = menu()
            var menus = requireFragmentManager().beginTransaction()
            menus.replace(bm.mainView.id, menu)
            menus.commit()

        }
        binding.calculateButton.setOnClickListener {
            val wager = binding.wagerEditText.text.toString().toDouble()
            val odds = binding.oddsEditText.text.toString().toDouble()

            val payout = calculateMoneylinePayout(wager, odds)

           binding.payoutTextView.text = getString(R.string.payout_result, payout)
        }
    }

    private fun calculateMoneylinePayout(wager: Double, odds: Double): Double {
        val payout: Double

        if (odds >= 0) {
            payout = wager * (odds / 100.0 + 1)
        } else {
            payout = wager / (odds / 100.0)
        }

        return payout

    }
}