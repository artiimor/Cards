package es.uam.eps.dadm.juanmartin.cards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import es.uam.eps.dadm.juanmartin.cards.database.CardDatabase

class CardListViewModel(application: Application)
    : AndroidViewModel(application) {
    /*
    private val context = getApplication<Application>().applicationContext
    val cards: LiveData<List<Card>> = CardDatabase.getInstance(context).cardDao.getCards()*/

    private val deckId = MutableLiveData<Long>()
    private val context = getApplication<Application>().applicationContext

    val cards: LiveData<List<Card>> = Transformations.switchMap(deckId) {
        CardDatabase.getInstance(CardsApplication()).cardDao.getCardsOfDeck(CardsApplication.coCurrentDeck)
    }

    fun loadDeckId(id: Long) {
        deckId.value = id
    }
}