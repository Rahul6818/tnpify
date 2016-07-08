package tnpify.tnp_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FiltersActivity extends AppCompatActivity {
    EditText nameFilter, locationFilter, CTCFilter, CGPAFilter;
    Spinner typeSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        nameFilter = (EditText) findViewById(R.id.name_filter);
        locationFilter = (EditText) findViewById(R.id.location_filter);
        CTCFilter = (EditText) findViewById(R.id.ctc_filter);
        CGPAFilter = (EditText) findViewById(R.id.cgpa_filter);
        typeSpinner = (Spinner) findViewById(R.id.type_filter);
        List<String> types = new ArrayList<String>();
        types.add("Select");
        types.addAll(Arrays.asList(Company.CompanyType.types()));
        ArrayAdapter<String> typeList = new SpinnerArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1, types);

        typeList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeList);
    }

    public void filter(View v) {
        ArrayList<String> filters = new ArrayList<>();
        String name = nameFilter.getText().toString();
        String location = locationFilter.getText().toString();
        String ctc = CTCFilter.getText().toString();
        String cgpa = CGPAFilter.getText().toString();
        String type = (String) typeSpinner.getSelectedItem();
        if(name.trim().length() > 0) {
            filters.add("Name" + name);
        }
        if(location.trim().length() > 0) {
            filters.add("Location" + location);
        }
        if(ctc.trim().length() > 0) {
            filters.add("CTC" + ctc);
        }
        if(cgpa.trim().length() > 0) {
            filters.add("CGPA" + cgpa);
        }
        if(type.length() > 0 && !type.equals("Select")) {
            filters.add("Type" + type);
        }
        Toast.makeText(this, filters.toString(), Toast.LENGTH_SHORT).show();
        Intent filterIntent = new Intent();
        filterIntent.putStringArrayListExtra(CompanyList.FILTERS_EXTRA, filters);
        setResult(RESULT_OK, filterIntent);
        finish();
    }
}
