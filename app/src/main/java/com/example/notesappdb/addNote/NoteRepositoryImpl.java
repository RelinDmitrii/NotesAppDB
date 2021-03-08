package com.example.notesappdb.addNote;

import androidx.annotation.NonNull;

import com.example.notesappdb.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NoteRepositoryImpl implements NoteRepository {

    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private final NoteFirestoreCallbacks callbacks;

    public NoteRepositoryImpl(NoteFirestoreCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void setNote(@NonNull String id, @NonNull String title, @NonNull String description, @NonNull String data) {
        final Map<String, Object> note = new HashMap<>();
        note.put("id", id);
        note.put("title", title);
        note.put("description", description);
        note.put("data", data);

        firebaseFirestore
                .collection(Constants.TABLE_NAME_NOTES)
                .document(id)
                .set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callbacks.onSuccess("Заметка успешно обновлена");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callbacks.onError(e.getMessage());
                    }
                });
    }

    @Override
    public void onDeleteClicked(@NonNull String id) {
    }
}
