package studentinfo.example.com.studentform.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

import studentinfo.example.com.studentform.R;
import studentinfo.example.com.studentform.entities.Student;


public class ListAdapter extends BaseAdapter {
    List<Student> students;
    Context ctx;

    public ListAdapter(List<Student> students, Context ctx) {
        this.students = students;
        this.ctx = ctx;
    }

    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.name.setText(students.get(position).name);
        holder.rollno.setText(students.get(position).rollno);

        return view;

    }
}

class ViewHolder {
    TextView name;
    TextView rollno;

    public ViewHolder(View view) {
        name = (TextView) view.findViewById(R.id.name_data);
        rollno = (TextView) view.findViewById(R.id.roll_data);
    }
}
