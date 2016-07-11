package tnpify.tnp_login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    long lastBackPressed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        DrawerLayout drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_navigation, null);
        RelativeLayout activityContent = (RelativeLayout) drawer.findViewById(R.id.content);
        getLayoutInflater().inflate(layoutResID, activityContent, true);

        Toolbar toolbar = (Toolbar) drawer.findViewById(R.id.toolbar_navigation);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) drawer.findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView drawerUsernameView = (TextView) header.findViewById(R.id.username_text_view);
        drawerUsernameView.setText(LoginActivity.getUsername(this));
        navigationView.setNavigationItemSelectedListener(this);
        super.setContentView(drawer);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (!drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.openDrawer(GravityCompat.START);
        } else {
            long timeNow = System.currentTimeMillis();
            if(timeNow - lastBackPressed > 2000) {
                lastBackPressed = timeNow;
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            } else {
                super.onBackPressed();
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notification) {
            Intent notify_intent = new Intent(NavigationActivity.this,NotificationActivity.class);
            NavigationActivity.this.startActivity(notify_intent);
            finish();
        } else if (id == R.id.nav_apply) {
            Intent apply_intent = new Intent(NavigationActivity.this,ApplyActivity.class);
            NavigationActivity.this.startActivity(apply_intent);
            finish();
        } else if (id == R.id.nav_resume) {
            Intent cv_intent = new Intent(NavigationActivity.this,ViewResume.class);
            NavigationActivity.this.startActivity(cv_intent);
            finish();
        } else if (id == R.id.nav_status) {
            Intent status_intent = new Intent(NavigationActivity.this,StatusActivity.class);
            NavigationActivity.this.startActivity(status_intent);
            finish();
        } else if (id == R.id.nav_company) {
            Intent comp_list_intent = new Intent(NavigationActivity.this,CompanyList.class);
            NavigationActivity.this.startActivity(comp_list_intent);
            finish();
        } else if (id == R.id.nav_events) {
            Intent event_intent = new Intent(NavigationActivity.this, EventsActivity.class);
            NavigationActivity.this.startActivity(event_intent);
            finish();
        } else if(id == R.id.nav_logout) {
            SharedPreferences loginSP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor logoutEdit = loginSP.edit();
            logoutEdit.putBoolean(getString(R.string.sp_logged_in), false);
            logoutEdit.commit();
            DummyData.logOut();
            Intent logoutIntent = new Intent(NavigationActivity.this, LoginActivity.class);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logoutIntent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
