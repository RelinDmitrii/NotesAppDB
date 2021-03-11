package com.example.notesappdb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class NotesDetailFragment extends Fragment implements RefreshNote {

    public static final String ARG_INDEX = "arg_index_notes_detail_fragment";

    public TextView titleNotesDetail;
    public TextView dataNotesDetail;
    public EditText descriptionNotesDetail;
    public MaterialButton mbEditNote;
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private Note noteGoToEdit;

    public static NotesDetailFragment newInstance(String index) {
        NotesDetailFragment fragment = new NotesDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmnet_notes_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
        clickOnButton();
    }

    public void initList(View view) {
        titleNotesDetail = view.findViewById(R.id.tv_title_nd);
        dataNotesDetail = view.findViewById(R.id.tv_data_nd);
        descriptionNotesDetail = view.findViewById(R.id.et_description_nd);
        mbEditNote = view.findViewById(R.id.mb_notes_detail_edit);
    }

    public void clickOnButton() {
        mbEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteFragmentEdit noteFragmentEdit = NoteFragmentEdit.newInstance(noteGoToEdit, NotesDetailFragment.this);
                noteFragmentEdit.show(getActivity().getSupportFragmentManager(), "NoteFragmentEdit");
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getSearchText().setVisibility(View.INVISIBLE);
        if (getArguments() != null) {
            refreshNote(getArguments().getString(ARG_INDEX));
        }
    }

    @Override
    public void onResume() {
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                String result = bundle.getString("bundleKey");
                descriptionNotesDetail.setText(result);
            }
        });
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                String result = bundle.getString("bundleKey");
                descriptionNotesDetail.setText(result);
            }
        });
    }


    public void refreshNote(String id) {
        firebaseFirestore
                .collection(Constants.TABLE_NAME_NOTES)
                .document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        titleNotesDetail.setText(documentSnapshot.getString("title"));
                        dataNotesDetail.setText(documentSnapshot.getString("data"));
                        descriptionNotesDetail.setText(documentSnapshot.getString("description"));
                        //Вынести в отдельный метод
                        noteGoToEdit = new Note();
                        noteGoToEdit.setTitle(documentSnapshot.getString("title"));
                        noteGoToEdit.setDescription(documentSnapshot.getString("description"));
                        noteGoToEdit.setData(documentSnapshot.getString("data"));
                        noteGoToEdit.setId(documentSnapshot.getString("id"));
                    }
                });
    }

}
