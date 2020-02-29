package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    // login button
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Start up shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);

        //
        if (!sharedPreferences.getString("username", "").equals("")) {

            // Get username from sharedPreferences
            String username = sharedPreferences.getString("username", "");

            // main activity intent
            Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);

            // Start main activity
            startActivity(mainActivityIntent);
        }
        else {
            setContentView(R.layout.activity_login);
            configLoginButton();
        }
    }

    /**
     * This method will configure what our login button will do.
     */
    private void configLoginButton(){

        // Get the button
        loginButton = (Button)findViewById(R.id.loginButton);

        // When the button is clicked we will call the onLogin method
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get username and password via EditText view
                EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
                EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Add username to sharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("username", username).apply();
                sharedPreferences.edit().putString("password", password).apply();

                // main activity intent
                Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);

                // Start the second activity
                startActivity(mainActivityIntent);
            }
        });
    }
}
