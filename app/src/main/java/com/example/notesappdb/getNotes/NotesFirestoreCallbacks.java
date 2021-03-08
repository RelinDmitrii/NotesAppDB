package com.example.notesappdb.getNotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notesappdb.Note;

import java.util.List;

public interface NotesFirestoreCallbacks {

    void onSuccessNotes(@NonNull List<Note> items);
    void onErrorNotes(@Nullable String message);
    void onSuccessDeleteNote();

}
