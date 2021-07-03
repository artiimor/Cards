package es.uam.eps.dadm.juanmartin.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import es.uam.eps.dadm.juanmartin.cards.database.CardDatabase
import es.uam.eps.dadm.juanmartin.cards.databinding.ListItemDeckBinding
import java.util.concurrent.Executors

class DeckAdapter() : RecyclerView.Adapter<DeckAdapter.DeckHolder>() {
    private val executor = Executors.newSingleThreadExecutor()
    lateinit var binding: ListItemDeckBinding
    var referenceDecks = FirebaseDatabase.getInstance().getReference("mazos")
    var referenceCards = FirebaseDatabase.getInstance().getReference("tarjetas")

    var data = listOf<Deck>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class DeckHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var deck: Deck

        fun bind(deck: Deck) {
            this.deck = deck
            binding.deck = deck
        }

        init {
            binding.listItemQuestion.setOnClickListener {
                val id = deck.deckId
                CardsApplication.coCurrentDeck=  deck.deckId
                it.findNavController()
                    .navigate(DeckListFragmentDirections.
                    actionDeckListFragmentToCardListFragment(id))
            }

            binding.deleteButton.setOnClickListener{
                executor.execute{
                    val cardDatabase = CardDatabase.getInstance(context = CardsApplication())
                    val cards = cardDatabase.cardDao.getCardsOfDeckRemove(deck.deckId)

                    for (card in cards){
                        cardDatabase.cardDao.deleteCard(card)
                        referenceCards.child(card.id).removeValue()
                    }

                    cardDatabase.cardDao.deleteDeck(deck)
                    referenceDecks.child(deck.deckId.toString()).removeValue()
                }


                it.findNavController()
                    .navigate(
                        DeckListFragmentDirections
                            .actionDeckListFragmentSelf()
                    )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeckHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ListItemDeckBinding.inflate(layoutInflater, parent, false)
        return DeckHolder(binding.root)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: DeckHolder, position: Int) {
        holder.bind(data[position])
    }
}