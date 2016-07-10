package tnpify.tnp_login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Tushar on 7/10/2016.
 */
public class StatusViewAdapter extends ArrayAdapter<Application> {

    private LayoutInflater inflater;
    List<Application> applications = null;
    String username;
    public StatusViewAdapter(Context context, Application[] objects, String username) {
        super(context, R.layout.application_layout, objects);
        this.username = username;
        inflater = LayoutInflater.from(context);
        applications = Arrays.asList(objects);
    }

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param objects  The objects to represent in the ListView.
     */
    public StatusViewAdapter(Context context, List<Application> objects, String username) {
        super(context, R.layout.application_layout, objects);
        this.username = username;
        inflater = LayoutInflater.from(context);
        applications = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = inflater.inflate(R.layout.application_layout, parent, false);
        }
        TextView compNameView = (TextView) view.findViewById(R.id.application_company_name);
        TextView appStatusView = (TextView) view.findViewById(R.id.application_status);
        Button withdrawButton = (Button) view.findViewById(R.id.withdraw_application);
        Application app = applications.get(position);
        Company comp = DummyData.getCompanyFromID(app.compID);
        compNameView.setText(comp.name);
        appStatusView.setText(app.status.toString());
        if(comp.canWithdraw(username)) {
            withdrawButton.setEnabled(true);
        }
        return view;
    }
}
