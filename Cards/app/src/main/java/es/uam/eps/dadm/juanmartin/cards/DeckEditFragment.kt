package es.uam.eps.dadm.juanmartin.cards

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.google.firebase.database.FirebaseDatabase
import es.uam.eps.dadm.juanmartin.cards.database.CardDatabase
import es.uam.eps.dadm.juanmartin.cards.databinding.FragmentDeckEditBinding
import timber.log.Timber
import java.util.concurrent.Executors

class DeckEditFragment : Fragment() {
    lateinit var binding: FragmentDeckEditBinding
    lateinit var deck: Deck
    private val executor = Executors.newSingleThreadExecutor()

    var reference = FirebaseDatabase.getInstance().getReference("mazos")

    private val viewModel by lazy {
        ViewModelProvider(this).get(DeckEditViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_deck_edit,
            container,
            false
        )

        viewModel.loadDeckId(CardsApplication.coCurrentDeck)
        viewModel.deck.observe(viewLifecycleOwner) {
            deck = it
            binding.deck = deck
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Timber.i("Onstart")
        val deckEditNameField = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                deck.name = s.toString()
            }
        }

        // Añade el escuchador a deckEditNameField
        binding.deckEditNameField.addTextChangedListener(deckEditNameField)

        binding.acceptCardEditButton.setOnClickListener {
            Timber.i("Antes execute")
            executor.execute{
                val cardDatabase = CardDatabase.getInstance(context = viewModel.getApplication())
                cardDatabase.cardDao.updateDeck(deck.deckId, deck.name)
            }
            Timber.i("Antes reference")
            reference.child(deck.deckId.toString()).setValue(deck)
            Timber.i("Despues reference")
            view?.findNavController()?.navigate(R.id.action_deckEditFragment_to_deckListFragment)
        }
    }
}