package tnpify.tnp_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;

public class ApplyActivity extends AppCompatActivity {
    Button applyButton, jnfButton;
    Spinner selectCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        selectCompany = (Spinner) findViewById(R.id.spinner_select_comp);
        applyButton = (Button) findViewById(R.id.button_apply);
        jnfButton = (Button) findViewById(R.id.button_jnf);
        final ArrayAdapter<Company> applyList = new SpinnerArrayAdapter<Company>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        refreshData(applyList);
        applyList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCompany.setAdapter(applyList);
        jnfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Company selection = (Company) selectCompany.getSelectedItem();
                if(selection == null || selectCompany.getSelectedItemPosition() == 0) {
                    Toast.makeText(getApplicationContext(), "Please select a company first", Toast.LENGTH_SHORT).show();
                } else {

                    Intent jnfIntent = new Intent(ApplyActivity.this, JNFActivity.class);
                    int compID = selection.id;
                    String compName = selection.name;
                    jnfIntent.putExtra(getResources().getString(R.string.company_id), compID);
                    jnfIntent.putExtra(getResources().getString(R.string.company_name), compName);
                    ApplyActivity.this.startActivity(jnfIntent);
                }
            }
        });
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Company selection = (Company) selectCompany.getSelectedItem();
                if(selection == null || selectCompany.getSelectedItemPosition() == 0) {
                    Toast.makeText(getApplicationContext(), "Please select a company first", Toast.LENGTH_SHORT).show();
                    return;
                }
                int compID = selection.id;
                boolean success = JNFActivity.applyToCompany(compID);
                Toast.makeText(getApplicationContext(), "Application " + (success ? "successful" : "failed"), Toast.LENGTH_SHORT).show();
                refreshData(applyList);
            }
        });
    }

    private void refreshData(ArrayAdapter<Company> applyList) {
        //TODO: refresh list
        applyList.add(new Company(-1, "Select a Company", null, null));
        applyList.addAll(DummyData.getAllCompanies());
        applyList.notifyDataSetChanged();
    }
}
