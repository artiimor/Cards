package es.uam.eps.dadm.juanmartin.cards

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.google.firebase.database.FirebaseDatabase
import es.uam.eps.dadm.juanmartin.cards.database.CardDatabase
import java.time.LocalDateTime
import java.util.concurrent.Executors


class StudyViewModel(application: Application) : AndroidViewModel(application) {
    /*
    private val _cardsLeft = MutableLiveData<Int>()
    val cardsLeft: LiveData<Int>
        get() = _cardsLeft

    private val _difficultCards = MutableLiveData<Int>()
    val difficultCards: LiveData<Int>
        get() = _difficultCards

    private val _doubtCards = MutableLiveData<Int>()
    val doubtCards: LiveData<Int>
        get() = _doubtCards

    private val _easyCards = MutableLiveData<Int>()
    val easyCards: LiveData<Int>
        get() = _easyCards

    init {
        cards = CardsApplication.decks[0].cards
        card = random_card()
        CardsApplication.cardsLeft = cards.size
        _cardsLeft.value = cards.size
        _difficultCards.value = 0
        _doubtCards.value = 0
        _easyCards.value = 0
    }

    fun update(quality: Int) {
        card?.quality = quality
        card?.update(LocalDateTime.now())
        card = random_card()
        _cardsLeft.value = cardsLeft.value?.minus(1)
        //CardsApplication.cardsLeft = _cardsLeft.value!!
        when(quality){
            0 -> _difficultCards.value = difficultCards.value?.plus(1)
            3 -> _doubtCards.value = doubtCards.value?.plus(1)
            5 -> _easyCards.value = easyCards.value?.plus(1)
        }
    }

    private fun random_card() = try {
        cards.filter { card ->
            card.isDue(LocalDateTime.now())
        }.random()
    } catch (e: NoSuchElementException) {
        null
    }*/

    private val executor = Executors.newSingleThreadExecutor()
    @SuppressLint("StaticFieldLeak")
    public val context = getApplication<Application>().applicationContext
    var left: Int? = null
    var card: Card? = null
    private val deckId = MutableLiveData<Long>()
    //var cards: LiveData<List<Card>> = CardDatabase.getInstance(context).cardDao.getCards()
    val cards: LiveData<List<Card>> = Transformations.switchMap(deckId) {
        CardDatabase.getInstance(CardsApplication()).cardDao.getCardsOfDeck(CardsApplication.coCurrentDeck)
    }

    var cardsLeft: LiveData<Int> =
        Transformations.map(cards, ::left)

    var dueCard: LiveData<Card?> =
        Transformations.map(cards, ::due)

    private val _difficultCards = MutableLiveData<Int>()
    val difficultCards: LiveData<Int>
        get() = _difficultCards

    private val _doubtCards = MutableLiveData<Int>()
    val doubtCards: LiveData<Int>
        get() = _doubtCards

    private val _easyCards = MutableLiveData<Int>()
    val easyCards: LiveData<Int>
        get() = _easyCards

    private fun due(cards: List<Card>) = try {
        cards.filter { card -> card.isDue(LocalDateTime.now()) }.random()
    } catch (e: Exception) {
        null
    }

    fun loadDeckId(id: Long) {
        deckId.value = id
    }

    private fun left(cards: List<Card>) =
        cards.filter { card -> card.isDue(LocalDateTime.now()) }.size

    fun update(quality: Int) {
        card?.quality = quality
        card?.answered = false
        card?.update(LocalDateTime.now())

        executor.execute {
            CardDatabase.getInstance(CardsApplication()).cardDao.update(card!!)
        }

        val reference = FirebaseDatabase.getInstance().getReference("tarjetas")
        card?.let { reference.child(it.id).setValue(card) }

        when(quality){
            0 -> _difficultCards.value = difficultCards.value?.plus(1)
            3 -> _doubtCards.value = doubtCards.value?.plus(1)
            5 -> _easyCards.value = easyCards.value?.plus(1)
        }
    }

    init {
        _difficultCards.value = 0
        _doubtCards.value = 0
        _easyCards.value = 0
    }
}
