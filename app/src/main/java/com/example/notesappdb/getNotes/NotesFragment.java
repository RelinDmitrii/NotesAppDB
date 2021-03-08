package com.example.notesappdb.getNotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesappdb.MainActivity;
import com.example.notesappdb.Note;
import com.example.notesappdb.NotesDetailFragment;
import com.example.notesappdb.NotesListRvAdapter;
import com.example.notesappdb.R;
import com.example.notesappdb.addNote.NoteAddFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment implements NotesListRvAdapter.OnItemClickListener, NotesFirestoreCallbacks {

    public static final String ARG_INDEX = "arg_index_notes_fragment";

    RecyclerView recyclerView;
    NotesListRvAdapter notesListRvAdapter;
    FloatingActionButton fab;
    private final List<Note> notesList = new ArrayList<>();
    private final NotesRepository repository = new NotesRepositoryImpl(this);
    private ItemTouchHelper itemTouchHelper;

    public static NotesFragment newInstance() {
        NotesFragment fragment = new NotesFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmnet_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter(view);
        fab = view.findViewById(R.id.fab_notes_fragment_add);
        ith();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setAdapter(notesListRvAdapter);
        ((MainActivity) getActivity()).getSearchText().setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteAddFragment fragment = new NoteAddFragment();
                ((MainActivity) getActivity()).getNavigation().addFragment(fragment, true);
            }
        });
        repository.requestNotes();
    }

    public void initAdapter(View view) {
        recyclerView = view.findViewById(R.id.rv);
        notesListRvAdapter = new NotesListRvAdapter(notesList);
        notesListRvAdapter.setOnItemClickListener(this);
    }


    private void startNotesDetailActivity(String index) {
        NotesDetailFragment fragment = NotesDetailFragment.newInstance(index);
        ((MainActivity) getActivity()).getNavigation().addFragment(fragment, true);
    }

    @Override
    public void onItemClick(View view, String position) {
        startNotesDetailActivity(position);
    }

    @Override
    public void onSuccessNotes(@NonNull List<Note> items) {
        notesList.clear();
        notesList.addAll(items);
        notesListRvAdapter.submitList(items);
    }

    @Override
    public void onErrorNotes(@Nullable String message) {

    }

    @Override
    public void onSuccessDeleteNote() {
        repository.requestNotes();
    }

    public void ith() {
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                repository.onDeleteClicked(notesListRvAdapter.getNote(viewHolder.getAdapterPosition()).id);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    ;


}
