package es.uam.eps.dadm.juanmartin.cards

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import es.uam.eps.dadm.juanmartin.cards.database.CardDatabase

class StatisticsViewModel(application: Application): AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

   val decks: LiveData<List<Deck>> = CardDatabase.getInstance(CardsApplication()).cardDao.getDecks()

    val cards: LiveData<List<Card>> = CardDatabase.getInstance(CardsApplication()).cardDao.getCards()


    /*@RequiresApi(Build.VERSION_CODES.O)
    fun getTotalDecks(): Int? {
        Timber.i(cards.value.toString())
        return 2
    }*/

    //var decks: LiveData<List<DeckWithCards>> = CardDatabase.getInstance(context).cardDao.getDecksWithCards()

    fun getTotalDecks(): Int {
        return CardDatabase.getInstance(CardsApplication()).cardDao.nTotalDecks()
    }


    fun getTotalCards(): Int {
        return CardDatabase.getInstance(CardsApplication()).cardDao.nTotalCards()
    }



}