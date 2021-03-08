package com.example.notesappdb.addNote;

import androidx.annotation.NonNull;

public interface NoteRepository {

    void setNote(
            @NonNull String id,
            @NonNull String title,
            @NonNull String description,
            @NonNull String data
    );

    void onDeleteClicked(@NonNull String id);
}
