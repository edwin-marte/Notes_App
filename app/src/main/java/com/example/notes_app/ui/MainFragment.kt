package com.example.notes_app.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes_app.R
import com.example.notes_app.core.Resource
import com.example.notes_app.data.model.NoteEntity
import com.example.notes_app.databinding.FragmentMainBinding
import com.example.notes_app.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel by activityViewModels<MainViewModel>()
    private val notes = mutableListOf<NoteEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateRecyclerViewData()

        binding.newNoteButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("isEditing", false)
            findNavController().navigate(R.id.action_mainFragment_to_noteDetailsFragment, bundle)
        }

        setupRecyclerViewLayout()
        setupRecyclerView(notes)
    }

    fun confirmDeletionDialog(note: NoteEntity, index: Int) {
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        alertDialog.setTitle("Delete note")
        alertDialog.setMessage("Are you sure you want to delete this note?")

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete"
        ) { dialog, _ ->
            viewModel.deleteNote(note)
            notes.removeAt(index)
            binding.notesRecyclerView.adapter?.notifyItemRemoved(index)
            dialog.dismiss()
        }

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel"
        ) { dialog, _ -> dialog.dismiss() }

        alertDialog.show()

        val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        val btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)

        val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = 10f
        btnPositive.layoutParams = layoutParams
        btnNegative.layoutParams = layoutParams
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateRecyclerViewData() {
        viewModel.getNotes().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    notes.clear()
                    notes.addAll(result.data)
                    binding.notesRecyclerView.adapter?.notifyDataSetChanged()
                }
                is Resource.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "An error ocurred while loading the data ${ result.exception }",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun setupRecyclerViewLayout() {
        binding.notesRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val spaceSize = 30
        binding.notesRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                with(outRect) {
                    if (parent.getChildAdapterPosition(view) == 0) top = 0
                    left = 0
                    right = 0
                    bottom = spaceSize
                }
            }
        })
    }

    private fun setupRecyclerView(notes: List<NoteEntity>) {
        fun onNoteClick() = object : RecyclerViewAdapter.OnItemClickListener {
            override fun onNoteClick(note: NoteEntity) {
                val bundle = Bundle()
                bundle.putParcelable("note", note)
                bundle.putBoolean("isEditing", true)
                findNavController().navigate(
                    R.id.action_mainFragment_to_noteDetailsFragment,
                    bundle
                )
            }
        }

        fun onDeleteClick() = object : RecyclerViewAdapter.OnDeleteClickListener {
            override fun onDeleteClick(note: NoteEntity) {
                confirmDeletionDialog(note, notes.indexOf(note))
            }
        }

        binding.notesRecyclerView.adapter =
            RecyclerViewAdapter(requireContext(), notes, onNoteClick(), onDeleteClick())
    }
}