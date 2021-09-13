package goryunov.bpmobile_test.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_registration.*
import goryunov.bpmobile_test.MyApplication.Companion.hideKeyboard
import goryunov.bet.bpmobile_test.R
import goryunov.bpmobile_test.presentation.viewmodel.RegistrationViewModel
import javax.inject.Inject


@AndroidEntryPoint
class RegistrationActivity @Inject constructor() : AppCompatActivity() {

    lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.hide()

        init()
    }

    fun init() {
        registrationViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)


        registrationViewModel.liveData.observe(this){answer ->
            if (answer == "OK"){
                openProfile()
            }
            else Snackbar.make(linearLayout, answer, Snackbar.LENGTH_LONG).show()
        }

        buttonRegistrationId.setOnClickListener {
            hideKeyboard()

            registrationViewModel.registerUser(
                nameFieldId.text.toString(),
                passwordFieldId.text.toString(),
                getRandomImage(),
                confirmFieldId.text.toString()
            )


        }

    }

    fun openProfile(){
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_in_up)
        finish()

    }

    fun getRandomImage(): Int{
        val i = listOf(R.drawable.i1, R.drawable.i2, R.drawable.i3 ).random()
        return i
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left2, R.anim.slide_in_right2)
    }
}