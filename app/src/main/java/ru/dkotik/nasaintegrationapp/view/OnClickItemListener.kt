package ru.dkotik.nasaintegrationapp.view

import ru.dkotik.nasaintegrationapp.dto.pod.Data

fun interface OnClickItemListener {
    fun onItemClick(data: Data)
}