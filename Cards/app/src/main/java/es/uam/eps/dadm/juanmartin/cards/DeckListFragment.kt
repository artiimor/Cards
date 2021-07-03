package es.uam.eps.dadm.juanmartin.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import es.uam.eps.dadm.juanmartin.cards.databinding.FragmentDeckListBinding
import es.uam.eps.dadm.juanmartin.cards.database.CardDatabase
import java.util.concurrent.Executors
import androidx.lifecycle.Observer
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

private const val DATABASENAME = "mazos"

class DeckListFragment : Fragment() {
    private lateinit var binding: FragmentDeckListBinding

    private lateinit var adapter: DeckAdapter
    private val executor = Executors.newSingleThreadExecutor()

    private var reference = FirebaseDatabase
        .getInstance()
        .getReference(DATABASENAME)

    private val deckListViewModel by lazy {
        ViewModelProvider(this).get(DeckListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_deck_list,
            container,
            false
        )
        adapter = DeckAdapter()
        //adapter.data = emptyList()
        //adapter.data = CardsApplication.decks
        binding.deckRecyclerView.adapter = adapter


        binding.newDeckFab.setOnClickListener {
            //var lastId: Long = deckListViewModel.higherId.value ?: 0
            //lastId++

            var lastId: Long = 0

            if(deckListViewModel.allDecks.value!=null){
                for (deck in deckListViewModel.allDecks.value!!){
                    if (deck.deckId >= lastId){
                        lastId = deck.deckId + 1
                    }
                }
            }

            val name = "Mazo $lastId"
            val deck = Deck(lastId, name, userId = Firebase.auth.currentUser!!.uid)
            //CardsApplication.addDeck(deck)

            //CardsApplication.currentDeck = deck
            CardsApplication.coCurrentDeck = lastId

            executor.execute{
                val cardDatabase = CardDatabase.getInstance(context = deckListViewModel.getApplication())
                cardDatabase.cardDao.addDeck(deck)
            }

            it.findNavController().navigate(
                DeckListFragmentDirections
                    .actionDeckListFragmentToDeckEditFragment()
            )

        }

        deckListViewModel.decks.observe(
            viewLifecycleOwner,
            Observer {
                adapter.data = it
                adapter.notifyDataSetChanged()
            })

        deckListViewModel.allDecks.observe(
            viewLifecycleOwner,
            Observer {
                adapter.notifyDataSetChanged()
            })


        return binding.root
    }
}