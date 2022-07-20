package com.android.movies.ui.main.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.movies.R
import com.android.movies.databinding.ListitemMovieBinding
import com.android.movies.domain.models.trending.Movie
import com.android.movies.utils.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class MoviesAdapter(
    var onClick: OnClick<Movie>? = null
) : PagingDataAdapter<Movie, MoviesAdapter.Companion.MovieHolder>(MOVIE_COMPARATOR) {

    companion object {
        class MovieHolder(val binding: ListitemMovieBinding) : RecyclerView.ViewHolder(binding.root)
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding: ListitemMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.listitem_movie,
            parent,
            false
        )
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = getItem(position)!!
        val binding = holder.binding
        val context = holder.itemView.context

        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.poster)

        binding.title.text = movie.title
        binding.date.text = movie.releaseDate
        binding.overview.text = movie.overview
        binding.rate.text = movie.vote_average.toString() + "/10"

        binding.cardParent.setOnClickListener { onClick?.invoke(movie, position) }

    }

}