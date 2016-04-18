package com.example.fmb.dirtel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityPer extends AppCompatActivity {
    private final static String TAG = "fmbDirTel";
    // String that recive the JSON array with all de contacts (per)
    String dirpersJson;
    // List with all the detail of contacts
    ArrayList<DirPer> dirpers;

    ListView lvPer;                     // ListView to display Per
    ArrayAdapter<String> lvPerAdapter;  // adapter for handle data of lvPer
    ArrayList<String> listPer;          // List associated with lvPer

    // Fields in the screen
    EditText etName, etTelWork, etExt, etCell, etHome, etMail;
    TextView etOrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per);

        ListView lvPer = (ListView) findViewById(R.id.lvPer);
        etName = (EditText) findViewById(R.id.etName);
        etTelWork = (EditText) findViewById(R.id.etTelWork);
        etExt = (EditText) findViewById(R.id.etExt);
        etCell = (EditText) findViewById(R.id.etCell);
        etHome = (EditText) findViewById(R.id.etHome);
        etMail = (EditText) findViewById(R.id.eteMail);
        etOrg = (TextView) findViewById(R.id.etOrg);

        // Get the parameters passed by the intent
        Intent myIntent = getIntent(); // gets the previously created intent
        String org = myIntent.getStringExtra("Org"); // will return the Org selected
        dirpersJson = myIntent.getStringExtra("DirPer");
//        Log.e(TAG, "dirperJSON2: "+dirpersJson.substring(0,50)+" ORG: "+org);
        // Build the array (ArrayList<DirPer> dirpers;) from the parameter passed dirperJson
        builDirPerFromJSON(org);
        // form the list to display in the ListView
        listPer = new ArrayList<String>();
        for (DirPer per : dirpers) {
            listPer.add(per.getNomAP() + " " + per.getNomAM() + " " + per.getNom1() + " " + per.getNom2());
        }
        etOrg.setText(org);

        // inflate the line that will be in the LineView
        View v = LayoutInflater.from(this).inflate(R.layout.singlesmall_line, lvPer, false);

        // create and attache the adapter to the Listview
        lvPerAdapter = new SpecialAdapter(this,R.layout.singlesmall_line, listPer);
        lvPer.setAdapter(lvPerAdapter);

        // attach a listener to the ListView to react to item click events
        lvPer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                perDetails(position);
            }
        });
    }

    private void perDetails(int position){
        DirPer per = dirpers.get(position);
        etName.setText(per.getNomAP() + " " + per.getNomAM() + " " + per.getNom1() + " " + per.getNom2());
        etTelWork.setText(per.getTelDirecto());
        etExt.setText(per.getExtension());
        etCell.setText(per.getTelCel());
        etHome.setText(per.getTelCasa());
        etMail.setText(per.geteMail());
    }

    private void builDirPerFromJSON(String org) {
        try {
//            Log.e(TAG, "dirperJSON3: "+dirpersJson.substring(0,50));
            JSONArray jArry = new JSONArray(dirpersJson);
            JSONObject jobj;
            dirpers = new ArrayList<DirPer>();
            for (int i = 0; i < jArry.length(); i++) {
                jobj = jArry.getJSONObject(i);
                if (jobj.getString("Organiza").equals(org)) {
                    buildDirPerList(jobj);
                }
            }
            Log.e(TAG, "DirPer: " + dirpers.size());
        }

        catch(    JSONException e )  {
            Log.e(TAG, "JSONException in Per");
            e.printStackTrace();
        }
    }

    private void buildDirPerList(JSONObject jobj){
        DirPer dirper = new DirPer();
        try {
            dirper.setOrganiza(jobj.getString("Organiza"));
            dirper.setidPer(jobj.getString("idPer"));
            dirper.setComentarios(jobj.getString("Comentarios"));
            dirper.seteMail(jobj.getString("eMail"));
            dirper.setExtension(jobj.getString("Extension"));
            dirper.setFaxDirecto(jobj.getString("FaxDirecto"));
            dirper.setNom1(jobj.getString("Nom1"));
            dirper.setNom2(jobj.getString("Nom2"));
            dirper.setNomAM(jobj.getString("NomAM"));
            dirper.setNomAP(jobj.getString("NomAP"));
            dirper.setTelDirecto(jobj.getString("TelDirecto"));
            dirper.setPuesto(jobj.getString("Puesto"));
            dirper.setTelCasa(jobj.getString("TelCasa"));
            dirper.setTelCel(jobj.getString("TelCel"));

            dirper.setAccion("1");

            dirpers.add(dirper);        // add the row

        } catch (JSONException e){
            Log.e(TAG, "JSONException"); e.printStackTrace();}

    }
}
