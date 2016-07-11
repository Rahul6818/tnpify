package tnpify.tnp_login;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyList extends NavigationActivity {

    public static final String FILTERS_EXTRA = "filters";
    private static final String SORT_BY = "sort_by";
    private static final String ASCENDING_SORT = "ascending_sort";
    static final int FILTERS_REQUEST = 1;
    int sortBy = Company.NAME_SORT;
    boolean ascendingSort = true;
    List<Company> list;
    ListView listView;
    FloatingActionButton fabFilter;
    StableArrayAdapter adapter;
    SharedPreferences sp;
    MenuItem lastSort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);
        sp = getPreferences(MODE_PRIVATE);
        sortBy = sp.getInt(SORT_BY, Company.NAME_SORT);
        ascendingSort = sp.getBoolean(ASCENDING_SORT, true);

        listView = (ListView)findViewById(R.id.listView);
//        String[] values = new String[] {"Analytics", "Core(Technical)", "Consulting","Finance", "Management", "Another Company", "yet another company", "lo and behold", "one more company", "Company 1", "Company2", "Company3", "company 4", "company 5", "company 6", "company 7", "company 8", "company 9", "company 0"};


        refreshListView();
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
                long compID = ((Company) parent.getItemAtPosition(position)).id;
                String compName = ((Company) parent.getItemAtPosition(position)).name;
                jnfIntent.putExtra(getResources().getString(R.string.company_id), compID);
                jnfIntent.putExtra(getResources().getString(R.string.company_name), compName);
                CompanyList.this.startActivity(jnfIntent);
            }
        });

        fabFilter = (FloatingActionButton) findViewById(R.id.fab_filter);
        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent filterIntent = new Intent(CompanyList.this, FiltersActivity.class);
                CompanyList.this.startActivityForResult(filterIntent, FILTERS_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FILTERS_REQUEST && resultCode == RESULT_OK) {
            refreshListView();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.company_list_menu, menu);

        int arrowRes = ascendingSort ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down;

        switch(sortBy) {
            case Company.CGPA_SORT:
                MenuItem cgpaItem = menu.findItem(R.id.cgpa_sort_action);
                cgpaItem.setIcon(arrowRes);
                lastSort = cgpaItem;
                break;
            case Company.CTC_SORT:
                MenuItem ctcItem = menu.findItem(R.id.ctc_sort_action);
                ctcItem.setIcon(arrowRes);
                lastSort = ctcItem;
                break;
            case Company.DEADLINE_SORT:
                MenuItem deadlineItem = menu.findItem(R.id.deadline_sort_action);
                deadlineItem.setIcon(arrowRes);
                lastSort = deadlineItem;
                break;
            default:
                MenuItem nameItem = menu.findItem(R.id.name_sort_action);
                nameItem.setIcon(arrowRes);
                lastSort = nameItem;
                break;
        }

        return true;
    }

    void sortMenu(MenuItem item) {
        int sortField = Company.NAME_SORT;
        switch(item.getItemId()) {
            case R.id.ctc_sort_action:
                sortField = Company.CTC_SORT;
                break;
            case R.id.cgpa_sort_action:
                sortField = Company.CGPA_SORT;
                break;
            case R.id.deadline_sort_action:
                sortField = Company.DEADLINE_SORT;
                break;
            default:
                sortField = Company.NAME_SORT;
                break;
        }
        lastSort.setIcon(0);
        SharedPreferences.Editor spedit = sp.edit();
        if(sortField == sortBy) {
            spedit.putBoolean(ASCENDING_SORT, !ascendingSort);
        } else {
            spedit.putInt(SORT_BY, sortField);
            spedit.putBoolean(ASCENDING_SORT, true);
        }
        spedit.commit();
        ascendingSort = sp.getBoolean(ASCENDING_SORT, true);
        sortBy = sp.getInt(SORT_BY, Company.NAME_SORT);
        refreshListView();
        int arrowRes = ascendingSort ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down;
        item.setIcon(arrowRes);
        lastSort = item;
    }

    List<String> getFilters() {
        SharedPreferences filterSP = getSharedPreferences("FiltersActivity", MODE_PRIVATE);
        Map<String, ?> filters = filterSP.getAll();
        List<String> ret = new ArrayList<>();
        if(filters.containsKey(FiltersActivity.NAME_FILTER)) {
            ret.add("Name" + filters.get(FiltersActivity.NAME_FILTER).toString());
        }
        if(filters.containsKey(FiltersActivity.CGPA_FILTER)) {
            ret.add("CGPA" + filters.get(FiltersActivity.CGPA_FILTER).toString());
        }
        if(filters.containsKey(FiltersActivity.CTC_FILTER)) {
            ret.add("CTC" + filters.get(FiltersActivity.CTC_FILTER).toString());
        }
        if(filters.containsKey(FiltersActivity.LOCATION_FILTER)) {
            ret.add("Location" + filters.get(FiltersActivity.LOCATION_FILTER).toString());
        }
        if(filters.containsKey(FiltersActivity.TYPE_FILTER)) {
            ret.add("Type" + filters.get(FiltersActivity.TYPE_FILTER).toString());
        }
        return ret;
    }

    void refreshListView() {
        list = Company.filtered(DummyData.getAllCompanies(), getFilters(), sortBy, ascendingSort);
        if(adapter != null) {
            adapter.notifyDataSetInvalidated();
        }
        adapter = new StableArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }



    private class StableArrayAdapter extends ArrayAdapter<Company> {

        HashMap<Company, Integer> mIdMap = new HashMap<>();

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

}
