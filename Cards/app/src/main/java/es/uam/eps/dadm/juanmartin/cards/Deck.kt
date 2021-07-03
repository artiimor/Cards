package es.uam.eps.dadm.juanmartin.cards

import Cloze
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "decks_table")
open class Deck(
    @PrimaryKey var deckId: Long,
    var name: String,
    val userId: String = " "

) {
    //var cards = mutableListOf<Card>()
    /*
    fun addCard() {
        println("Añadiendo tarjeta al mazo ${name}: ")

        println("Teclea el tipo (0 -> Card 1 -> Cloze): ")
        var tipo = readLine()?.toInt()
        if (tipo !in 0..1) {
            println("Tipo escogido no válido.")
            return
        }

        println("  Teclea la pregunta: ")
        var pregunta = readLine()?.toString()
        println("  Teclea la respuesta: ")
        var respuesta = readLine()?.toString()

        if (pregunta != null && respuesta != null) {
            if (tipo == 0)
                cards.add(Card(pregunta, respuesta, deckId = deckId))
            else if (tipo == 1)
                cards.add(Cloze(pregunta, respuesta))

            println("Tarjeta añadida correctamente.")
        }
    }

    fun listCards() {
        var cont = 1
        cards.forEach { carta: Card ->
            println("$cont- ${carta.question} -> ${carta.answer}")
            cont++
        }
    }

    fun simulate(period: Int) {
        val TS_DATE_PATTERN = "dd-MM-yyyy"
        val formatter = DateTimeFormatter.ofPattern(TS_DATE_PATTERN)
        println("Simulación del mazo $name:")
        var now = LocalDateTime.now()

        println("Fecha: ${now.format(formatter)}")
        cards.forEach { carta: Card ->
            carta.show()
            carta.update(now)
            carta.details()
        }
        now = now.plusDays(1)
        for (i in 2..period) {
            println("Fecha: ${now.format(formatter)}")
            cards.forEach { carta: Card ->
                if (now.toString().compareTo(carta.nextPracticeDate) == 0) {
                    carta.show()
                    carta.update(now)
                    carta.details()
                }
            }
            now = now.plusDays(1)
        }
    }

    fun writeCards(name: String) {
        var file = File(name)
        var cont = 0

        file.createNewFile()

        cards.forEach { carta ->
            if (cont == 0) {
                File(name).writeText(carta.toString())
                cont++
            } else
                File(name).appendText(carta.toString())
        }
    }

    /*fun readCards(name: String) {
        try {
            var lineas = File(name).readLines()
            for (linea in lineas) {
                var campos = linea.split("|")
                if (campos[0] == "card") {
                    cards.add(Card.fromString(linea))

                } else if (campos[0] == "cloze") {
                    cards.add(Cloze.fromString(linea))
                }
            }
            println("Tarjetas añadidas correctamente.")
        } catch (e: Exception) {
            println("Error de lectura del fichero")
        }
    }*/

    fun deleteCard(numT: Int) {
        var indice = numT - 1

        if (cards.remove(cards[indice]))
            println("La carta numero $numT ha sido eliminada correctamente.")

    }

    fun add(card: Card) {
        cards.add(card)
    }

     */
    constructor() : this(
        0,
        "deck"
    )
}