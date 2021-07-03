package es.uam.eps.dadm.juanmartin.cards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import es.uam.eps.dadm.juanmartin.cards.database.CardDatabase

class CardEditViewModel(application: Application): AndroidViewModel(application) {

    private val cardId = MutableLiveData<String>()

    val card: LiveData<Card> = Transformations.switchMap(cardId) {
        CardDatabase.getInstance(CardsApplication()).cardDao.getCard(it)
    }

    fun loadCardId(id: String) {
        cardId.value = id
    }
}

