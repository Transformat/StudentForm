package studentinfo.example.com.studentform.entities;

/**
 * Created by Sarthak on 28/01/2015.
 */
public class Student {
    public String name;
    public String rollno;
    public String phoneNumber;
    public String address;

    public Student(String name, String rollno, String phoneNumber, String address) {
        this.name = name;
        this.rollno = rollno;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
