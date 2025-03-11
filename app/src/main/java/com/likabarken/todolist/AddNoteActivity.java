package com.likabarken.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddNoteActivity extends AppCompatActivity {
    private EditText editTextAddNote;
    private RadioButton radioButtonHigh, radioButtonMedium, radioButtonLow;
    private Button buttonSave;
    private NoteDatabase noteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);
        noteDatabase = NoteDatabase.getInstance(getApplication());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    private void initViews() {
        editTextAddNote = findViewById(R.id.editTextAddNote);
        radioButtonHigh = findViewById(R.id.radioButtonHigh);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        radioButtonLow = findViewById(R.id.radioButtonLow);
        buttonSave = findViewById(R.id.buttonSave);
    }

    private void saveNote() {
        String text = editTextAddNote.getText().toString().trim();
        int priority = getPriority();
        Note note = new Note(text, priority);
        noteDatabase.notesDao().add(note);

        finish(); // close Activity
    }

    private int getPriority() {
        int priority;
        if (radioButtonHigh.isChecked()) {
            priority = 0;
        } else if (radioButtonMedium.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }
        return priority;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }
}