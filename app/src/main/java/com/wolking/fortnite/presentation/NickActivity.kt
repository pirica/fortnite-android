package com.wolking.fortnite.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.wolking.fortnite.R
import com.wolking.fortnite.databinding.ActivityNickBinding
import com.wolking.fortnite.presentation.cache.AppPreferences
import com.wolking.fortnite.presentation.home.viewmodel.HomeViewModel
import com.wolking.fortnite.utils.CountingIdlingResourceSingleton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NickActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityNickBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nick)

        binding.btSearch.setOnClickListener {
            val nick = binding.etNick.text.toString()
            if (TextUtils.isEmpty(nick)) {
                Toast.makeText(this, "Digita teu nick", Toast.LENGTH_SHORT).show()
            } else {
                CountingIdlingResourceSingleton.increment()
                search(nick)
            }
        }
    }

    private fun search(nick: String) {
        homeViewModel.getStats(nick)

        homeViewModel.stats.observe(this) {
            AppPreferences(this).setString("nick", binding.etNick.text.toString())
            goToMain()
        }

        homeViewModel.error.observe(this) {
            Toast.makeText(this, "Hum..., não encontrei você.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    override fun onDestroy() {
        super.onDestroy()
        CountingIdlingResourceSingleton.decrement()
    }
}