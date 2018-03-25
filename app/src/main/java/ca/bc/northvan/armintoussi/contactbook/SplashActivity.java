package ca.bc.northvan.armintoussi.contactbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ca.bc.northvan.armintoussi.contactbook.Activities.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(SplashActivity.this, HomeActivity.class));

        finish();
    }
}
