package com.android.movies.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.android.movies.R
import com.android.movies.databinding.ActivityMainBinding
import com.android.movies.databinding.DialogSortConfigBinding
import com.android.movies.domain.models.SortDirection
import com.android.movies.domain.models.SortType
import com.android.movies.ui.main.movies.MoviesFragmentDirections
import com.android.movies.utils.gone
import com.android.movies.utils.show
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        resolveFilter()

        binding.actionBar.btnBack.setOnClickListener { onBackPressed() }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.actionBar.tvTitle.text = destination.label ?: ""

            when (destination.id) {
                R.id.movieDetailsFragment, R.id.sortFragment -> {
                    binding.actionBar.btnBack.show()
                    binding.actionBar.filter.gone()
                }

                else -> {
                    binding.actionBar.btnBack.gone()
                    binding.actionBar.filter.show()
                }
            }

        }
    }


    private fun resolveFilter() {

        binding.actionBar.filter.setOnClickListener {

            showSortDialog()
        }
    }

    private fun showSortDialog() {

        val dialogBinding = DataBindingUtil.inflate<DialogSortConfigBinding>(
            layoutInflater,
            R.layout.dialog_sort_config,
            null,
            false
        )


        val builder = MaterialAlertDialogBuilder(this).apply {
            setView(dialogBinding.root)
        }

        val dialog = builder.create()

        dialog.show()


        dialogBinding.apply.setOnClickListener {
            dialog.hide()

            val sortType = when {
                dialogBinding.btnName.isChecked -> SortType.NAME
                dialogBinding.btnRank.isChecked -> SortType.RANK
                else -> throw IllegalStateException("")
            }

            val direction = when {
                dialogBinding.btnAscending.isChecked -> SortDirection.ASC
                dialogBinding.btnDescending.isChecked -> SortDirection.DESC
                else -> throw IllegalStateException("")
            }

            val action =
                MoviesFragmentDirections.actionMoviesFragmentToSortFragment(sortType, direction)

            navController.navigate(action)

        }

    }

}