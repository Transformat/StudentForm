package studentinfo.example.com.studentform.activites;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import studentinfo.example.com.studentform.util.DbController;
import studentinfo.example.com.studentform.util.ListAdapter;


public class DisplayActivity extends Activity implements AppConstants {
    public int clickPosition;
    DbController dbController;
    ListView listView;
    GridView displayGrid;
    ListAdapter adapter;

    List<Student> student;
    Dialog dialog;
    Button getDetails;
    Button dialogEdit;
    Button dialogDelete;
    Button dialogView;
    Button displayListButton;
    Button displayGridButton;
    Spinner spinner;
    int position;

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
        student = new ArrayList<>();
        adapter = new ListAdapter(student, DisplayActivity.this);
        spinner.setAdapter(spinnerAdapter);
        displayGrid.setAdapter(adapter);
        listView.setAdapter(adapter);
        dbController = new DbController(DisplayActivity.this);
        new AsyncProcess().execute("view");

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
                if (adapter.students.size() > 1) {
                    if (position == 1) {
                        Collections.sort(adapter.students, new Comparator<Student>() {
                            @Override
                            public int compare(Student lhs, Student rhs) {
                                return Integer.parseInt(lhs.rollno) - Integer.parseInt(rhs.rollno);
                            }
                        });
                        adapter.notifyDataSetChanged();

                    }
                    if (position == 2) {
                        Collections.sort(adapter.students, new Comparator<Student>() {
                            @Override
                            public int compare(Student lhs, Student rhs) {
                                return lhs.name.compareTo(rhs.name);
                            }
                        });
                        adapter.notifyDataSetChanged();
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
                Intent intent = new Intent(this, EnterInfoActivity.class);
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
            default:
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
                String getPhone = data.getStringExtra("phone");
                String getAddress = data.getStringExtra("address");
                adapter.students.add(new Student(getName, getRoll, getPhone, getAddress));
                Collections.sort(student, new Comparator<Student>() {
                    @Override
                    public int compare(Student lhs, Student rhs) {
                        return lhs.name.compareTo(rhs.name);
                    }
                });
                dbController.dbOpen();
                dbController.dbInsert(new Student(getName, getRoll, getPhone, getAddress));
                dbController.dbClose();
                adapter.notifyDataSetChanged();
            } else if (resultCode == RESULT_CANCELED) {
            }

        }
        if (requestCode == REQUEST_CODE_EDIT_STUDENT) {
            if (resultCode == RESULT_OK) {
                String getName = data.getStringExtra("name");
                String getRoll = data.getStringExtra("rollno");
                String getPhone = data.getStringExtra("phone");
                String getAddress = data.getStringExtra("address");
                adapter.students.get(clickPosition).name = getName;
                adapter.students.get(clickPosition).rollno = getRoll;
                adapter.students.get(clickPosition).phoneNumber = getPhone;
                adapter.students.get(clickPosition).address = getAddress;
                Collections.sort(adapter.students, new Comparator<Student>() {
                    @Override
                    public int compare(Student lhs, Student rhs) {
                        return lhs.name.compareTo(rhs.name);
                    }
                });
                dbController.dbOpen();
                dbController.dbUpdate(adapter.students.get(clickPosition));
                dbController.dbClose();
                adapter.notifyDataSetChanged();

            } else if (resultCode == RESULT_CANCELED) {
            }
        }


    }

    public void displayDialog(int postionRecieved) {
        position = postionRecieved;
        dialog = new Dialog(DisplayActivity.this);
        dialog.setContentView(R.layout.dialogbox);
        dialog.setTitle("Select an option.");
        dialogDelete = (Button) dialog.findViewById(R.id.dialog_delete);
        dialogEdit = (Button) dialog.findViewById(R.id.dialog_edit);
        dialogView = (Button) dialog.findViewById(R.id.dialog_view);

        dialogDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncProcess().execute("delete");
                dialog.dismiss();
            }
        });
        dialogEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPosition = position;
                Intent intent = new Intent(DisplayActivity.this, EnterInfoActivity.class);
                intent.putExtra("requestCode", RECEIVE_CODE_EDIT);
                intent.putExtra("name", adapter.students.get(position).name);
                intent.putExtra("roll", adapter.students.get(position).rollno);
                intent.putExtra("phone", adapter.students.get(position).phoneNumber);
                intent.putExtra("address", adapter.students.get(position).address);
                startActivityForResult(intent, REQUEST_CODE_EDIT_STUDENT);
                dialog.dismiss();
            }
        });
        dialogView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, EnterInfoActivity.class);
                intent.putExtra("requestCode", RECEIVE_CODE_VIEW);
                intent.putExtra("name", adapter.students.get(position).name);
                intent.putExtra("roll", adapter.students.get(position).rollno);
                intent.putExtra("phone", adapter.students.get(position).phoneNumber);
                intent.putExtra("address", adapter.students.get(position).address);
                startActivityForResult(intent, REQUEST_CODE_VIEW_STUDENT);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    class AsyncProcess extends android.os.AsyncTask<String, String, String> {
        ProgressDialog progressDialog = new ProgressDialog(DisplayActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading Data...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String... params) {
            if (params[0].equals("view")) {
                dbController.dbOpen();
                adapter.students = dbController.dbView();
                dbController.dbClose();
            } else if (params[0].equals("delete")) {
                dbController.dbOpen();
                int resultDelete = dbController.dbDelete(adapter.students.get(position));
                dbController.dbClose();
                if (resultDelete == 1) {
                    adapter.students.remove(position);
                }
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }
    }
}

