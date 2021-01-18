package org.openweathermap.weather.presentation.main.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.openweathermap.weather.R
import org.openweathermap.weather.databinding.ItemWeatherRecyclerViewBinding

class WeatherAdapter :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private val days: MutableList<DayViewModel> = mutableListOf()

    class WeatherViewHolder(private val binding: ItemWeatherRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dayViewModel: DayViewModel) {
            dayViewModel.apply {
                binding.dayViewModel = dayViewModel
                binding.iconImageView.load(iconUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding: ItemWeatherRecyclerViewBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_weather_recycler_view,
                parent,
                false
            )
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(days[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return days.size
    }

    fun addItems(days: List<DayViewModel>) {
        this.days.apply {
            clear()
            addAll(days)
        }
        notifyDataSetChanged()
    }
}