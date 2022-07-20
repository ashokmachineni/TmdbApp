package com.android.movies.ui.main.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.movies.R
import com.android.movies.databinding.FragmentMoviesBinding
import com.android.movies.domain.models.trending.Movie
import com.android.movies.ui.main.MoviesViewModel
import com.android.movies.ui.main.loading.MovieLoadStateAdapter
import com.android.movies.utils.OnClick
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel
    private var binding: FragmentMoviesBinding? = null
    private var moviesAdapter: MoviesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMoviesUI()
    }

    private fun initMoviesUI() {
        binding?.movies?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            moviesAdapter = MoviesAdapter(onMovieClick)
            adapter = moviesAdapter?.run {
                withLoadStateFooter(
                    footer = MovieLoadStateAdapter(this)
                )
            }
            lifecycleScope.launchWhenCreated {
                moviesAdapter?.loadStateFlow?.collect { loadState ->
                    loadState.refresh.let {
                        when (it) {
                            is LoadState.Loading    -> {binding?.loadingLayout?.loadingLayout?.isVisible = true}
                            is LoadState.NotLoading -> {binding?.loadingLayout?.loadingLayout?.isVisible = false}
                            is LoadState.Error      -> {}
                        }
                    }

                }
            }

            lifecycleScope.launch {
                viewModel.getTrending().collectLatest { pagingData ->
                    moviesAdapter?.submitData(pagingData)
                }
            }

        }
    }

    private val onMovieClick = OnClick<Movie> { movie, _ ->
        val movieJson = Gson().toJson(movie)
        findNavController().navigate(
            MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                movieJson
            )
        )
    }
    override fun onDestroyView() {
        super.onDestroyView()

        binding?.movies?.adapter = null
        binding = null

    }

}