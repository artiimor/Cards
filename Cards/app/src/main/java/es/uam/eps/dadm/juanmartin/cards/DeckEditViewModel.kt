package es.uam.eps.dadm.juanmartin.cards

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import es.uam.eps.dadm.juanmartin.cards.database.CardDatabase

class DeckEditViewModel (application: Application): AndroidViewModel(application){

    @SuppressLint("StaticFieldLeak")
    val context = getApplication<Application>().applicationContext

    private val deckId = MutableLiveData<Long>()

    val deck: LiveData<Deck> = Transformations.switchMap(deckId) {
        CardDatabase.getInstance(CardsApplication()).cardDao.getDeck(it) // RTO
    }

    fun loadDeckId(id: Long) {
        deckId.value = id
    }

}