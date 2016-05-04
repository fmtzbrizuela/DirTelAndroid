package com.example.fmb.dirtel;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityOrg extends AppCompatActivity {
    // Lists that contains all data of DirOrg and DirPer tables
    String dirorgsJSON;
    String dirpersJSON;
    ArrayList<DirOrg> dirorgs;

    ListView lvOrg;                     // ListView to display Org
    ArrayAdapter<String> lvOrgAdapter;  // adapter for handle data of lvOrg
    ArrayList<String> listOrg;          // List associated with lvOrg

    private final static String TAG = "fmbDirTel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org);

        ListView lvOrg = (ListView) findViewById(R.id.lvOrg);

        // Load DirTel Files
        // Get the parameters passed by the intent
        Intent myIntent = getIntent(); // gets the previously created intent
        String org = myIntent.getStringExtra("Org"); // will return the Org selected
        dirorgsJSON = myIntent.getStringExtra("DirOrg");
        dirpersJSON = myIntent.getStringExtra("DirPer");
        // create and attache the adapter to the Listview
        listOrg = new ArrayList<String>();
        builDirOrgFromJSON();
        // inflate the line that will be in the LineView
        View v = LayoutInflater.from(this).inflate(R.layout.singlesmall_line, lvOrg, false);
        // lvOrgAdapter = new ArrayAdapter<String>(this,R.layout.singlesmall_line, listOrg);
        lvOrgAdapter = new SpecialAdapter(this,R.layout.singlesmall_line, listOrg);
        lvOrg.setAdapter(lvOrgAdapter);

        // attach a listener to the ListView to react to item click events
        lvOrg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callOrg(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.UpData:
                Log.e(TAG, "Up Data");
                Intent myIntent = new Intent(this, ActivityMain.class);
                myIntent.putExtra("Sync","Sync"); // Just to say that you wanto sync
                startActivity(myIntent);
                return true;
        }
        return false;
    }

        private void callOrg(int index){
            Intent myIntent = new Intent(this, ActivityPer.class);
            myIntent.putExtra("Org", listOrg.get(index));
            myIntent.putExtra("DirPer",dirpersJSON);
            startActivity(myIntent);
//            Log.e(TAG, "callOrg" + "DirOrg: "+dirorgs.size());
//            Toast.makeText(getApplicationContext(), listOrg.get(index), Toast.LENGTH_SHORT).show();
        }


    private void builDirOrgFromJSON() {
        try {
            JSONArray jArry = new JSONArray(dirorgsJSON);
            JSONObject jobj;
            dirorgs = new ArrayList<DirOrg>();
            for (int i = 0; i < jArry.length(); i++) {
                jobj = jArry.getJSONObject(i);
                listOrg.add(jobj.getString("Organiza"));
                buildDirOrgList(jobj);
            }
            Log.e(TAG, "DirOrg: " + dirorgs.size());
        }

        catch(    JSONException e )  {
            Log.e(TAG, "JSONException in Org");
            e.printStackTrace();
        }
    }

    // Build a DirOrg object from a JSON object
    private void buildDirOrgList(JSONObject jobj){
        DirOrg dirorg = new DirOrg();
        try {
            dirorg.setOrganiza(jobj.getString("Organiza"));
            dirorg.setNombre(jobj.getString("Nombre"));
            dirorg.setCalle(jobj.getString("Calle"));
            dirorg.setColonia(jobj.getString("Colonia"));
            dirorg.setCP(jobj.getString("CP"));
            dirorg.setEstado(jobj.getString("Estado"));
            dirorg.setFax1(jobj.getString("Fax1"));
            dirorg.setFax2(jobj.getString("Fax2"));
            dirorg.setGiro(jobj.getString("Giro"));
            dirorg.setLocalidad(jobj.getString("Localidad"));
            dirorg.setTel1(jobj.getString("Tel1"));
            dirorg.setTel2(jobj.getString("Tel2"));
            dirorg.setwebsite(jobj.getString("website"));
            dirorg.setPais(jobj.getString("Pais"));

            dirorg.setAccion("1");

            dirorgs.add(dirorg);        // add the row

        } catch (JSONException e){
            Log.e(TAG, "JSONException"); e.printStackTrace();}

    }

}
