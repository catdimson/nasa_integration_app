package ru.dkotik.nasaintegrationapp.view

import androidx.recyclerview.widget.RecyclerView
import ru.dkotik.nasaintegrationapp.dto.pod.Data

fun interface OnClickItemListener {
    fun onItemClick(data: Data)
}

fun interface OnStartDragListener {
    fun onStartDrag (view: RecyclerView.ViewHolder)
}