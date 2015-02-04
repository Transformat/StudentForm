package studentinfo.example.com.studentform.activites;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import studentinfo.example.com.studentform.R;
import studentinfo.example.com.studentform.util.AppConstants;


public class EnterInfo extends ActionBarActivity implements AppConstants {
    Button submit;
    Button cancel;
    EditText editText1;
    EditText editText2;
    TextView textView;
    public static List<String> rollno = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_info);
        editText1 = (EditText) findViewById(R.id.edit1);
        editText2 = (EditText) findViewById(R.id.edit2);

        submit = (Button) findViewById(R.id.button_submit);
        cancel = (Button) findViewById(R.id.button_cancel);
        textView = (TextView) findViewById(R.id.edithead_tv);

        String requestCode = getIntent().getStringExtra("requestCode");
        if (requestCode.equals(RECEIVE_CODE_EDIT)) {
            String name = getIntent().getStringExtra("name");
            String rollno = getIntent().getStringExtra("roll");
            editText1.setText(name);
            editText2.setText(rollno);
        } else if (requestCode.equals(RECEIVE_CODE_VIEW)) {
            String name = getIntent().getStringExtra("name");
            String rollno = getIntent().getStringExtra("roll");
            editText1.setText(name);
            editText2.setText(rollno);
            editText1.setFocusable(false);
            editText1.setClickable(false);
            editText2.setFocusable(false);
            editText2.setClickable(false);
            submit.setVisibility(View.INVISIBLE);
            cancel.setText("Back");
            textView.setText("Details of Students:");
        }

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_submit:
                if (editText1.getText().toString().trim().matches("") || editText2.getText().toString().trim().matches("")) {


                    Toast.makeText(this, "Fields are empty", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent();
                    if (rollno.contains(editText2.getText().toString())) {
                        Toast.makeText(this, "Rollno already exists.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (editText1.getText().toString().startsWith(" ")) {
                            Toast.makeText(this, "Enter name without space in front.", Toast.LENGTH_SHORT).show();
                        } else {
                            rollno.add(editText2.getText().toString());
                            intent.putExtra("name", editText1.getText().toString().toUpperCase());
                            intent.putExtra("rollno", editText2.getText().toString());
                            Toast.makeText(this, "Deltails Entered Sucessfully", Toast.LENGTH_LONG).show();
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                }
                break;
            case R.id.button_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
            default:
                break;
        }
    }


}
