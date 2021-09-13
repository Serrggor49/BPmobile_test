package goryunov.bpmobile_test.presentation.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import goryunov.bet.bpmobile_test.R
import kotlinx.android.synthetic.main.activity_profile.*
import goryunov.bpmobile_test.MyApplication.Companion.hideKeyboard
import goryunov.bpmobile_test.data.RepositoryImpl
import goryunov.bpmobile_test.presentation.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    @Inject
    lateinit var repositoryImpl: RepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()
        init()
    }

    @SuppressLint("SetTextI18n")
    fun init() {
        val viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.init()
        viewModel.liveData.observe(this) { user ->
            nameUserId.text = ("Login: ${user.name}")
            imageProfileId.setImageResource(user.imageProfile)
        }

        buttonExitId.setOnClickListener {
            finish()
        }


        viewModel.liveDataNewPasswordAnswer.observe(this) {
            Snackbar.make(constraintLayout, it, Snackbar.LENGTH_LONG).show();
        }

        buttonSaveNewPassword.setOnClickListener {
            hideKeyboard()

            CoroutineScope(Dispatchers.Main).launch {
                showProgress(true)
                delay(2000)
                showProgress(false)

                withContext(Dispatchers.Main) {
                    viewModel.refreshPassword(
                        oldPasswordFieldId.text.toString(),
                        newPasswordFieldId.text.toString(),
                        confirmPasswordFieldId.text.toString()
                    )
                }
            }

        }
    }

    fun showProgress(show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
            textInputLayout.visibility = View.INVISIBLE
            textInputLayout2.visibility = View.INVISIBLE
            textInputLayout3.visibility = View.INVISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
            textInputLayout.visibility = View.VISIBLE
            textInputLayout2.visibility = View.VISIBLE
            textInputLayout3.visibility = View.VISIBLE

        }

    }

}