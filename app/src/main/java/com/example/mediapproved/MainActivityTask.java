package com.example.mediapproved;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivityTask extends AsyncTask<String, String, String> {

    Context context;
    MainActivityTask(Context ctx) {
        this.context=ctx;
    }


    @Override
    protected String doInBackground(String... strings) {

        String type         = strings[0];
        String username     = strings[1];
        String password     = strings[2];

//        String regUrl   = "http://54.251.75.129/fortest/android/sign_in";
        String regUrl   = "https://mediapprove.net/android/sign_in.php";



        if(type.equals("login")) {
            try {
                URL url = new URL(regUrl);

                try {



                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String insert_data = URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")+
                            "&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");

                    bufferedWriter.write(insert_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-1");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String result = "";
                    String line ="";
                    StringBuilder stringBuilder = new StringBuilder();
                    while (( line = bufferedReader.readLine())!=null ) {
                        stringBuilder.append(line).append("\n");

                    }

                    result = stringBuilder.toString();
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return  result;
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject json_data = new JSONObject(s);
            String response_type    = (json_data.getString("resp_type"));
            String response_message = (json_data.getString("resp_mess"));
            String response_res     = (json_data.getString("resp_resid"));


            if(response_type.equals("0")) {
                Toast.makeText(context, response_message, Toast.LENGTH_SHORT).show();
            }else{
//                Toast.makeText(context, "Welcome Back" + response_message,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, final_dashboard.class);

                i.putExtra("KEY_LOGIN_NAME", response_message);
                i.putExtra("KEY_LOGIN_RESID", response_res);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // if its and array of data then convert it to json array

//        if(s.contains("Success")) {
//            Toast.makeText(context, "Redirecting...",Toast.LENGTH_SHORT).show();
//

//        }else{
//            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
//        }

    }

//    public void gotoDashboard() {
//        Intent intent=new Intent( Dashboard.class); // redirecting to LoginActivity.
//        startActivity(intent);
//    }
}
