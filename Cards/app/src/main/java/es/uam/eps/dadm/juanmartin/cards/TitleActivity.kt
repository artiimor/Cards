package es.uam.eps.dadm.juanmartin.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.database.FirebaseDatabase
import es.uam.eps.dadm.juanmartin.cards.databinding.ActivityTitleBinding
import timber.log.Timber

class TitleActivity : AppCompatActivity(){

    lateinit var binding: ActivityTitleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_title)

        Timber.i("ActivityTitle")
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference = database.getReference("mensaje")
        reference.setValue("Hola desde Cards")

        /*reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                Timber.i(reference.toString())
                Toast.makeText(
                    baseContext,
                    "${database.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })*/

        NavigationUI.setupWithNavController(
            binding.navView,
            findNavController(R.id.navHostFragment))
    }
}