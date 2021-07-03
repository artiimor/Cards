package es.uam.eps.dadm.juanmartin.cards

import android.app.Application
import es.uam.eps.dadm.juanmartin.cards.database.CardDatabase
import java.util.concurrent.Executors

class CardsApplication : Application() {
    //var card: Card? = null
    //var cards: MutableList<Card> = mutableListOf<Card>()
    private val executor = Executors.newSingleThreadExecutor()
    var currentDeckId: Long = 1

    override fun onCreate() {
        super.onCreate()

        val cardDatabase = CardDatabase.getInstance(context = this)
        //CardsApplication.currentDeck = Deck(0, "Default")

        /*
        executor.execute {
            cardDatabase.cardDao.addDeck(Deck(1, "Francés"))
            cardDatabase.cardDao.addCard(Card("Se reveiller", "Levantarse", deckId = 1))
            cardDatabase.cardDao.addCard(Card("Prendre", "Recoger", deckId = 1))
            cardDatabase.cardDao.addCard(Card("Donner", "Ceder", deckId = 1))

            cardDatabase.cardDao.addDeck(Deck(2, "Inglés"))
            cardDatabase.cardDao.addCard(Card("To wake up", "Levantarse", deckId = 2))
        }*/

    }

    init {

    }


    companion object {
       // var decks: MutableList<Deck> = mutableListOf<Deck>()
       // lateinit var currentDeck: Deck
        var coCurrentDeck: Long = -1

        /*
        fun numberOfCardsLeft(): Int {
            return cardsLeft
        }

        fun getCard(id: String): Card {
            return decks[0].cards.filter { it.id == id }[0]
        }
        fun addCard(card: Card) {
            decks[0].cards.add(card)
        }
        fun deleteCard(id: String) {
            decks[0].cards.remove(getCard(id))
        }


        fun addDeck(deck: Deck) {
            decks.add(deck)
        }
        */

        //var cardsLeft: Int = 1
    }

}
