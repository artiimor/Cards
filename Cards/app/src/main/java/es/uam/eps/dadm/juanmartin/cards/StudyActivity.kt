package es.uam.eps.dadm.juanmartin.cards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import es.uam.eps.dadm.juanmartin.cards.databinding.ActivityStudyBinding

class StudyActivity : AppCompatActivity() {
    lateinit var binding: ActivityStudyBinding
    private val viewModel: StudyViewModel by lazy {
        ViewModelProvider(this).get(StudyViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_study)
        supportActionBar?.hide()
    }
}
