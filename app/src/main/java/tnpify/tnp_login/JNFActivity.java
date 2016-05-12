package tnpify.tnp_login;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class JNFActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jnf);
        Intent callerIntent = getIntent();
        final int compID = callerIntent.getIntExtra(getResources().getString(R.string.company_id), -1);
        final String compName = callerIntent.getStringExtra(getResources().getString(R.string.company_name));
        Company company = Company.getCompanyFromID(compID);
        String[] data = new String[] {company.name, Arrays.toString(company.locations)};
        ListView jnf = (ListView) findViewById(R.id.listViewJNF);
//        ArrayList<String> jnfData = new ArrayList<String>();
//        for(int i = 0; i < data.length; i++) {
//            jnfData.add(data[i]);
//        }
        jnf.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data));

        CollapsingToolbarLayout collToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(compName);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = applyToCompany(compID);
                Toast.makeText(getApplicationContext(), "Application " + (success ? "successful" : "failed"),
                        Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                builder.setTitle("Apply to " + compName)
////                        .setSingleChoiceItems(R.array.resumes, 0, new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                                // TODO: set resume here
////                            }
////                        })
//                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//                        .setPositiveButton(R.string.apply, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                boolean success = applyToCompany(compID);
//                                Toast.makeText(getApplicationContext(), "Application " + (success ? "successful" : "failed"),
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }).show();
            }
        });
    }

    public static boolean applyToCompany(int compID) {
        return false;
    }
}
