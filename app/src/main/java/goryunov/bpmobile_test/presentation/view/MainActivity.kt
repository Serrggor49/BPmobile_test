package goryunov.bpmobile_test.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import goryunov.bet.bpmobile_test.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.coroutines.*
import goryunov.bpmobile_test.MyApplication.Companion.hideKeyboard
import goryunov.bpmobile_test.presentation.viewmodel.ViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        init()
    }

    fun init() {
        val viewModel = ViewModelProvider(this)[ViewModel::class.java]

        createButtonId.setOnClickListener {
            hideKeyboard()
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
        }



        viewModel.liveDataAnswer.observe(this) { answer ->
            if (answer == "OK") startProfileActivity()
            else Snackbar.make(constraintLayoutId, answer, Snackbar.LENGTH_LONG).show()
        }

        buttonLogIn.setOnClickListener {
            hideKeyboard()

            // progress bar
            CoroutineScope(Dispatchers.Main).launch {
                showProgress(true)
                delay(2000)
                showProgress(false)

                withContext(Dispatchers.Main) {
                    viewModel.logInFlow(loginId.text.toString(), passwordId.text.toString())
                }
            }

        }

    }

    fun showProgress(show: Boolean) {
        if (show) {
            progressBarId.visibility = View.VISIBLE
            mainLayoutId.visibility = View.GONE
        } else {
            progressBarId.visibility = View.GONE
            mainLayoutId.visibility = View.VISIBLE
        }

    }


    fun startProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_in_up)
    }

}