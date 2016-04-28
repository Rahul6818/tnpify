package tnpify.tnp_login;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class testing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
//        TextView textView =(TextView)findViewById(R.id.test_text1);
//        textView.setClickable(true);
//        textView.setMovementMethod(LinkMovementMethod.getInstance());
//        String link = "<a href='http://www.cse.iitd.ernet.in/~cs1120244/RAHUL_PATIDAR_CV.pdf'>Resume    </a>";
//        textView.setText(Html.fromHtml(link));
        final ListView listView = (ListView)findViewById(R.id.listView);
        String[] values = new String[] {"Analytics", "Core(Technical)", "Consulting","Finance", "Management"};

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);

                view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
                     @Override
                     public void run() {
                         list.remove(item);
                         adapter.notifyDataSetChanged();
                         view.setAlpha(1);
                     }
                });
            }
        });
    }
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    Intent intent = getIntent();
}
