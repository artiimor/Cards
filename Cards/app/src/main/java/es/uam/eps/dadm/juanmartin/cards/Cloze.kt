import es.uam.eps.dadm.juanmartin.cards.Card

class Cloze(question: String, answer: String) : Card(question, answer) {
    override fun show() {
        println("${this.question} (INTRO para ver respuesta)")
        readLine()
        val dividida = question.split("*")
        println("${dividida[0]} ${this.answer} ${dividida[2]} (Teclea 0 -> Difícil 3 -> Dudo 5 -> Fácil): ")
        var dificultad: Int? = readLine()?.toIntOrNull()
        if (dificultad == null)
            dificultad = 0
        this.quality = dificultad
    }

    override fun toString(): String =
        "cloze|$question|$answer|$date|$id|$quality|$repetitions|$interval|$easiness|$nextPracticeDate\n"

    companion object {
        fun fromString(cad: String): Cloze {
            var campos = cad.split("|")
            var carta = Cloze(campos[1], campos[2])

            carta.quality = campos[5].toInt()
            carta.repetitions = campos[6].toInt()
            carta.interval = campos[7].toLong()
            carta.nextPracticeDate = campos[9]
            carta.easiness = campos[8].toDouble()
            carta.date = campos[3]
            carta.id = campos[4]

            return carta
        }
    }
}