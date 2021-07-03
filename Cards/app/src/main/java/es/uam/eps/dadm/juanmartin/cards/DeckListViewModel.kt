package es.uam.eps.dadm.juanmartin.cards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import es.uam.eps.dadm.juanmartin.cards.database.CardDatabase

class DeckListViewModel(application: Application)
    : AndroidViewModel(application) {

    val decks: LiveData<List<Deck>> = CardDatabase.getInstance(CardsApplication()).cardDao.getDecks()
    val allDecks: LiveData<List<Deck>> = CardDatabase.getInstance(CardsApplication()).cardDao.getAllDecks()
    val higherId: LiveData<Long> = CardDatabase.getInstance(CardsApplication()).cardDao.getHigherId()
    //var decks: LiveData<List<DeckWithCards>> = CardDatabase.getInstance(context).cardDao.getDecksWithCards()
}