package com.example.notesappdb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesListRvAdapter extends RecyclerView.Adapter<NotesListRvAdapter.ViewHolder> {

    List<Note> notes;
    private OnItemClickListener itemClickListener;
    public NotesListRvAdapter(@NonNull List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesListRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesListRvAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view , String position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public void submitList(List<Note> noteModelList){
        notes.clear();
        notes.addAll(noteModelList);
        notifyDataSetChanged();
    }

    public Note getNote(int position){
        return notes.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        TextView textViewData;
        TextView textViewDescription;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.tv_card_title);
            textViewData = itemView.findViewById(R.id.tv_card_data);
            textViewDescription = itemView.findViewById(R.id.tv_card_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, notes.get(getAdapterPosition()).id);
                    }

                }
            });
        }

        public void bind(int position){
            textViewTitle.setText(notes.get(position).title);
            textViewData.setText(notes.get(position).data);
            textViewDescription.setText(notes.get(position).description);
        }
    }
}
