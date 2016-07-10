package tnpify.tnp_login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

public class JNFActivity extends AppCompatActivity {

    FloatingActionButton fab;
    Company company;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jnf);
        Intent callerIntent = getIntent();
        final int compID = callerIntent.getIntExtra(getResources().getString(R.string.company_id), -1);
        final String compName = callerIntent.getStringExtra(getResources().getString(R.string.company_name));
        company = DummyData.getCompanyFromID(compID);
        String locations = Arrays.toString(company.locations);
        String[] data = new String[] {locations.substring(1, locations.length() - 1),
        company.type.toString(), Float.toString(company.cgpa), Integer.toString(company.ctc), company.deadline.toString()};
        ListView jnf = (ListView) findViewById(R.id.listViewJNF);
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        adap.addAll(data);
        jnf.setAdapter(adap);

        CollapsingToolbarLayout collToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(compName);


        fab = (FloatingActionButton) findViewById(R.id.fab_jnf);
        username = LoginActivity.getUsername(getApplicationContext());
        refreshFabView();
    }

    public void fabClick(View view) {
        if(company.isOpen(username)) {
            applyToCompany(this, company.id);
        } else {
            withdawFromCompany(company);
        }
        refreshFabView();
    }

    private void withdawFromCompany(Company company) {
        boolean success = DummyData.withdraw(company);
        Toast.makeText(this, "Withdraw operation " + (success ? "successful" : "failed"), Toast.LENGTH_SHORT).show();
        refreshFabView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshFabView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext(), "Application " + (resultCode == RESULT_OK ? "successful" : "failed"), Toast.LENGTH_SHORT).show();
        refreshFabView();
    }

    void refreshFabView() {
        if(company.isOpen(username)) {
            fab.setVisibility(View.VISIBLE);
            fab.setImageResource(R.drawable.ic_tick);
        } else if(company.canWithdraw(username)) {
            fab.setVisibility(View.VISIBLE);
            fab.setImageResource(R.drawable.ic_cross);
        } else {
            fab.setVisibility(View.GONE);
        }
    }

    public static void applyToCompany(Activity context, int compID) {
        Intent intent = new Intent(context, SelectResume.class);
        intent.putExtra(SelectResume.SELECT_RESUME_FOR_COMPANY, compID);
        context.startActivityForResult(intent, SelectResume.RESULT_REQUEST_CODE);
    }
}
