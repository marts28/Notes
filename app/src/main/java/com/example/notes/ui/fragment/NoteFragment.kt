package com.example.notes.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.NoteApplication
import com.example.notes.R
import com.example.notes.databinding.FragmentNoteBinding
import com.example.notes.presentation.viewmodel.NoteViewModel
import com.example.notes.presentation.viewmodel.ViewModelFactory
import com.example.notes.util.AlertDialogUtil
import javax.inject.Inject

class NoteFragment : Fragment() {

    private val args by navArgs<NoteFragmentArgs>()

    private var _binding: FragmentNoteBinding? = null
    private val binding: FragmentNoteBinding
        get() = _binding?: throw RuntimeException("FragmentNoteBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: NoteViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[NoteViewModel::class]
    }

    private val component by lazy {
        (requireActivity().application as NoteApplication).component
    }

    private val menuProvider = object: MenuProvider{
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.menu_toolbar_note, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            if (menuItem.itemId == R.id.bt_delete_note){
                AlertDialogUtil.createDialogToDeleteNote(requireActivity()){
                    viewModel.deleteNote(args.noteId)
                    findNavController().navigate(R.id.noteListFragment)
                }
            }
            return true
        }

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getSelectedNote(args.noteId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolBar()
        setOnBackPressCallback()
        if (args.noteId !=0){
            setObserveViewModel()
            requireActivity().addMenuProvider(menuProvider)
        }
    }

    private fun setToolBar() {
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding.tbFragmentNote)
            title = ""
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        }

        binding.tbFragmentNote.setNavigationOnClickListener {
            saveNoteAndExit()
        }
    }

    private fun setObserveViewModel() {
        viewModel.selectedNote.observe(viewLifecycleOwner){ note ->
            binding.etNoteTitle.setText(note.title)
            binding.etNoteContent.setText(note.text)
        }
    }

    private fun setOnBackPressCallback() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                saveNoteAndExit()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    private fun saveNoteAndExit() {
        val title = binding.etNoteTitle.text.toString()
        val content = binding.etNoteContent.text.toString()
        viewModel.saveNote(title, content)
        findNavController().navigate(R.id.noteListFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (args.noteId !=0 ){
            requireActivity().removeMenuProvider(menuProvider)
        }
    }
}