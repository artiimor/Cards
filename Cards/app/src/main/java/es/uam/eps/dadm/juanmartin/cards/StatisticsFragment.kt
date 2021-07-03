package es.uam.eps.dadm.juanmartin.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import es.uam.eps.dadm.juanmartin.cards.databinding.FragmentStatisticsBinding
import timber.log.Timber


class StatisticsFragment: Fragment() {
    private lateinit var binding: FragmentStatisticsBinding

    private val viewModel by lazy {
        ViewModelProvider(this).get(StatisticsViewModel::class.java)
    }

    private var totalCards: Int = 0
    private var totalDecks: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_statistics,
            container,
            false
        )

        viewModel.cards.observe(
            viewLifecycleOwner,
            Observer {
                totalCards = it.size
                binding.cardsCreated.text = "" + getString(R.string.total_cards) + " " + totalCards
            })

        viewModel.decks.observe(
            viewLifecycleOwner,
            Observer {
                Timber.i(it.size.toString())
                totalDecks = it.size
                binding.decksCreated.text = "" + getString(R.string.total_decks) + " " + totalDecks

            })

        return binding.root
    }
}