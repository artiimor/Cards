package es.uam.eps.dadm.juanmartin.cards

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.uam.eps.dadm.juanmartin.cards.databinding.FragmentCardListBinding
import es.uam.eps.dadm.juanmartin.cards.database.CardDatabase
import java.util.concurrent.Executors

class CardListFragment : Fragment() {
    private lateinit var binding: FragmentCardListBinding
    private lateinit var adapter: CardAdapter
    private val executor = Executors.newSingleThreadExecutor()

    /*private var reference = FirebaseDatabase
        .getInstance()
        .getReference(DATABASENAME)*/

    /*Para cargar la lista de tarjetas desde Room*/
    private val cardListViewModel by lazy {
        ViewModelProvider(this).get(CardListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_card_list,
            container,
            false
        )
        adapter = CardAdapter()

        cardListViewModel.cards.observe(
            viewLifecycleOwner,
            Observer {
                adapter.data = it
                adapter.notifyDataSetChanged()
            })

        cardListViewModel.loadDeckId(CardsApplication.coCurrentDeck)
        binding.cardRecyclerView.adapter = adapter
        /*
        adapter.data = emptyList()
        val args = CardListFragmentArgs.fromBundle(requireArguments())*/

        /* Antes de firebase
        binding.newCardFab.setOnClickListener {
            val card = Card("", "")
            executor.execute {
                CardDatabase.getInstance(
                    requireContext()
                ).cardDao.addCard(card)
            }
            reference.child(card.id).setValue(card)
            it.findNavController().navigate(
                CardListFragmentDirections
                    .actionCardListFragmentToCardEditFragment(card.id)
            )
        }*/

        binding.newCardFab.setOnClickListener {
            val card = Card("", "",deckId = CardsApplication.coCurrentDeck, userId = Firebase.auth.currentUser!!.uid)
            //reference.child(card.id).setValue(card)

            // Anhadir a Room
            executor.execute{
                val cardDatabase = CardDatabase.getInstance(context = cardListViewModel.getApplication())
                cardDatabase.cardDao.addCard(card)
            }

            it.findNavController().navigate(
                CardListFragmentDirections
                    .actionCardListFragmentToCardEditFragment(card.id)
            )
        }



        /*cardListViewModel.cards.observe(
            viewLifecycleOwner,
            Observer {
                adapter.data = it
                adapter.notifyDataSetChanged()
            })*/

        //context?.let { SettingsActivity.setMaximumNumberOfCards(it, "20") }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_card_list, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
            }
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(context, AuthActivity::class.java))
            }
        }
        return true
    }

}
