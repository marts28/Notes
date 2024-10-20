package com.example.notes.ui.fragment

import android.content.Context
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.NoteApplication
import com.example.notes.R
import com.example.notes.databinding.FragmentNoteListBinding
import com.example.notes.domain.entity.NoteEntity.Companion.NEW_NOTE_ID
import com.example.notes.presentation.state.NoteListState
import com.example.notes.presentation.viewmodel.NoteListViewModel
import com.example.notes.presentation.viewmodel.ViewModelFactory
import com.example.notes.ui.adapter.NoteListAdapter
import com.example.notes.util.AlertDialogUtil
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null
    private val binding: FragmentNoteListBinding
        get() = _binding?: throw RuntimeException("FragmentNoteListBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[NoteListViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as NoteApplication).component
    }

    private val noteAdapter = NoteListAdapter()

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.menu_toolbar_note_list, menu)

            val searchView = menu.findItem(R.id.sv_search_note).actionView as SearchView
            setQueryListener(searchView)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            if (menuItem.itemId == R.id.bt_change_list_style) {
                changeNoteListAppereance()
            }
            return true
        }

        private fun changeNoteListAppereance() {
            with((binding.rvNotes.layoutManager as StaggeredGridLayoutManager)) {
                if (spanCount == 2) {
                    spanCount = 1
                    binding.tbFragmentNoteList.menu.findItem(R.id.bt_change_list_style)
                        .setIcon(R.drawable.outline_grid_view_24)
                } else {
                    spanCount = 2
                    binding.tbFragmentNoteList.menu.findItem(R.id.bt_change_list_style)
                        .setIcon(R.drawable.outline_view_agenda_24)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
        viewModel.getNotes()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStateObserver()
        setupRecycler()
        setClickListeners()
        setupToolBar()
    }

    private fun setupToolBar() {
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding.tbFragmentNoteList)
            title = ""
        }
        requireActivity().addMenuProvider(menuProvider)
    }

    private fun setQueryListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { text ->
                    if (text.isNotEmpty())
                        viewModel.searchNote(newText)
                    else
                        viewModel.getNotes()
                }
                return true
            }

        })
    }

    private fun setClickListeners() {
        binding.fbCreateNote.setOnClickListener {
            navigateToNote(NEW_NOTE_ID)
        }
    }

    private fun setupRecycler() {
        binding.rvNotes.adapter = noteAdapter

        setupSetOnClickListenerNote()
        setupSetOnLongClickListenerNote()
    }

    private fun setupSetOnClickListenerNote() {
        noteAdapter.onNoteClickListener = { note ->
            navigateToNote(note.id)
        }
    }

    private fun setupSetOnLongClickListenerNote() {
        noteAdapter.onNoteLongClickListener = { note ->
            AlertDialogUtil.createDialogToDeleteNote(requireActivity()) {
                viewModel.deleteNote(
                    note.id
                )
            }
        }
    }

    private fun navigateToNote(noteId: Int) {
        val action =
            NoteListFragmentDirections.actionNoteListFragmentToNoteFragment()
                .setNoteId(
                    noteId
                )
        findNavController().navigate(action)
    }

    private fun setStateObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect {
                    when (it) {
                        is NoteListState.NoteList -> {
                            binding.tvMessage.visibility = View.GONE
                            noteAdapter.submitList(it.noteList)
                        }

                        is NoteListState.EmptyNoteList -> {
                            noteAdapter.submitList(emptyList())
                            with(binding.tvMessage) {
                                visibility = View.VISIBLE
                                text = getString(R.string.empty_note_list_message)
                            }
                        }

                        is NoteListState.Loading -> {
                            with(binding.tvMessage) {
                                visibility = View.VISIBLE
                                text = getString(R.string.loading_note_list_message)
                            }
                        }
                    }
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().removeMenuProvider(menuProvider)
        _binding = null
    }
}