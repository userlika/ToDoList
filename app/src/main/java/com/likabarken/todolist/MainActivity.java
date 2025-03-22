package com.likabarken.todolist;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycleViewNotes;
    private FloatingActionButton buttonAddNote;
    private NotesAdapter notesAdapter;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // viewModel занимается только отображением данных и взаимодействием с пользователем
        // viewModel живет дольше, чем активити и умеет переживать переворот экрана
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

//        viewModel.getCount().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer count) {
//                Toast.makeText(
//                        MainActivity.this,
//                        String.valueOf(count),
//                        Toast.LENGTH_SHORT
//                ).show();
//            }
//        });

        initViews();
        notesAdapter = new NotesAdapter();

//        notesAdapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
//            @Override
//            public void onNoteClick(Note note) {
//                viewModel.showCount();
//                //int count  = viewModel.getCount();
//            }
//        });

        recycleViewNotes.setAdapter(notesAdapter);

        // Подписка на все изменения, которые произойдут в базе данных.
        // При наличии изменений, новые данные прилетят в метод onChanged.
        viewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesAdapter.setNotes(notes);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Note note = notesAdapter.getNotes().get(position);

                        viewModel.remove(note);

                    }
                });

        itemTouchHelper.attachToRecyclerView(recycleViewNotes);

        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AddNoteActivity.newIntent(MainActivity.this));
            }
        });

    }


    private void initViews() {
        recycleViewNotes = findViewById(R.id.recycleViewNotes);
        buttonAddNote = findViewById(R.id.buttonAddNote);
    }
}
