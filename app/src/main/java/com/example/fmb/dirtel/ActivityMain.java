package com.example.fmb.dirtel;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActivityMain extends AppCompatActivity {

    // Strings that contains all data of DirOrg and DirPer tables in format JSON
    String dirpersJSON;
    String dirorgsJSON;

    private int konAsyncTaskEnding = 0;

    private final static String TAG = "fmbDirTel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Load DirTel Files, first from Local Storage
        dirorgsJSON = getFileFromLocalStorage("Org");
        dirpersJSON = getFileFromLocalStorage("Per");
        if (dirorgsJSON.charAt(0) == '[' && dirorgsJSON.charAt(dirorgsJSON.length()-1) == ']' &&
                dirpersJSON.charAt(0) == '[' && dirpersJSON.charAt(dirpersJSON.length()-1) == ']') {
            konAsyncTaskEnding = 1;
            callActivityOrg();
            return;
        }

        // if there is not good data in local storage, load from the WebServers
        new LoadDirTelFiles().execute("Org");
        new LoadDirTelFiles().execute("Per");
    }

    private void callActivityOrg(){
        konAsyncTaskEnding++;
        if (konAsyncTaskEnding % 2 == 0) {
            Intent myIntent = new Intent(this, ActivityOrg.class);
            myIntent.putExtra("DirPer", dirpersJSON);
            myIntent.putExtra("DirOrg", dirorgsJSON);
            startActivity(myIntent);
        }
    }

    // Read Files from local storage for the case that Wifi fails
    private String getFileFromLocalStorage(String parametervalue){
        Log.e(TAG, "getFileFromLocalStorage");
        String fileName = ""; String str = "";
        if (parametervalue.equals("Org"))
            fileName = "DirOrgFile"; else fileName = "DirPerFile";
        File file = new File(this.getFilesDir(), fileName);
        int size = (int) file.length(); Log.e(TAG,parametervalue+" Size: "+size);
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            str = new String(bytes, "UTF-8"); // for UTF-8 encoding
        }catch (UnsupportedEncodingException ex){}
        return str;
    }

    private void  putFileIntoLocalStorage(String out, String parametervalue){
        Log.e(TAG, "putFileIntoLocalStorage");
        String fileName = "";
        if (out.charAt(0) != '[' || out.charAt(out.length()-1) != ']') {
            Log.e(TAG, "Error, BAD DATA");
            return;
        }// return if the string is bad form
        if (parametervalue.equals("Org"))
            fileName = "DirOrgFile"; else fileName = "DirPerFile";
        try
        {
            OutputStreamWriter fout=
                    new OutputStreamWriter(
                            openFileOutput(fileName, Context.MODE_PRIVATE));

            fout.write(out);
            fout.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }


    class LoadDirTelFiles extends AsyncTask<String, Void, String> {
        // Urls of webServices that give us a copy of DirOrg and DirPer tables
        private static final String URLorg = "http://192.168.1.155/DirTelWebServices/DirOrgSvc.svc/GetAllDirOrg";
        private static final String URLper = "http://192.168.1.155/DirTelWebServices/DirPerSvc.svc/GetAllDirPer";
        private String URLtarget = "http://192.168.1.50/DirTelWebServices/DirOrgSvc.svc/GetAllDirOrg";
        private String parametervalue = "";

//    @Override
//    protected void onPreExecute() {
//            Log.e(TAG, "onPreExecute"); mProgressBar.setVisibility(ProgressBar.VISIBLE);
//        }

        @Override
        protected String doInBackground(String... params) {
            Log.e(TAG, "doInBackground");
            parametervalue = params[0];
            if (params[0].equals("Org")) URLtarget = URLorg;
            else if (params[0].equals("Per")) URLtarget = URLper;
            else {Log.e(TAG, "Parameter must be Org or Per"); throw new WindowManager.BadTokenException(); }

            String data = "";
            HttpURLConnection httpUrlConnection = null;

            try {
                httpUrlConnection = (HttpURLConnection) new URL(URLtarget)
                        .openConnection();

                InputStream in = new BufferedInputStream(
                        httpUrlConnection.getInputStream());

                data = readStream(in);

            } catch (IOException exception) {
                data = getFileFromLocalStorage(parametervalue);  // if error, get last copy from storage
                Log.e(TAG, "IOException 1"); exception.printStackTrace();
            } finally {
                if (null != httpUrlConnection)
                    httpUrlConnection.disconnect();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "onPostExecute");
                if (result != null && result.length() > 0) {
                    // write a copy of files in format JSON
                    putFileIntoLocalStorage(result, parametervalue);
                    if (parametervalue.equals("Org")) {
                        dirorgsJSON = result;
                    } else {
                        dirpersJSON = result;
                    }
                } else {
                    Log.e(TAG, "NO DATA, something is Wrong!");
                }
            callActivityOrg();    // register the end to call ActivityOrg
        }


        // Read JSON stream and form a String
        private String readStream(InputStream in) {
            Log.e(TAG, "readStream");
            BufferedReader reader = null;
            StringBuffer data = new StringBuffer("");
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    data.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException 2");
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG, "IOException 3"); e.printStackTrace();
                    }
                }
            }
            return data.toString();
        }

    }
}
