package studentinfo.example.com.studentform.activites;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import studentinfo.example.com.studentform.R;

public class LoginActivity extends Activity {
    EditText editUsername;
    EditText editPassword;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editUsername = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.edit_password);
        preferences = getSharedPreferences("Login", MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("username", "sarthak");
        editor.putString("password", "password");
        editor.commit();
    }

    public void onClick(View view) {
        if (editUsername.getText().toString().equals(preferences.getString("username", "")) &&
                editPassword.getText().toString().equals(preferences.getString("password", ""))) {
            Intent intent = new Intent(LoginActivity.this, DisplayActivity.class);
            startActivity(intent);
            finish();
        } else
            Toast.makeText(LoginActivity.this, "Wrong username or password.", Toast.LENGTH_LONG).show();
    }
}
