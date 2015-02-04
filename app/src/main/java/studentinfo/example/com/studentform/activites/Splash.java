package studentinfo.example.com.studentform.activites;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import studentinfo.example.com.studentform.R;


public class Splash extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(Splash.this, studentinfo.example.com.studentform.activites.Display.class);
                    startActivity(i);
                    finish();
                }

            }
        });
        thread.start();
    }


}
