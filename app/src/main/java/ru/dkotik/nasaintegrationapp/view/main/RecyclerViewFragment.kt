package ru.dkotik.nasaintegrationapp.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.dkotik.nasaintegrationapp.databinding.FragmentRecyclerViewBinding
import ru.dkotik.nasaintegrationapp.dto.pod.Data
import ru.dkotik.nasaintegrationapp.dto.pod.TYPE_HEADER
import ru.dkotik.nasaintegrationapp.dto.pod.TYPE_MARS
import ru.dkotik.nasaintegrationapp.view.OnClickItemListener
import ru.dkotik.nasaintegrationapp.view.OnStartDragListener

class RecyclerViewFragment : Fragment() {

    private var _binding: FragmentRecyclerViewBinding? = null
    private lateinit var itemTouchHelper: ItemTouchHelper

    val binding: FragmentRecyclerViewBinding
        get() = _binding!!

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = createAdapter()
        adapter.setData(generateFakeData())
        binding.recyclerView.adapter = adapter
        binding.recyclerFragmentFAB.setOnClickListener {
            adapter.appendItem()
            binding.recyclerView.smoothScrollToPosition(adapter.itemCount)
        }

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun createAdapter(): RecyclerFragmentAdapter {
        return RecyclerFragmentAdapter(
            object: OnClickItemListener {
                override fun onItemClick(data: Data) {
                    Toast.makeText(context, "Работает. Клик по: ${data.name}", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            object: OnStartDragListener {
                override fun onStartDrag(view: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(view)
                }
            }
        )
    }

    class ItemTouchHelperCallback(
        private val recyclerFragmentAdapter: RecyclerFragmentAdapter
    ): ItemTouchHelper.Callback() {

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val position = viewHolder.adapterPosition
            val dragFlags: Int;
            val swipeFlags: Int;

            when (position) {
                0 -> {
                    return 0
                }
                else -> {
                    dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                    swipeFlags = ItemTouchHelper.END
                }
            }

            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder, // from
            target: RecyclerView.ViewHolder      // to
        ): Boolean {
            recyclerFragmentAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            recyclerFragmentAdapter.onItemDismiss(viewHolder.adapterPosition)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (viewHolder is RecyclerFragmentAdapter.MarsViewHolder) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.onItemSelected()
                }
            }
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            if (viewHolder is RecyclerFragmentAdapter.MarsViewHolder) {
                viewHolder.onItemClear()
            }
        }
    }

}

private fun generateFakeData(): MutableList<Pair<Data, Boolean>> {
    val listData = arrayListOf(
        Pair(Data("Земля", "Дополнительный текст"), false),
        Pair(Data("Земля", "Дополнительный текст"), false),
        Pair(Data("Земля", "Дополнительный текст"), false),
        Pair(Data("Земля", "Дополнительный текст"), false),
        Pair(Data("Марс", type = TYPE_MARS), false),
        Pair(Data("Марс", type = TYPE_MARS), false),
        Pair(Data("Марс", type = TYPE_MARS), false),
        Pair(Data("Марс", type = TYPE_MARS), false),
    )
    listData.shuffle()
    listData.add(0, Pair(Data("Заголовок", type = TYPE_HEADER), false))

    return listData
}