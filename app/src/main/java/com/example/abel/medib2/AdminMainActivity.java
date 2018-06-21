package com.example.abel.medib2;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abel.medib2.contents.MatchContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdminMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,
        MatchFragment.OnListFragmentInteractionListener{

    String eventId = "";
    String token = "";
    String cash = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

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


        final String url = "http://10.42.0.1:3000/user/index";
        String jsonstring = "{'name':'name'}";  //empty json (no josn needed in request)
        JSONObject requestJson = null;

        try {
            requestJson  = new JSONObject(jsonstring);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(AdminMainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, requestJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray eventsJson = response.getJSONArray("doc");
//                    token = response.getString("token");
                    String nameFromServer = null ;
//                    token = eventsJson.getJSONObject(1).getString("token");
                    Intent intent = getIntent();
                    Bundle b = intent.getExtras();
                    token = b.getString("tok");
                    cash = b.getString("cash");
                    String [][] data = new String[eventsJson.length()][6];


                    for (int i =0; i <eventsJson.length() ; i++) {  //loop to set contents of the match view (match comes from server)
                        Log.d("medib" , "inside for loop");
                        JSONObject eventJson = eventsJson.getJSONObject(i);

                        String team1_name = eventJson.getString("team1_name");
                        String team2_name = eventJson.getString("team2_name");
                        Double team1_odd  = Double.parseDouble(eventJson.getString("team1_odd"));
                        Double team2_odd  = Double.parseDouble(eventJson.getString("team2_odd"));
                        eventId = eventJson.getString("_id");
                        nameFromServer = team1_name;
                        data[i][0] = team1_name;
                        data[i][1] = team2_name;
                        data[i][2] = team1_odd.toString();
                        data[i][3] = team2_odd.toString();
                        data[i][4] = eventId;
                        data[i][5] = token;




                        Toast.makeText(getApplicationContext() ,"t1" +team1_name + " t2 " +team2_name , Toast.LENGTH_LONG).show();
                        //MatchContent.Match match = new MatchContent.Match(team1_name , team2_name , team1_odd.toString() , team2_odd.toString() , eventId , token) ;
                        //MatchContent.addItem(match);
                    }
                    if(MatchContent.ITEMS ==null){
                        for( int i =0 ; i<eventsJson.length() ;i++) {
                            MatchContent.Match match = new MatchContent.Match(data[i][0] , data[i][1] , data[i][2] , data[i][3] , data[i][4] , data[i][5]) ;
                            MatchContent.addItem(match);
                        }

                    }
                    else {
                        MatchContent.ITEMS.clear();
                        for( int i =0 ; i<eventsJson.length() ;i++) {
                            MatchContent.Match match = new MatchContent.Match(data[i][0] , data[i][1] , data[i][2] , data[i][3] , data[i][4] , data[i][5]) ;
                            MatchContent.addItem(match);
                        }


                    }

                    /*for( int i =0 ; i<eventsJson.length() ;i++) {
                        MatchContent.Match match = new MatchContent.Match(data[i][0] , data[i][1] , data[i][2] , data[i][3] , data[i][4] , data[i][5]) ;
                        MatchContent.addItem(match);
                    }
                    */
                    MatchFragment.notifyAdapter();
                      /*
                    Intent i = new Intent("android.intent.action.Testig");
                    Bundle extra = new Bundle();
                    extra.putString("name" , nameFromServer);
                    i.putExtras(extra);
                    startActivity(i);
                    */


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("inside response" , error.getMessage());

            }

        }
        );
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_post) {

            Bundle b= new Bundle();
            b.putString("cash" , cash);

            Intent intent=new Intent(getApplicationContext(),PostEventActivity.class);
            intent.putExtras(b);
            startActivity(intent);

        } else if (id == R.id.nav_edit){
            Bundle b= new Bundle();
            b.putString("cash" , cash);

                Intent intent=new Intent(getApplicationContext(),AdminMainActivity.class);
                intent.putExtras(b);
                startActivity(intent);


        } else if (id == R.id.nav_logout){
            onBackPressed();
            super.onBackPressed();



        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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

}

