package tnpify.tnp_login;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompanyList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);
        final ListView listView = (ListView)findViewById(R.id.listView);
        String[] values = new String[] {"Analytics", "Core(Technical)", "Consulting","Finance", "Management", "Another Company", "yet another company", "lo and behold", "one more company", "Company 1", "Company2", "Company3", "company 4", "company 5", "company 6", "company 7", "company 8", "company 9", "company 0"};

        final ArrayList<Company> list = new ArrayList<Company>();
        for (int i = 0; i < 20; ++i) {
            list.add(new Company(i, "company" + i, new String[] {"loc" + (2 * i), "loc" + (2 * i + 1)}));
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,int position, long id) {
//                final String item = (String) parent.getItemAtPosition(position);
//
//                view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
//                     @Override
//                     public void run() {
//                         list.remove(item);
//                         adapter.notifyDataSetChanged();
//                         view.setAlpha(1);
//                     }
//                });
                Intent jnfIntent = new Intent(CompanyList.this, JNFActivity.class);
                int compID = ((Company) parent.getItemAtPosition(position)).id;
                String compName = ((Company) parent.getItemAtPosition(position)).name;
                jnfIntent.putExtra(getResources().getString(R.string.company_id), compID);
                jnfIntent.putExtra(getResources().getString(R.string.company_name), compName);
                CompanyList.this.startActivity(jnfIntent);
                overridePendingTransition(0,R.anim.abc_fade_out);
            }
        });
    }
    private class StableArrayAdapter extends ArrayAdapter<Company> {

        HashMap<Company, Integer> mIdMap = new HashMap<Company, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<Company> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            Company item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    Intent intent = getIntent();
}
