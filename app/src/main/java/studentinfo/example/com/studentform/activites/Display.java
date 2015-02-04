package studentinfo.example.com.studentform.activites;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import studentinfo.example.com.studentform.R;
import studentinfo.example.com.studentform.entities.Student;
import studentinfo.example.com.studentform.util.AppConstants;
import studentinfo.example.com.studentform.util.GridViewAdapter;
import studentinfo.example.com.studentform.util.ListAdapter;


public class Display extends ActionBarActivity implements AppConstants {
    ListView listView;
    GridView displayGrid;
    ListAdapter adapter;
    GridViewAdapter gridAdapter;
    List<Student> student;
    Dialog dialog;
    Button dialogEdit;
    Button dialogDelete;
    Button dialogView;
    Button displayListButton;
    Button displayGridButton;
    Spinner spinner;
    public int clickPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        displayListButton = (Button) findViewById(R.id.button_list_view);
        displayGridButton = (Button) findViewById(R.id.button_grid_view);
        displayListButton.setBackgroundColor(0xff3b70cb);
        displayGridButton.setBackgroundColor(0xFFD6D7D7);
        listView = (ListView) findViewById(R.id.listView);
        displayGrid = (GridView) findViewById(R.id.gridView);
        spinner = (Spinner) findViewById(R.id.spinner_sort);
        ArrayList<String> spinnerList = new ArrayList<String>();
        spinnerList.add("Choose option..");
        spinnerList.add("Rollno");
        spinnerList.add("Name");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerList);
        student = new ArrayList<Student>();
        adapter = new ListAdapter(student, Display.this);
        gridAdapter = new GridViewAdapter(student, Display.this);
        displayGrid.setAdapter(gridAdapter);
        spinner.setAdapter(spinnerAdapter);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                displayDialog(position);

            }
        });
        displayGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayDialog(position);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (student.size() > 1) {
                    if (position == 1) {
                        Collections.sort(student, new Comparator<Student>() {
                            @Override
                            public int compare(Student lhs, Student rhs) {
                                return Integer.parseInt(lhs.rollno) - Integer.parseInt(rhs.rollno);
                            }
                        });
                        adapter.notifyDataSetChanged();
                        gridAdapter.notifyDataSetChanged();
                    }
                    if (position == 2) {
                        Collections.sort(student, new Comparator<Student>() {
                            @Override
                            public int compare(Student lhs, Student rhs) {
                                return lhs.name.compareTo(rhs.name);
                            }
                        });
                        adapter.notifyDataSetChanged();
                        gridAdapter.notifyDataSetChanged();
                    }
                    spinner.setSelection(0);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add:
                Intent intent = new Intent(this, EnterInfo.class);
                intent.putExtra("requestCode", RECEIVE_CODE_ADD);
                startActivityForResult(intent, REQUEST_CODE_ADD_STUDENT);
                break;
            case R.id.button_list_view:
                listView.setVisibility(View.VISIBLE);
                displayGrid.setVisibility(View.INVISIBLE);
                displayListButton.setBackgroundColor(0xff3b70cb);
                displayGridButton.setBackgroundColor(0xFFD6D7D7);
                break;
            case R.id.button_grid_view:

                listView.setVisibility(View.INVISIBLE);
                displayGrid.setVisibility(View.VISIBLE);
                displayGridButton.setBackgroundColor(0xff3b70cb);
                displayListButton.setBackgroundColor(0xFFD6D7D7);

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_STUDENT) {
            if (resultCode == RESULT_OK) {
                String getName = data.getStringExtra("name");
                String getRoll = data.getStringExtra("rollno");
                student.add(new Student(getName, getRoll));
                Collections.sort(student, new Comparator<Student>() {
                    @Override
                    public int compare(Student lhs, Student rhs) {
                        return lhs.name.compareTo(rhs.name);
                    }
                });
                adapter.notifyDataSetChanged();
                gridAdapter.notifyDataSetChanged();
            } else if (resultCode == RESULT_CANCELED) {

            }

        }
        if (requestCode == REQUEST_CODE_EDIT_STUDENT) {
            if (resultCode == RESULT_OK) {
                String getName = data.getStringExtra("name");
                String getRoll = data.getStringExtra("rollno");
                student.get(clickPosition).name = getName;
                student.get(clickPosition).rollno = getRoll;
                Collections.sort(student, new Comparator<Student>() {
                    @Override
                    public int compare(Student lhs, Student rhs) {
                        return lhs.name.compareTo(rhs.name);
                    }
                });
                adapter.notifyDataSetChanged();
                gridAdapter.notifyDataSetChanged();
            } else if (resultCode == RESULT_CANCELED) {

            }
        }


    }

    public void displayDialog(int postionRecieved) {
        final int position = postionRecieved;
        dialog = new Dialog(Display.this);
        dialog.setContentView(R.layout.dialogbox);
        dialog.setTitle("Select an option.");
        dialogDelete = (Button) dialog.findViewById(R.id.dialog_delete);
        dialogEdit = (Button) dialog.findViewById(R.id.dialog_edit);
        dialogView = (Button) dialog.findViewById(R.id.dialog_view);

        dialogDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.remove(position);
                adapter.notifyDataSetChanged();
                gridAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialogEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display.this, EnterInfo.class);
                clickPosition = position;
                intent.putExtra("requestCode", RECEIVE_CODE_EDIT);
                String sendName = student.get(position).name;
                String sendroll = student.get(position).rollno;
                intent.putExtra("name", sendName);
                intent.putExtra("roll", sendroll);
                startActivityForResult(intent, REQUEST_CODE_EDIT_STUDENT);
                dialog.dismiss();
            }
        });
        dialogView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display.this, EnterInfo.class);
                intent.putExtra("requestCode", RECEIVE_CODE_VIEW);
                String sendName = student.get(position).name;
                String sendroll = student.get(position).rollno;
                intent.putExtra("name", sendName);
                intent.putExtra("roll", sendroll);
                startActivityForResult(intent, REQUEST_CODE_VIEW_STUDENT);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}

