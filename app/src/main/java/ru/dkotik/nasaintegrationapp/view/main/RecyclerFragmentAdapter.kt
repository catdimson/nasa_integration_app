package ru.dkotik.nasaintegrationapp.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dkotik.nasaintegrationapp.databinding.FragmentRecyclerItemEarthBinding
import ru.dkotik.nasaintegrationapp.databinding.FragmentRecyclerItemMarsBinding
import ru.dkotik.nasaintegrationapp.databinding.FragmentRecyclerViewBinding
import ru.dkotik.nasaintegrationapp.dto.pod.Data
import ru.dkotik.nasaintegrationapp.dto.pod.TYPE_EARTH
import ru.dkotik.nasaintegrationapp.view.OnClickItemListener

class RecyclerFragmentAdapter(
    val onClickListener: OnClickItemListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var listData: List<Data>
    fun setData(listData: List<Data>) {
        this.listData = listData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_EARTH -> {
                val binding = FragmentRecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EarthViewHolder(binding.root)
            }
            else -> {
                val binding = FragmentRecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MarsViewHolder(binding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_EARTH -> {
                (holder as EarthViewHolder).bind(listData[position])
            }
            else -> {
                (holder as MarsViewHolder).bind(listData[position])
            }
        }
    }

    override fun getItemCount() = listData.size

    override fun getItemViewType(position: Int) = listData[position].type

    inner class EarthViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(data: Data) {
            FragmentRecyclerItemEarthBinding.bind(itemView).apply {
                tvName.text = data.name
                tvDescription.text = data.description
                ivEarth.setOnClickListener {
                    onClickListener.onItemClick(data)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(data: Data) {
            FragmentRecyclerItemMarsBinding.bind(itemView).apply {
                tvName.text = data.name
                ivMars.setOnClickListener {
                    onClickListener.onItemClick(data)
                }
            }
        }
    }
}