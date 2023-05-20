package com.em.notes_app.ui.notedetails

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.em.notes_app.R
import com.em.notes_app.data.model.NoteEntity
import com.em.notes_app.databinding.FragmentNoteDetailsBinding
import com.em.notes_app.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailsFragment : Fragment() {
    private lateinit var binding: FragmentNoteDetailsBinding
    private var note = NoteEntity()
    private var validation = false
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_note_details, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clearUI()
        if (requireArguments().getBoolean("isEditing")) {
            note = getNoteValue()
            setupData(note)
        }
        setupButtons()

        binding.noteTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrBlank() || s.isEmpty())  validation = false
                if (note.title == s.toString()) {
                    Toast.makeText(context, "GOT IN", Toast.LENGTH_SHORT).show()
                    validation = false
                }
                 if (!s.isNullOrBlank() && s.isNotEmpty())  validation = true

                if (validation) binding.saveButton.isEnabled = true
                if (!validation) binding.saveButton.isEnabled = false
            }
        })
    }

    private fun clearUI() {
        binding.noteTitle.setText("")
        binding.noteDescription.setText("")
    }

    private fun setupData(note: NoteEntity) {
        binding.noteTitle.setText(note.title)
        binding.noteDescription.setText(note.description)
    }

    private fun setupButtons() {
        setupSaveButton()
        binding.cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            viewModel.saveNote(
                NoteEntity(note.noteId, binding.noteTitle.text.toString(),
                binding.noteDescription.text.toString())
            )
            findNavController().navigateUp()
        }
    }

    private fun getNoteValue(): NoteEntity {
        when {
            SDK_INT >= 33 -> {
                return requireArguments().getParcelable("note", NoteEntity::class.java)!!
            }
            else -> {
                @Suppress("DEPRECATION")
                return requireArguments().getParcelable("note")!!
            }
        }
    }
}