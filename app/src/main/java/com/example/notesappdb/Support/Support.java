package com.example.notesappdb.Support;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.notesappdb.R;
import com.example.notesappdb.getNotes.NotesFragment;

public class Support {
    private final FragmentManager fragmentManager;

    public Support(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void addFragment(Fragment fragment, boolean useBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (useBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    public void addAlertDialog(Fragment fragment, String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
        builder.setTitle(R.string.exclamation)
        .setMessage(R.string.deleteNote)
        .setIcon(R.mipmap.ic_launcher_round)
        .setCancelable(false)
                .setPositiveButton(R.string.buttonYes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((NotesFragment) fragment).deleteItem(id);
                            }
                        }).create().show();
    }

}
