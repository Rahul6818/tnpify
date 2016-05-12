package tnpify.tnp_login;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class ApplyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        final Spinner selectCompany = (Spinner) findViewById(R.id.spinner_select_comp);
//        final Company selectedCompany = null;
        Button applyButton = (Button) findViewById(R.id.button_apply);
        Button jnfButton = (Button) findViewById(R.id.button_jnf);
        final ArrayAdapter<Company> applyList = new ArrayAdapter<Company>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        refreshData(applyList);
        applyList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCompany.setAdapter(applyList);
        jnfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Company selection = (Company) selectCompany.getSelectedItem();
                if(selection == null) {
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
                int compID = ((Company) selectCompany.getSelectedItem()).id;
                boolean success = JNFActivity.applyToCompany(compID);
                Toast.makeText(getApplicationContext(), "Application " + (success ? "successful" : "failed"), Toast.LENGTH_SHORT).show();
                refreshData(applyList);
            }
        });
    }

    private void refreshData(ArrayAdapter<Company> applyList) {
        //TODO: refresh list
        applyList.addAll(Company.getCompaniesFromIDs(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18}));
        applyList.notifyDataSetChanged();
    }
}
