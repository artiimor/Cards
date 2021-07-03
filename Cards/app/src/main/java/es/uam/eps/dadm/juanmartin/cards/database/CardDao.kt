package es.uam.eps.dadm.juanmartin.cards.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.uam.eps.dadm.juanmartin.cards.Card
import es.uam.eps.dadm.juanmartin.cards.Deck
import es.uam.eps.dadm.juanmartin.cards.DeckWithCards

@Dao
interface CardDao {
    @Query("SELECT * FROM cards_table WHERE userId = :userId")
    fun getCards(userId:String = Firebase.auth.currentUser!!.uid): LiveData<List<Card>>

    @Query("SELECT * FROM cards_table WHERE id = :id AND userId = :userId")
    fun getCard(id: String, userId: String = Firebase.auth.currentUser!!.uid): LiveData<Card>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCard(card: Card)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeck(deck: Deck)

    @Update
    fun update(card: Card)

    @Transaction
    @Query("SELECT * FROM decks_table")
    fun getDecksWithCards(): LiveData<List<DeckWithCards>>

    @Transaction
    @Query("SELECT * FROM decks_table WHERE deckId = :deckId")
    fun getDeckWithCards(deckId: Long): LiveData<List<DeckWithCards>>

    @Query("SELECT * FROM decks_table WHERE userId = :userId")
    fun getDecks(userId: String = Firebase.auth.currentUser!!.uid): LiveData<List<Deck>>

    @Query("SELECT * FROM decks_table")
    fun getAllDecks(): LiveData<List<Deck>>

    @Query("SELECT * FROM cards_table WHERE deckId = :id AND userID = :userId")
    fun getCardsOfDeck(id: Long, userId: String = Firebase.auth.currentUser!!.uid): LiveData<List<Card>>

    @Query("SELECT * FROM cards_table WHERE deckId = :id AND userID = :userId")
    fun getCardsOfDeckRemove(id: Long, userId: String = Firebase.auth.currentUser!!.uid): List<Card>

    @Query("SELECT deckId FROM decks_table ORDER BY deckId DESC LIMIT 1")
    fun getHigherId(): LiveData<Long>

    @Query("UPDATE cards_table SET card_question = :question, answer = :answer WHERE id = :id")
    fun updateCard(id: String, question: String, answer: String): Int

    @Query("SELECT * FROM decks_table WHERE deckId = :id AND userId = :userId")
    fun getDeck(id: Long, userId: String = Firebase.auth.currentUser!!.uid): LiveData<Deck>

    @Query("UPDATE decks_table SET name = :name WHERE deckId = :id")
    fun updateDeck(id: Long, name: String): Int

    @Query("SELECT COUNT(id) FROM cards_table")
    fun nTotalCards(): Int

    @Query("SELECT COUNT(deckId) FROM decks_table")
    fun nTotalDecks(): Int

    @Delete
    fun deleteCard(card: Card)

    @Delete
    fun deleteDeck(deck: Deck)
}
