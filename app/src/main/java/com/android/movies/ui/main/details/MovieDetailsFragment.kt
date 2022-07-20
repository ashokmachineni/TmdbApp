package com.android.movies.ui.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.movies.R
import com.android.movies.databinding.FragmentMovieDetailsBinding
import com.android.movies.ui.main.MoviesViewModel
import com.android.movies.domain.models.trending.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    lateinit var viewModel: MoviesViewModel
    private var binding: FragmentMovieDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)

        val movieJson = arguments?.getString("movieJson")

        val movie = Gson().fromJson(movieJson, Movie::class.java)

        initUI(movie)


    }

    private fun initUI(movie: Movie) {

        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding?.poster!!)
        binding?.title?.text = movie.title
        binding?.date?.text = movie.releaseDate
        binding?.overview?.text = movie.overview
        binding?.rate?.text = movie.vote_average.toString() + "/10"
        binding?.popularity?.text = "Popularity: ${movie.popularity}"
        binding?.country?.text = "Country: ${movie.origin_country?:"Unknown"}"

    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}