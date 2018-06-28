package com.example.abel.medib2;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

import com.example.abel.lib.Authenticator;
import com.example.abel.lib.NetworkErrorAlert;
import com.example.abel.lib.Request.IndexRequest;
import com.example.abel.lib.Request.MedibRequest;
import com.example.abel.medib2.contents.MatchContent;

public class AdminMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,
        MatchFragment.OnListFragmentInteractionListener{
    private Authenticator auth;
    private IndexRequest request;
    private IndexObserver observer;

    String eventId = "";

    private class IndexObserver implements Observer {
        IndexRequest request;
        public IndexObserver(IndexRequest request){
            this.request = request;
        }

        @Override
        public void update(Observable o, Object arg) {
            if(request.equals(o)){
                if(request.whatHappened == IndexRequest.RESPONSE_OR_ERROR.RESPONSE) {
                    try {

                        JSONArray eventsJson = request.getDoc();
                        String[][] data = new String[eventsJson.length()][6];

                        //loop to set contents of the match view (match comes from server)
                        for (int i = 0; i < eventsJson.length(); i++) {
                            JSONObject eventJson = eventsJson.getJSONObject(i);

                            String team1_name = eventJson.getString("team1_name");
                            String team2_name = eventJson.getString("team2_name");
                            Double team1_odd = Double.parseDouble(eventJson.getString("team1_odd"));
                            Double team2_odd = Double.parseDouble(eventJson.getString("team2_odd"));
                            eventId = eventJson.getString("_id");
                            data[i][0] = team1_name;
                            data[i][1] = team2_name;
                            data[i][2] = team1_odd.toString();
                            data[i][3] = team2_odd.toString();
                            data[i][4] = eventId;
                            data[i][5] = auth.getToken();

                        }
                        MatchContent.ITEMS.clear();
                        for (int i = 0; i < eventsJson.length(); i++) {

                            MatchContent.Match match = new MatchContent.Match(data[i][0], data[i][1], data[i][2], data[i][3], data[i][4], data[i][5]);
                            MatchContent.addItem(match);
                        }

                        MatchFragment.notifyAdapter();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else{
                    int status = request.status();
                    if(status == 500){
                        NetworkErrorAlert.createDialog(request.getContext(), "Server error. Please try again.", request, null).show();
                    }
                    else if(status == 401){
                        Authenticator.getInstance(request.getContext()).removeToken();
                        Toast.makeText(request.getContext(), "You have to login first ", Toast.LENGTH_LONG).show();
                        checkLoggedIn();
                    }
                    else{
                        NetworkErrorAlert.createDialog(request.getContext(), "Network connection error. Please try again", request, null).show();
                    }
                }
            }
        }
    }

    public void checkLoggedIn(){
        auth = Authenticator.getInstance(this);
        if(auth.getToken() != null){
            if (!auth.isAdmin()) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
        else{
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        checkLoggedIn();

        MatchContent.Match match = new MatchContent.Match("Man United" , "Real Madrid" , "2.0" , "3.0" , "1" , "aaaaaaaaaaaaa") ;
        MatchContent.addItem(match);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment matchFragment=new MatchFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.pagerAdmin,matchFragment).commit();


        request = new IndexRequest(this);
        observer = new IndexObserver(request);
        request.addObserver(observer);

        JSONObject requestJson = null;

        request.execute(requestJson);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
                finishAffinity();
            else
                System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        switch(id){
            case R.id.nav_post:
                intent = new Intent(getApplicationContext(),PostEventActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_edit:
                intent=new Intent(getApplicationContext(),AdminMainActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_logout:
                logout();
                break;
//            case R.id.nav_manage:
//            case R.id.nav_share:
//            case R.id.nav_send:
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    @Override
    public void onMatchSelected(MatchContent.Match item) {

        Log.d("TAGG"," itwn Selected");
        Intent intent=new Intent(getApplicationContext(),EditEventActivity.class);
        Bundle bundle=new Bundle();

        bundle.putStringArrayList("Key",item.toArray());
        intent.putExtra("Match",bundle);
        startActivity(intent);
    }

    public void logout(){
        auth.removeToken();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}

