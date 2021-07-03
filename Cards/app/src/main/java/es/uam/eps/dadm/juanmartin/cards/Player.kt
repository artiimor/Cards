package es.uam.eps.dadm.juanmartin.cards
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

class Player(lifecycle: Lifecycle) : LifecycleObserver {
    init {
        lifecycle.addObserver(this)
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        Timber.i("player started")
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        Timber.i("player stopped")
    }
}

