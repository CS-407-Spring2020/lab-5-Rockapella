package c.sakshi.lab5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    // Reference note button
    Button addNoteButton;

    // Reference textEdit field
    EditText addNoteEditText;

    // noteID
    int noteid = -1;

    // create DB Helper
    DBHelper dbHelper;

    // SharedPreferences

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        noteid = getIntent().getIntExtra("noteid", -1);

        if(noteid != -1) {
            Note note = MainActivity.notes.get(noteid);
            String noteContent = note.getContent();
            addNoteEditText = (EditText) findViewById(R.id.addNoteEditText);
            addNoteEditText.setText(noteContent);
        }

        // Get SQLiteDatabase instance.
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",Context.MODE_PRIVATE,null);
        dbHelper = new DBHelper(sqLiteDatabase);

        configAddNoteButton();
    }

    /**
     * This method will configure what our login button will do.
     */
    private void configAddNoteButton(){

        // Get the button
        addNoteButton = (Button)findViewById(R.id.addNoteButton);

        // Get editText Field
        addNoteEditText = (EditText) findViewById(R.id.addNoteEditText);


        // When the button is clicked we will call the onLogin method
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Save note
                saveMethod(view);
            }
        });
    }

    public void saveMethod(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String content = addNoteEditText.getText().toString();

        // Get SQLiteDatabase instance.
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",Context.MODE_PRIVATE,null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        String username = sharedPreferences.getString("username", "");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        System.out.println(noteid);

        if (noteid == -1) { // Add note.
            title = "NOTE_" + (MainActivity.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        }
        else { // Update note
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(title, date, content, username);
        }

        // main activity intent
//        Intent mainActivityIntent = new Intent(AddNoteActivity.this, MainActivity.class);

        // Go back to main screen
        finish();
    }
}
