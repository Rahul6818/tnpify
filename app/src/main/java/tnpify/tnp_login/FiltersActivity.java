package tnpify.tnp_login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FiltersActivity extends AppCompatActivity {
    public static final String NAME_FILTER = "name_filter", LOCATION_FILTER = "location_filter", CTC_FILTER = "ctc_filter", CGPA_FILTER = "cgpa_filter", TYPE_FILTER = "type_filter";
    EditText nameFilter, locationFilter, CTCFilter, CGPAFilter;
    Spinner typeFilter;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        sp = getPreferences(MODE_PRIVATE);
        nameFilter = (EditText) findViewById(R.id.name_filter);
        locationFilter = (EditText) findViewById(R.id.location_filter);
        CTCFilter = (EditText) findViewById(R.id.ctc_filter);
        CGPAFilter = (EditText) findViewById(R.id.cgpa_filter);
        typeFilter = (Spinner) findViewById(R.id.type_filter);
        List<String> types = new ArrayList<String>();
        types.add("Select");
        types.add("None");
        types.addAll(Arrays.asList(Company.CompanyType.types()));
        ArrayAdapter<String> typeList = new SpinnerArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1, types);
        typeList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeFilter.setAdapter(typeList);

        nameFilter.setText(sp.getString(NAME_FILTER, ""));
        locationFilter.setText(sp.getString(LOCATION_FILTER, ""));
        CTCFilter.setText(sp.getString(CTC_FILTER, ""));
        CGPAFilter.setText(sp.getString(CGPA_FILTER, ""));
        int typeIndex = getTypeIndex();
        typeFilter.setSelection(typeIndex);
    }

    private int getTypeIndex() {
        String type = sp.getString(TYPE_FILTER, "Select");
        for(int i = 0; i < Company.CompanyType.types().length; i++) {
            if(type.equalsIgnoreCase(Company.CompanyType.types()[i])) {
                return i + 2;
            }
        }
        return 0;
    }

    public void filter(View v) {
        SharedPreferences.Editor spedit = sp.edit();
        ArrayList<String> filters = new ArrayList<>();
        String type = (String) typeFilter.getSelectedItem();
        spedit.putString(NAME_FILTER, nameFilter.getText().toString());
        spedit.putString(LOCATION_FILTER, locationFilter.getText().toString());
        spedit.putString(CTC_FILTER, CTCFilter.getText().toString());
        spedit.putString(CGPA_FILTER, CGPAFilter.getText().toString());
        if(!type.equalsIgnoreCase("Select")) {
            spedit.putString(TYPE_FILTER, type);
        }
        spedit.commit();
        setResult(RESULT_OK);
        finish();
    }
}
