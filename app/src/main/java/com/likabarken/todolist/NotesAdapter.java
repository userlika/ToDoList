package com.likabarken.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private ArrayList<Note> notes = new ArrayList<>();

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // В этом методе показывается, как создается view из макета

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.note_item,
                parent,
                false
        );
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder viewHolder, int position) {
        // Этот метод отвечает за то, какой цвет, какой текст будет установлен во view элементы
        // По номеру позиции position можно узнать, какой элемент нужно установить

        Note note = notes.get(position); // Заметка, которую будем отображать
        viewHolder.textViewNote.setText(note.getText());

        int colorResId;

        switch (note.getPriority()) {
            case 0:
                colorResId = R.color.green_priority_1;
                break;
            case 1:
                colorResId = R.color.yellow_priority_2;
                break;
            default:
                colorResId = R.color.pink_priority_3;
        }

        int color = ContextCompat.getColor(viewHolder.itemView.getContext(), colorResId);
        viewHolder.textViewNote.setBackgroundColor(color);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNote;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNote =itemView.findViewById(R.id.textViewNote);
        }
    }
}
