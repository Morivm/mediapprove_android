package com.example.mediapproved;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mediapproved.databinding.ActivityFinalDashboardBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;


import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class final_dashboard extends AppCompatActivity {
//    public TextView _login_name;

    ImageView profile_image_id;
//    TextView loginedFullname;

    private String strJson, apiUrl ="https://mediapprove.net/android/myprofile", resID;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityFinalDashboardBinding binding;

    private OkHttpClient client;
    private Response response;
    private RequestBody requestBody;
    private Request request;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.show();
        client = new OkHttpClient();
        new GetUserDataRequest().execute();

        Intent getlOGIN = getIntent();
        String RecievedValue = getlOGIN.getStringExtra("KEY_LOGIN_NAME");
//        String RecievedResidenceid = getlOGIN.getStringExtra("KEY_LOGIN_RESID");
        resID = getlOGIN.getStringExtra("KEY_LOGIN_RESID");


        binding = ActivityFinalDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarFinalDashboard.toolbar);
//        binding.appBarFinalDashboard.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow )
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_final_dashboard);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);
        TextView userName = headerView.findViewById(R.id.txt_logined);
        TextView adnrname = headerView.findViewById(R.id.txt_androidname);

        userName.setText(RecievedValue);
        adnrname.setText("Mediapprove");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.final_dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_final_dashboard);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public class GetUserDataRequest extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            request = new Request.Builder().url(apiUrl+"?residence_id="+resID).build();
            try {
                response = client.newCall(request).execute();


            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            try {
                strJson = response.body().string();
                updateUserData(strJson);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        private void updateUserData(String strJson) {

            TextView fullname = findViewById(R.id.txt_fname2);
//            TextView plan = findViewById(R.id.txt_plan);

            try{
                JSONArray parent = new JSONArray(strJson);
                JSONObject child = parent.getJSONObject(0);
//                String imgUrl = child.getString("residence_id");
                String profile_name_txt = child.getString("fullname");
                String plan_name_txt = child.getString("planname");
                fullname.setText(profile_name_txt);
//                plan.setText(plan_name_txt);
//
                progressDialog.hide();

            }catch (JSONException e) {

                e.printStackTrace();
            }

        }
    }
}