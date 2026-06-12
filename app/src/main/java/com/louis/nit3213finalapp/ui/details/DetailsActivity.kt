package com.louis.nit3213finalapp.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.louis.nit3213finalapp.databinding.ActivityDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Book Details"

        binding.tvTitle.text = intent.getStringExtra("title") ?: ""
        binding.tvAuthor.text = intent.getStringExtra("author") ?: ""
        binding.tvGenre.text = intent.getStringExtra("genre") ?: ""
        binding.tvYear.text = intent.getIntExtra("publicationYear", 0).toString()
        binding.tvDescription.text = intent.getStringExtra("description") ?: ""
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
