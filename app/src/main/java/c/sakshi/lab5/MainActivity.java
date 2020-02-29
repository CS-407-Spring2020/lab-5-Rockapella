package c.sakshi.lab5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set username as text
        TextView usernameTextView = (TextView) findViewById(R.id.displayUsernameTextView);
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);

        // Display welcome message. Fetch username from SharedPreferences.
        String username = sharedPreferences.getString("username", "");
        usernameTextView.setText("Welcome " + username);

        // Get SQLiteDatabase instance.
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",Context.MODE_PRIVATE,null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        // Initiate the "notes" class variable using readNotes method implemented in DBHelper class. Use the username you got from SharedPreferences as a parameter to readNotes method.
        notes = dbHelper.readNotes(username);

        // Create an arrayList<String> object by iterating over notes object
        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        // Use ListView view to display notes on screen
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent addNoteIntent = new Intent(getApplicationContext(), AddNoteActivity.class);
                addNoteIntent.putExtra("noteid", position);
                System.out.println(addNoteIntent.getIntExtra("noteid", 2));
                startActivity(addNoteIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addNote:
                // addNoteActivity intent
                Intent addNoteActivityIntent = new Intent(MainActivity.this, AddNoteActivity.class);

                // Start the Add Note activity
                startActivity(addNoteActivityIntent);
                return true;
            case R.id.logout:
                SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("username","").apply();
                // addNoteActivity intent
//                Intent loginActivityIntent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(loginActivityIntent);
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

}
