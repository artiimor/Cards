package es.uam.eps.dadm.juanmartin.cards

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import es.uam.eps.dadm.juanmartin.cards.databinding.FragmentCardEditBinding
import es.uam.eps.dadm.juanmartin.cards.database.CardDatabase
import java.util.concurrent.Executors

class CardEditFragment : Fragment() {
    private val executor = Executors.newSingleThreadExecutor()
    var reference = FirebaseDatabase.getInstance().getReference("tarjetas")

    lateinit var binding: FragmentCardEditBinding
    lateinit var card: Card
    lateinit var question: String
    lateinit var answer: String

    var deckId: Long = 0

    private val cardEditviewModel by lazy {
        ViewModelProvider(this).get(CardEditViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_card_edit,
            container,
            false
        )

        val args = CardEditFragmentArgs.fromBundle(requireArguments())

        cardEditviewModel.loadCardId(args.cardId)
        deckId = CardsApplication.coCurrentDeck

        cardEditviewModel.card.observe(viewLifecycleOwner) {
            card = it
            binding.card = card
            question = card.question
            answer = card.answer
        }


        return binding.root
    }


    override fun onStart() {
        super.onStart()

        val questionTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                question = s.toString()
            }
        }

        val answerTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                answer = s.toString()
            }
        }

        binding.questionEditText.addTextChangedListener(questionTextWatcher)
        binding.answerEditText.addTextChangedListener(answerTextWatcher)

        binding.acceptCardEditButton.setOnClickListener {

            //if(card.question == "" || card.answer == ""){

            //}else{
            executor.execute{
                val cardDatabase = CardDatabase.getInstance(context = cardEditviewModel.getApplication())
                cardDatabase.cardDao.updateCard(card.id, question, answer)
            }
            //}

            //Para firebase
            card.answer = answer
            card.question = question
            card.userId = Firebase.auth.currentUser!!.uid
            reference.child(card.id).setValue(card)

            val bundle = bundleOf("deckId" to deckId)
            view?.findNavController()?.navigate(R.id.action_cardEditFragment_to_cardListFragment, bundle)
            //it.findNavController().navigate(CardEditFragmentDirections.actionCardEditFragmentToCardListFragment(card.deckId))
        }

        binding.cancelCardEditButton.setOnClickListener {

            //if (card.question == "" || card.answer == "")
                //CardsApplication.deleteCard(card.id)
            //it.findNavController().navigate(CardEditFragmentDirections.actionCardEditFragmentToCardListFragment(card.deckId))
            val bundle = bundleOf("deckId" to deckId)
            view?.findNavController()?.navigate(R.id.action_cardEditFragment_to_cardListFragment, bundle)
        }
    }
}