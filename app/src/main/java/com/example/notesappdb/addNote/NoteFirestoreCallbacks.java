package com.example.notesappdb.addNote;

import androidx.annotation.Nullable;

public interface NoteFirestoreCallbacks {

    void onSuccess(@Nullable String message);

    void onError(@Nullable String message);

}
