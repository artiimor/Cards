package es.uam.eps.dadm.juanmartin.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import es.uam.eps.dadm.juanmartin.cards.databinding.FragmentStudyBinding

class StudyFragment : Fragment() {
    lateinit var binding: FragmentStudyBinding

    private val viewModel: StudyViewModel by lazy{
        ViewModelProvider(this).get(StudyViewModel::class.java)
    }

    private var listener = View.OnClickListener { v ->
        val quality = when (v?.id) {
            R.id.easy_button -> 5
            R.id.doubt_button -> 3
            R.id.difficult_button -> 0
            else -> throw Exception("Unknown quality")
        }
        viewModel.update(quality)
        if (viewModel.card == null) {
            Toast.makeText(
                activity,
                resources.getString(R.string.no_more_cards),
                Toast.LENGTH_LONG
            ).show()
        }
        binding.invalidateAll()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentStudyBinding>(
            inflater,
            R.layout.fragment_study,
            container,
            false)

        binding.studyViewModel = viewModel
        viewModel.loadDeckId(CardsApplication.coCurrentDeck)
        binding.answerButton.setOnClickListener {
            viewModel.card?.answered = true
            binding.invalidateAll()
        }

        binding.difficultButton.setOnClickListener {
            viewModel.update(0)

            if (viewModel.card == null){
                Toast.makeText(this.activity, "No quedan tarjetas", Toast.LENGTH_LONG).show()
            }
            binding.invalidateAll()
        }

        binding.doubtButton.setOnClickListener {
            viewModel.update(3)

            if (viewModel.card == null){
                Toast.makeText(this.activity, "No quedan tarjetas", Toast.LENGTH_LONG).show()
            }
            binding.invalidateAll()
        }

        binding.easyButton.setOnClickListener {
            viewModel.update(5)

            if (viewModel.card == null){
                Toast.makeText(this.activity, "No quedan tarjetas", Toast.LENGTH_LONG).show()
            }
            binding.invalidateAll()
        }

        val observerLeft = Observer<Int> {
            viewModel.left = it
            binding.invalidateAll()
        }
        viewModel.cardsLeft.observe(viewLifecycleOwner, observerLeft)

        val observer = Observer<Card?> {
            viewModel.card = it
            binding.invalidateAll()
        }
        viewModel.dueCard.observe(viewLifecycleOwner, observer)

        if (!SettingsActivity.getBoardOption(viewModel.context)){
            binding.board.visibility = View.GONE
        }

        return binding.root
    }


}