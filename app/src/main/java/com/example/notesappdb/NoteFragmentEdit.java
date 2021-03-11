package com.example.notesappdb;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.notesappdb.addNote.NoteFirestoreCallbacks;
import com.example.notesappdb.addNote.NoteRepository;
import com.example.notesappdb.addNote.NoteRepositoryImpl;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

public class NoteFragmentEdit extends DialogFragment implements NoteFirestoreCallbacks {

    public static final String ARG_INDEX = "arg_index_notes_edit_fragment";
    private DatePickerDialog picker;
    private NoteRepositoryImpl noteRepository;
    private RefreshNote refreshNote;

    public static NoteFragmentEdit newInstance(Note note, RefreshNote refreshNote) {
        NoteFragmentEdit fragment = new NoteFragmentEdit();
        fragment.refreshNote = refreshNote;
        Bundle bundle = new Bundle();
        bundle.putSerializable("NoteObject", note);
        fragment.setArguments(bundle);
        return fragment;
    }

    EditText textViewTitle;
    TextView textViewData;
    EditText editTextDescription;
    MaterialButton materialButtonSave;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_edit, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getSearchText().setVisibility(View.INVISIBLE);
        if (getArguments() != null) {
            Note note = (Note) getArguments().getSerializable("NoteObject");
            textViewTitle.setText(note.title);
            textViewData.setText(note.data);
            editTextDescription.setText(note.description);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        clickOnButton();
        clickOnDataChange();
    }

    public void init(View view) {
        textViewTitle = view.findViewById(R.id.et_edit_fragment_title);
        textViewData = view.findViewById(R.id.tv_edit_fragment_data);
        editTextDescription = view.findViewById(R.id.et_edit_fragment_description);
        materialButtonSave = view.findViewById(R.id.mb_edit_fragment_save);
    }

    public void clickOnButton() {
        materialButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteRepository = new NoteRepositoryImpl(NoteFragmentEdit.this);
                noteRepository.setNote(((Note) getArguments().getSerializable("NoteObject")).id, textViewTitle.getText().toString(), editTextDescription.getText().toString(), textViewData.getText().toString());

            }
        });
    }

    @Override
    public void onSuccess(@Nullable String message) {
        Toast.makeText(getContext(), "Успешно", Toast.LENGTH_SHORT).show();
        refreshNote.refreshNote(((Note) getArguments().getSerializable("NoteObject")).id);
        this.dismiss();


    }

    @Override
    public void onError(@Nullable String message) {
        Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
    }


    public void clickOnDataChange() {
        textViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                textViewData.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }
}
