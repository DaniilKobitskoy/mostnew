package com.bets.mos.tbe.ts.most

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bets.mos.tbe.ts.most.databinding.FragmentMenuBinding


class menu : Fragment() {
   lateinit var binding: FragmentMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
binding.button.setOnClickListener {

    val calc = SimpleInterestFragment()
    var new = requireFragmentManager().beginTransaction()
    new.replace(bm.mainView.id, calc)
    new.commit()

}
        binding.button2.setOnClickListener {

            val calc = firscalc()
            var new = requireFragmentManager().beginTransaction()
            new.replace(bm.mainView.id, calc)
            new.commit()

        }
        binding.button3.setOnClickListener {

            val calc = threecalc()
            var new = requireFragmentManager().beginTransaction()
            new.replace(bm.mainView.id, calc)
            new.commit()

        }
    }

    companion object {

        fun newInstance() =
            menu()
    }
}