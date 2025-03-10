package com.likabarken.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    // Здесь можно в переменной с типом List использовать конструктор с типом ArrayList,
    // посколько ArrayList реализует интерфейс List
    private List<Note> notes = new ArrayList<>();

    // Из активити в переменную onNoteClickListener будем устанавливать новое значение,
    // будем передавать реализацию интерфейса onNoteClickListener,
    // а в адаптере будем с ней работать
    private OnNoteClickListener onNoteClickListener;

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public List<Note> getNotes() {
        // return notes;
        // Если нужно вернуть коллекцию, то возвращать нужно копию коллекции, чтобы снаружи не было
        // возможности изменять этот объект
        return new ArrayList<>(notes);
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
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

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onNoteClickListener != null){
                    onNoteClickListener.onNoteClick(note);
                }

            }
        });

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

    interface OnNoteClickListener{

        void onNoteClick(Note note);
    }
}
