package com.googlelabs.simulador.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.googlelabs.simulador.databinding.ActivityDetailBinding
import com.googlelabs.simulador.domain.Match

class DetailActivity : AppCompatActivity() {
    object Extras {
        const val MATCH = "EXTRA_MATCH"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        LoadMatchFromExtra()
    }

    private fun LoadMatchFromExtra() {
        intent?.extras?.getParcelable<Match>(Extras.MATCH)?.let{
            Glide.with(this).load(it.place.image).into(binding.ivPlace)
            supportActionBar?.title = it.place.name
            binding.tvDescription.text = it.description

            Glide.with(this).load(it.homeTeam.imagem).into(binding.ivHomeTeam)
            binding.tvHomeTeamName.text= it.homeTeam.name
            binding.rbHomeTeamStars.rating = it.homeTeam.stars.toFloat()
            if(it.homeTeam.score != null){
                binding.tvHomeTeamScore.text = it.homeTeam.score.toString()
            }

            Glide.with(this).load(it.awayTeam.imagem).into(binding.ivAwayTeam)
            binding.tvAwayTeamName.text= it.awayTeam.name
            binding.rbAwayTeamStars.rating = it.awayTeam.stars.toFloat()
            if(it.awayTeam.score != null){
                binding.tvAwayTeamScore.text = it.awayTeam.score.toString()
            }
        }
    }
}