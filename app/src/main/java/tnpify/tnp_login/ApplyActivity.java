package tnpify.tnp_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class ApplyActivity extends NavigationActivity {
    Button applyButton, jnfButton;
    Spinner selectCompany;
    ArrayAdapter<Company> applyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        selectCompany = (Spinner) findViewById(R.id.spinner_select_comp);
        applyButton = (Button) findViewById(R.id.button_apply);
        jnfButton = (Button) findViewById(R.id.button_jnf);
        refreshData(applyList);
        jnfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Company selection = (Company) selectCompany.getSelectedItem();
                if(selection == null || selectCompany.getSelectedItemPosition() == 0) {
                    Toast.makeText(getApplicationContext(), "Please select a company first", Toast.LENGTH_SHORT).show();
                } else {

                    Intent jnfIntent = new Intent(ApplyActivity.this, JNFActivity.class);
                    long compID = selection.id;
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
                long compID = selection.id;
                JNFActivity.applyToCompany(ApplyActivity.this, compID);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext(), "Application " + (resultCode == RESULT_OK ? "successful" : "failed"), Toast.LENGTH_SHORT).show();
        refreshData(applyList);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshData(applyList);
    }

    private void refreshData(ArrayAdapter<Company> applyList) {
        if(applyList != null) {
            applyList.notifyDataSetInvalidated();
        }
        List<Company> appliedList = DummyData.openCompanies();
        appliedList.add(0, new Company("Select a Company", null, null));
        applyList = new SpinnerArrayAdapter<Company>(this, android.R.layout.simple_spinner_item, android.R.id.text1, appliedList);
        applyList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCompany.setAdapter(applyList);
    }
}
