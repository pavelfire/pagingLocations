package com.example.android.codelabs.paging.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.android.codelabs.paging.R

class FilterDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(layoutInflater.inflate(R.layout.filter_characters, null))
                //.setView(R.layout.filter_characters)
                //.setTitle("Filter for characters")
                //.setMessage("Этот экран ещё не сделан")
                //.setIcon(R.drawable.ic_characters)
                .setPositiveButton("Применить") { dialog, id ->
                    run {
                        Toast.makeText(requireContext(), "Применить", Toast.LENGTH_LONG)
                            .show()

                        val result = "result======="
                        // Здесь мы можем использовать Kotlin экстеншен функцию из fragment-ktx
                        parentFragmentManager.setFragmentResult("requestKey", bundleOf("bundleKey" to result))
                        //dialog.cancel()
                    }
                }
                .setNegativeButton("Отменить") { dialog, id ->
                    kotlin.run {
                        Toast.makeText(requireContext(), "отменить", Toast.LENGTH_LONG)
                            .show()
                        dialog.cancel()
                    }
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}