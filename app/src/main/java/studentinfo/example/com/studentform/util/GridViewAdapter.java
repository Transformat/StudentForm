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

/**
 * Created by Sarthak on 22/01/2015.
 */
public class GridViewAdapter extends BaseAdapter {
    List<Student> students;
    Context ctx;

    public GridViewAdapter(List<Student> students, Context ctx) {
        this.students = students;
        this.ctx = ctx;
    }

    @Override
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
        ViewHolderForGrid holder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_grid_item, parent, false);
            holder = new ViewHolderForGrid(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolderForGrid) view.getTag();
        }

        holder.name.setText(students.get(position).name);
        holder.rollno.setText(students.get(position).rollno);

        return view;
    }
}

class ViewHolderForGrid {
    TextView name;
    TextView rollno;

    public ViewHolderForGrid(View view) {
        name = (TextView) view.findViewById(R.id.name_data_grid);
        rollno = (TextView) view.findViewById(R.id.roll_data_grid);
    }
}
