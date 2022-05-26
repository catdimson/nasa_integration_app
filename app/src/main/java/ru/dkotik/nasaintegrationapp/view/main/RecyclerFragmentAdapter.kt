package ru.dkotik.nasaintegrationapp.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dkotik.nasaintegrationapp.databinding.FragmentRecyclerItemEarthBinding
import ru.dkotik.nasaintegrationapp.databinding.FragmentRecyclerItemHeaderBinding
import ru.dkotik.nasaintegrationapp.databinding.FragmentRecyclerItemMarsBinding
import ru.dkotik.nasaintegrationapp.databinding.FragmentRecyclerViewBinding
import ru.dkotik.nasaintegrationapp.dto.pod.Data
import ru.dkotik.nasaintegrationapp.dto.pod.TYPE_EARTH
import ru.dkotik.nasaintegrationapp.dto.pod.TYPE_HEADER
import ru.dkotik.nasaintegrationapp.dto.pod.TYPE_MARS
import ru.dkotik.nasaintegrationapp.view.OnClickItemListener

class RecyclerFragmentAdapter(
    val onClickItemListener: OnClickItemListener
): RecyclerView.Adapter<RecyclerFragmentAdapter.BaseViewHolder>() {
    private lateinit var listData: MutableList<Data>
    fun setData(listData: MutableList<Data>) {
        this.listData = listData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when(viewType) {
            TYPE_EARTH -> {
                val binding = FragmentRecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EarthViewHolder(binding.root)
            }
            TYPE_HEADER -> {
                val binding = FragmentRecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding.root)
            }
            else -> {
                val binding = FragmentRecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MarsViewHolder(binding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount() = listData.size

    override fun getItemViewType(position: Int) = listData[position].type

    fun appendItem() {
        listData.add(generateItem())
        notifyItemInserted(listData.size - 1)
    }

    private fun generateItem() = Data("Mars", type = TYPE_MARS)

    abstract class BaseViewHolder(view:View):RecyclerView.ViewHolder(view){
        abstract fun bind(data: Data)
    }

    inner class EarthViewHolder(view: View): BaseViewHolder(view) {
        override fun bind(data: Data) {
            FragmentRecyclerItemEarthBinding.bind(itemView).apply {
                tvName.text = data.name
                tvDescription.text = data.description
                ivEarth.setOnClickListener {
                    onClickItemListener.onItemClick(data)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View): BaseViewHolder(view) {
        override fun bind(data: Data) {
            FragmentRecyclerItemMarsBinding.bind(itemView).apply {
                tvName.text = data.name
                ivMars.setOnClickListener {
                    onClickItemListener.onItemClick(data)
                }
            }
        }
    }

    inner class HeaderViewHolder(view:View):BaseViewHolder(view){
        override fun bind(data: Data){
            FragmentRecyclerItemHeaderBinding.bind(itemView).apply {
                tvName.text = data.name
                itemView.setOnClickListener {
                    onClickItemListener.onItemClick(data)
                }
            }
        }
    }
}