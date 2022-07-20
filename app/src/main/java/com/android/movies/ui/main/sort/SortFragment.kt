package com.android.movies.ui.main.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.movies.R
import com.android.movies.databinding.FragmentSortBinding
import com.android.movies.domain.models.SortType
import com.android.movies.domain.models.trending.Movie
import com.android.movies.ui.main.MoviesViewModel
import com.android.movies.utils.OnClick
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

private const val ARG_TYPE = "type"
private const val ARG_DIRECTION = "direction"

@AndroidEntryPoint
class SortFragment : Fragment() {
    private var type: String? = null
    private var direction: String? = null

    private var binding: FragmentSortBinding? = null

    private val viewModel: MoviesViewModel by viewModels()

    private val args: SortFragmentArgs by navArgs()

    private var moviesAdapter: SortAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString(ARG_TYPE)
            direction = it.getString(ARG_DIRECTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sort, container, false)

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            val type = args.type
            val direction = args.direction
            val response = when (type) {
                SortType.NAME -> viewModel.sortResults().sortByName(direction).first()
                SortType.RANK -> viewModel.sortResults().sortByRating(direction).first()
            }
            initMoviesUI(response)
        }
    }

    private fun initMoviesUI(movies: List<Movie>) {
        binding?.movies?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            moviesAdapter = SortAdapter(movies, onMovieClick)
            adapter = moviesAdapter
        }
    }

    private val onMovieClick = OnClick<Movie> { movie, _ ->
        val movieJson = Gson().toJson(movie)
        findNavController().navigate(
            SortFragmentDirections.actionSortFragmentToMovieDetailsFragment(
                movieJson
            )
        )

    }

}