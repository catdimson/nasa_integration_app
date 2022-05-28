package ru.dkotik.nasaintegrationapp.view.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import ru.dkotik.nasaintegrationapp.databinding.FragmentRecyclerItemEarthBinding
import ru.dkotik.nasaintegrationapp.databinding.FragmentRecyclerItemHeaderBinding
import ru.dkotik.nasaintegrationapp.databinding.FragmentRecyclerItemMarsBinding
import ru.dkotik.nasaintegrationapp.dto.pod.Data
import ru.dkotik.nasaintegrationapp.dto.pod.TYPE_EARTH
import ru.dkotik.nasaintegrationapp.dto.pod.TYPE_HEADER
import ru.dkotik.nasaintegrationapp.dto.pod.TYPE_MARS
import ru.dkotik.nasaintegrationapp.view.OnClickItemListener
import ru.dkotik.nasaintegrationapp.view.OnStartDragListener

class RecyclerFragmentAdapter(
    val onClickItemListener: OnClickItemListener,
    val onStartDragListener: OnStartDragListener
): RecyclerView.Adapter<RecyclerFragmentAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {

    private lateinit var listData: MutableList<Pair<Data, Boolean>>
    fun setData(listData: MutableList<Pair<Data, Boolean>>) {
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

    override fun getItemViewType(position: Int) = listData[position].first.type

    fun appendItem() {
        listData.add(generateItem())
        notifyItemInserted(listData.size - 1)
    }

    private fun generateItem() = Pair(Data("Mars", type = TYPE_MARS), false)

    abstract class BaseViewHolder(view:View):RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Pair<Data, Boolean>)
    }

    inner class EarthViewHolder(view: View): BaseViewHolder(view) {
        override fun bind(data: Pair<Data, Boolean>) {
            FragmentRecyclerItemEarthBinding.bind(itemView).apply {
                tvName.text = data.first.name
                tvDescription.text = data.first.description
                ivEarth.setOnClickListener {
                    onClickItemListener.onItemClick(data.first)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View): BaseViewHolder(view), ItemTouchHelperViewHolder {
        @SuppressLint("ClickableViewAccessibility")
        override fun bind(data: Pair<Data, Boolean>) {
            FragmentRecyclerItemMarsBinding.bind(itemView).apply {
                tvName.text = data.first.name
                ivMars.setOnClickListener {
                    onClickItemListener.onItemClick(data.first)
                }
                addItemImageView.setOnClickListener {
                    listData.add(layoutPosition, generateItem())
                    notifyItemInserted(layoutPosition)
                }
                removeItemImageView.setOnClickListener {
                    listData.removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
                moveItemUp.setOnClickListener {
                    if (isNotUpLimit(layoutPosition)) {
                        listData.removeAt(layoutPosition).apply {
                            listData.add(layoutPosition - 1, this)
                        }
                        notifyItemMoved(layoutPosition,layoutPosition - 1)
                    }
                }
                moveItemDown.setOnClickListener {
                    if (isNotDownLimit(layoutPosition)) {
                        listData.removeAt(layoutPosition).apply {
                            listData.add(layoutPosition + 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition + 1)
                    }
                }

                marsDescriptionTextView.visibility = if(listData[layoutPosition].second) View.VISIBLE else View.GONE

                itemView.setOnClickListener {
                    listData[layoutPosition] = listData[layoutPosition].let {
                        it.first to !it.second
                    }
                    notifyItemChanged(layoutPosition)
                }

                dragHandleImageView.setOnTouchListener { _, motionEvent ->
                    if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                        onStartDragListener.onStartDrag(this@MarsViewHolder)
                    }
                    false
                }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.YELLOW)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    inner class HeaderViewHolder(view:View):BaseViewHolder(view){
        override fun bind(data: Pair<Data, Boolean>){
            FragmentRecyclerItemHeaderBinding.bind(itemView).apply {
                tvName.text = data.first.name
                itemView.setOnClickListener {
                    onClickItemListener.onItemClick(data.first)
                }
            }
        }
    }

    private fun isNotUpLimit(position: Int) = position != 1

    private fun isNotDownLimit(position: Int) = position + 1 != listData.size

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (isNotUpLimit(fromPosition) || fromPosition < toPosition) { //  && fromPosition < toPosition
            listData.removeAt(fromPosition).apply {
                listData.add(toPosition, this)
            }
            notifyItemMoved(fromPosition, toPosition)
        }
    }

    override fun onItemDismiss(position: Int) {
        listData.removeAt(position)
        notifyItemRemoved(position)
    }
}