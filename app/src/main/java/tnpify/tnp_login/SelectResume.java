package tnpify.tnp_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SelectResume extends AppCompatActivity {

    public static final String SELECT_RESUME_FOR_COMPANY = "Select Resume for Company";
    public static final int RESULT_REQUEST_CODE = 18183;
    private static List<String> resumes = SpinnerList(DummyData.getResumes());
    Spinner resumeSelector;
    Company company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_resume);
        int compID = getIntent().getIntExtra(SELECT_RESUME_FOR_COMPANY, -1);
        company = DummyData.getCompanyFromID(compID);
        TextView title = (TextView) findViewById(R.id.textView);
        title.setText(company != null ? company.name : "Invalid Company");
        resumeSelector = (Spinner) findViewById(R.id.spinner_resume);
        ArrayAdapter<String> adapter = new SpinnerArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1, resumes);
        resumeSelector.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    private static List<String> SpinnerList(String[] list) {
        ArrayList<String> aList = new ArrayList<String>();
        aList.add("Select a resume");
        for(int i = 0; i < list.length; i++) {
            aList.add(list[i]);
        }
        return aList;
    }

    public void applyToComp(View v) {
        int selection = resumeSelector.getSelectedItemPosition();
        if(selection == 0 || selection == Spinner.INVALID_POSITION) {
            Toast.makeText(this, "Please select a resume", Toast.LENGTH_SHORT).show();
        } else {
            boolean success = DummyData.apply(company, (String) resumeSelector.getSelectedItem());
            setResult(success ? RESULT_OK : RESULT_CANCELED);
            finish();
        }
    }
}
