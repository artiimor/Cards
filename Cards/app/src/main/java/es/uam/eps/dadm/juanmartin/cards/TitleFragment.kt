package es.uam.eps.dadm.juanmartin.cards

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import es.uam.eps.dadm.juanmartin.cards.databinding.FragmentTitleBinding

class TitleFragment: Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(TitleViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
            inflater,
            R.layout.fragment_title,
            container,
            false)

        var aux = viewModel.mazosFirebase
        binding.cardsTitleTextView.setOnClickListener { view ->
            //if (CardDatabase.getInstance(CardsApplication()).cardDao.nTotalCards() > 0)
            if(CardsApplication.coCurrentDeck != -1L)
                //view.findNavController().navigate(R.id.action_titleFragment_to_cardListFragment)
                view.findNavController().navigate(R.id.action_titleFragment_to_studyFragment)

            else
                Toast.makeText(activity, R.string.no_deck_selected, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}