package es.uam.eps.dadm.juanmartin.cards

import android.view.View
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToLong

@Entity(tableName = "cards_table")
open class Card(
    @ColumnInfo(name = "card_question")
    var question: String,
    var answer: String,
    var date: String = LocalDateTime.now().toString(),
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var deckId: Long = 0,
    var userId: String = " ",
    var quality: Int = 0,
    var repetitions: Int = 0,
    var interval: Long = 1L,
    var nextPracticeDate: String = LocalDateTime.now().toString(),
    var easiness: Double = 2.5,
    var answered: Boolean = false
) {

    constructor() : this(
        "Pregunta",
        "Respuesta",
        LocalDateTime.now().toString(),
        UUID.randomUUID().toString()
    )

    open fun show() {
        println("$question (INTRO para ver respuesta)")
        readLine()
        println("$answer (Teclea 0 -> Difícil 3 -> Dudo 5 -> Fácil): ")
        var dificultad: Int? = readLine()?.toIntOrNull()
        if (dificultad == null)
            dificultad = 0
        quality = dificultad
    }

    fun update(currentDate: LocalDateTime) {
        easiness = maxOf(1.3, (easiness + 0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02)))

        repetitions = if (quality < 3) 0 else repetitions + 1

        interval = if (repetitions <= 1) 1L else if (repetitions == 2) 6 else (interval * easiness).roundToLong()

        nextPracticeDate = currentDate.plusDays(interval).toString()
    }

    private fun details() {
        val tsDatePattern = "dd-MM-yyyy"
        val formatter = DateTimeFormatter.ofPattern(tsDatePattern)
        println(
            "eas = ${
                String.format(
                    "%.2f",
                    easiness
                )
            } rep = $repetitions int = $interval nPD = ${nextPracticeDate.format(formatter)}"
        )
    }

    fun simulate(period: Long) {
        val tsDatePattern = "dd-MM-yyyy"
        val formatter = DateTimeFormatter.ofPattern(tsDatePattern)
        println("Simulación de la tarjeta $question:")
        var now = LocalDateTime.now()

        println("Fecha: ${now.format(formatter)}")
        show()
        update(now)
        details()
        now = now.plusDays(1)
        for (i in 2..period) {
            println("Fecha: ${now.format(formatter)}")
            if (now.toString().compareTo(nextPracticeDate) == 0) {
                show()
                update(now)
                details()
            }
            now = now.plusDays(1)
        }
    }

    override fun toString(): String =
        "card|$question|$answer|$date|$id|$quality|$repetitions|$interval|$easiness|$nextPracticeDate\n"

    /*companion object {
        fun fromString(cad: String): Card {
            var campos = cad.split("|")
            var carta = Card(
                campos[1],
                campos[2],
                campos[3],
                campos[4]
            )

            return carta
        }
    }*/

    fun update_easy() {
        quality = 5
        update(LocalDateTime.now())
    }

    fun update_doubt() {
        quality = 3
        update(LocalDateTime.now())
    }

    fun update_difficult() {
        quality = 0
        update(LocalDateTime.now())
    }

    fun isDue(fecha:LocalDateTime): Boolean{

        return fecha.toString() >= nextPracticeDate
    }

    fun showInfo(view: View){
        var v = view.findViewById<View>(R.id.list_item_easiness)
        v.visibility = if (v.visibility == View.VISIBLE) View.GONE else View.VISIBLE

        v = view.findViewById<View>(R.id.list_item_quality)
        v.visibility = if (v.visibility == View.VISIBLE) View.GONE else View.VISIBLE

        v = view.findViewById<View>(R.id.delete_button)
        v.visibility = if (v.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }
}
