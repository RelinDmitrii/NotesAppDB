package com.example.notesappdb.addNote;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notesappdb.MainActivity;
import com.example.notesappdb.Note;
import com.example.notesappdb.R;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;
import java.util.UUID;

public class NoteAddFragment extends Fragment implements NoteFirestoreCallbacks {

    EditText etTitle;
    TextView etData;
    EditText editTextDescription;
    MaterialButton materialButtonSave;
    MaterialButton materialButtonDataPicker;
    DatePickerDialog picker;

    private final NoteRepository repository = new NoteRepositoryImpl(this);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_add, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getSearchText().setVisibility(View.INVISIBLE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        materialButtonDataPicker.setOnClickListener(new View.OnClickListener() {
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
                                etData.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        materialButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = UUID.randomUUID().toString();
                repository.setNote(id, etTitle.getText().toString(), editTextDescription.getText().toString(), etData.getText().toString());
            }
        });
    }

    public void init(View view) {
        etTitle = view.findViewById(R.id.et_add_fragment_title);
        etData = view.findViewById(R.id.et_add_fragment_data);
        editTextDescription = view.findViewById(R.id.et_add_fragment_description);
        materialButtonSave = view.findViewById(R.id.mb_note_add_fragment_save);
        materialButtonDataPicker = view.findViewById(R.id.mb_note_add_fragment_addData);

    }

    @Override
    public void onSuccess(@Nullable String message) {
        showToastMessage(message);
        getActivity().onBackPressed();
    }

    @Override
    public void onError(@Nullable String message) {
        showToastMessage(message);
    }

    private void showToastMessage(@Nullable String message) {
        if (message != null) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
