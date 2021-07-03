package es.uam.eps.dadm.juanmartin.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import es.uam.eps.dadm.juanmartin.cards.database.CardDatabase
import es.uam.eps.dadm.juanmartin.cards.databinding.ListItemCardBinding
import java.util.concurrent.Executors


class CardAdapter() : RecyclerView.Adapter<CardAdapter.CardHolder>() {
    private val executor = Executors.newSingleThreadExecutor()
    lateinit var binding: ListItemCardBinding
    var referenceCards = FirebaseDatabase.getInstance().getReference("tarjetas")

    var data = listOf<Card>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class CardHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var card: Card

        fun bind(card: Card) {
            this.card = card
            binding.card = card
        }

        init {
            binding.listItemQuestion.setOnClickListener {
                val id = card.id
                it.findNavController()
                    .navigate(
                        CardListFragmentDirections
                            .actionCardListFragmentToCardEditFragment(id)
                    )
            }

            binding.info.setOnClickListener{
                binding.card?.showInfo(view)
            }

            binding.deleteButton.setOnClickListener{
                executor.execute{
                    val cardDatabase = CardDatabase.getInstance(context = CardsApplication())
                    cardDatabase.cardDao.deleteCard(card)
                }
                referenceCards.child(card.id).removeValue()
                it.findNavController()
                    .navigate(
                        CardListFragmentDirections
                            .actionCardListFragmentToDeckListFragment()
                    )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ListItemCardBinding.inflate(layoutInflater, parent, false)

        return CardHolder(binding.root)
    }


    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(data[position])
    }

}






