package tnpify.tnp_login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

public class ApplyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        Spinner selectCompany = (Spinner) findViewById(R.id.spinner_select_comp);
        Button applyButton = (Button) findViewById(R.id.button_apply);
        Button jnfButton = (Button) findViewById(R.id.button_jnf);
    }
}
