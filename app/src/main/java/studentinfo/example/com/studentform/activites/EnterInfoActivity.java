package studentinfo.example.com.studentform.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import studentinfo.example.com.studentform.R;
import studentinfo.example.com.studentform.util.AppConstants;


public class EnterInfoActivity extends ActionBarActivity implements AppConstants {
    public static List<String> rollnoList = new ArrayList<String>();
    Button submit;
    Button cancel;
    EditText editName;
    EditText editRoll;
    EditText editPhone;
    EditText editAddress;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_info);
        editName = (EditText) findViewById(R.id.edit_name);
        editRoll = (EditText) findViewById(R.id.edit_roll);
        editPhone = (EditText) findViewById(R.id.edit_phone);
        editAddress = (EditText) findViewById(R.id.edit_address);
        submit = (Button) findViewById(R.id.button_submit);
        cancel = (Button) findViewById(R.id.button_cancel);
        textView = (TextView) findViewById(R.id.edithead_tv);

        String requestCode = getIntent().getStringExtra("requestCode");
        if (requestCode.equals(RECEIVE_CODE_EDIT)) {
            String recieveName = getIntent().getStringExtra("name");
            String recieveRollno = getIntent().getStringExtra("roll");
            String recievePhone = getIntent().getStringExtra("phone");
            String receiveAddress = getIntent().getStringExtra("address");
            editName.setText(recieveName);
            editRoll.setText(recieveRollno);
            rollnoList.remove(recieveRollno);
            editRoll.setClickable(false);
            editRoll.setFocusable(false);
            editPhone.setText(recievePhone);
            editAddress.setText(receiveAddress);
        } else if (requestCode.equals(RECEIVE_CODE_VIEW)) {
            String recieveName = getIntent().getStringExtra("name");
            String recieveRollno = getIntent().getStringExtra("roll");
            String recievePhone = getIntent().getStringExtra("phone");
            String receiveAddress = getIntent().getStringExtra("address");
            editName.setText(recieveName);
            editRoll.setText(recieveRollno);
            editPhone.setText(recievePhone);
            editAddress.setText(receiveAddress);
            editName.setFocusable(false);
            editName.setClickable(false);
            editRoll.setFocusable(false);
            editRoll.setClickable(false);
            editPhone.setFocusable(false);
            editPhone.setClickable(false);
            editAddress.setFocusable(false);
            editAddress.setClickable(false);
            submit.setVisibility(View.INVISIBLE);
            cancel.setText("Back");
            textView.setText("Details of Students:");
        }

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_submit:
                if (
                        editName.getText().toString().trim().matches("") ||
                                editRoll.getText().toString().trim().matches("") ||
                                editPhone.getText().toString().trim().matches("") ||
                                editAddress.getText().toString().trim().matches("")
                        ) {
                    Toast.makeText(this, "Fields are empty", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent();
                    if (rollnoList.contains(editRoll.getText().toString())) {
                        Toast.makeText(this, "Rollno already exists.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (editName.getText().toString().startsWith(" ") ||
                                editPhone.getText().toString().startsWith(" ") ||
                                editAddress.getText().toString().startsWith(" ")) {
                            Toast.makeText(this, "Enter details without space in front.", Toast.LENGTH_SHORT).show();
                        } else {
                            rollnoList.add(editRoll.getText().toString());
                            intent.putExtra("name", editName.getText().toString());
                            intent.putExtra("rollno", editRoll.getText().toString());
                            intent.putExtra("phone", editPhone.getText().toString());
                            intent.putExtra("address", editAddress.getText().toString());
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
