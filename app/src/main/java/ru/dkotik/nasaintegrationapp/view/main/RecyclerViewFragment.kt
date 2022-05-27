package ru.dkotik.nasaintegrationapp.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.dkotik.nasaintegrationapp.databinding.FragmentRecyclerViewBinding
import ru.dkotik.nasaintegrationapp.dto.pod.Data
import ru.dkotik.nasaintegrationapp.dto.pod.TYPE_HEADER
import ru.dkotik.nasaintegrationapp.dto.pod.TYPE_MARS
import ru.dkotik.nasaintegrationapp.view.OnClickItemListener

class RecyclerViewFragment : Fragment() {

    private var _binding: FragmentRecyclerViewBinding? = null

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

        super.onViewCreated(view, savedInstanceState)
    }

    private fun createAdapter(): RecyclerFragmentAdapter {
        return RecyclerFragmentAdapter(object : OnClickItemListener {
            override fun onItemClick(data: Data) {
                Toast.makeText(context, "Работает. Клик по: ${data.name}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
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