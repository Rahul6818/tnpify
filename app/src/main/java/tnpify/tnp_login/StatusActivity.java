package tnpify.tnp_login;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class StatusActivity extends NavigationActivity {

    ListView applicationList;
    ArrayAdapter<Application> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        applicationList = (ListView) findViewById(R.id.status_list);
        refreshList();
    }

    void refreshList() {
        if(adapter != null) {
            adapter.notifyDataSetInvalidated();
        }
        List<Application> appList = DummyData.getAppList();
        adapter = new StatusViewAdapter(this, appList, LoginActivity.getUsername(this));
        applicationList.setAdapter(adapter);
    }

    public void withdrawApplication(View v) {
        LinearLayout parent = (LinearLayout) v.getParent();
        int position = applicationList.getPositionForView(parent);
        Application app = adapter.getItem(position);
        boolean success = DummyData.withdraw(DummyData.getCompanyFromID(app.compID));
        Toast.makeText(this, "Withdraw operation " + (success ? "successful" : "failed"), Toast.LENGTH_SHORT).show();
        refreshList();
    }
}
