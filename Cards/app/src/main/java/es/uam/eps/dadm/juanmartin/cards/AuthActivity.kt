package es.uam.eps.dadm.juanmartin.cards

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import es.uam.eps.dadm.juanmartin.cards.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity(){

    lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)

        binding.RegisterButton.setOnClickListener{
            if (binding.CorreoEditText.text.isNotEmpty() && binding.ContrasenhaEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.CorreoEditText.text.toString(),
                    binding.ContrasenhaEditText.text.toString()).addOnCompleteListener{
                        if(it.isSuccessful){
                            startActivity(Intent(this, TitleActivity::class.java))
                        }else{
                            Toast.makeText(this, R.string.error_new_user, Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }

        binding.LoginButton.setOnClickListener{
            if (binding.CorreoEditText.text.isNotEmpty() && binding.ContrasenhaEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.CorreoEditText.text.toString(),
                    binding.ContrasenhaEditText.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        startActivity(Intent(this, TitleActivity::class.java))
                    }else{
                        Toast.makeText(this, R.string.error_new_user, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}