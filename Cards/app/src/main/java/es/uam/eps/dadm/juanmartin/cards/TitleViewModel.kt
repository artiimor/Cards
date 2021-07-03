package es.uam.eps.dadm.juanmartin.cards

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import es.uam.eps.dadm.juanmartin.cards.database.CardDatabase
import timber.log.Timber
import java.util.concurrent.Executors

class TitleViewModel: ViewModel() {
    private val executor = Executors.newSingleThreadExecutor()
    var mazosFirebase = FirebaseDatabase.getInstance().getReference("mazos")
    var tarjetasFirebase = FirebaseDatabase.getInstance().getReference("tarjetas")

    init {
        Timber.i("Init")
        tarjetasFirebase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (card in snapshot.children) {
                    Timber.i("For")
                    val cartaNueva = card.getValue(Card::class.java)

                    if (cartaNueva != null && cartaNueva.userId == Firebase.auth.currentUser!!.uid)
                        executor.execute {
                            val cardDatabase = CardDatabase.getInstance(context = CardsApplication())
                            cardDatabase.cardDao.addCard(cartaNueva)
                        }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                //
            }
        })

        mazosFirebase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (deck in snapshot.children) {
                    val deckNuevo = deck.getValue(Deck::class.java)

                    if (deckNuevo != null && deckNuevo.userId == Firebase.auth.currentUser!!.uid)
                        executor.execute {
                            val cardDatabase = CardDatabase.getInstance(context = CardsApplication())
                            cardDatabase.cardDao.addDeck(deckNuevo)
                        }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                //
            }
        })
    }
}
