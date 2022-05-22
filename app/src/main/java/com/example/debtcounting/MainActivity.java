package com.example.debtcounting;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editUser, editAmount, selectedUser;
    RecyclerView recyclerview;
    Button UserBtn, AmountBtn, RefreshBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editUser = findViewById(R.id.editTextTextPersonName);
        editAmount = findViewById(R.id.editTextNumber);
        selectedUser = findViewById(R.id.editTextTextPersonName2);
        recyclerview = findViewById(R.id.recyclerView);
        UserBtn = findViewById(R.id.addButton);
        AmountBtn = findViewById(R.id.AmountButton);
        RefreshBtn = findViewById(R.id.refreshButo);

        Show();

        RefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show();
                Toast.makeText(MainActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        UserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editUser.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Enter User Name First", Toast.LENGTH_SHORT).show();
                }
                else{
                    AddUser(editUser.getText().toString());
                    makeText(MainActivity.this, "Add User Successfully", LENGTH_SHORT).show();

                }
                Show();
            }
        });

        AmountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedUser.getText().toString().isEmpty() || editAmount.getText().toString().isEmpty()){
                    makeText(MainActivity.this, "Please fill selected User and Edit Amount", LENGTH_SHORT).show();
                }
                else{
                    SumEarn(selectedUser.getText().toString(), editAmount.getText().toString());
                    makeText(MainActivity.this, "User's Account Added", LENGTH_SHORT).show();
                }
                Show();
            }
        });
    }

    private void Show(){
        String url = "http://192.168.254.104/Cuizon_FExam/DisplayUser.php";

        RequestQueue q = Volley.newRequestQueue(MainActivity.this);

        StringRequest r = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject oh = new JSONObject(response);

                            JSONArray userArray = oh.getJSONArray("User");

                            int size = userArray.length();

                            String[] A1 = new String[size];
                            String[] A2 = new String[size];
                            String[] A3 = new String[size];


                            for(int i=0; i<size; i++) {

                                JSONObject ob = userArray.getJSONObject(i);

                                A1[i] = ob.getString("userid");
                                A2[i] = ob.getString("username");
                                A3[i] = ob.getString("amount");


                                LinearLayoutManager layout = new LinearLayoutManager(MainActivity.this);
                                recyclerview.setLayoutManager(layout);
                                recyclerview.setAdapter(new MainAdapter(MainActivity.this,A1,A2,A3));
                            }
                        }
                        catch(Exception e){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Volley Error Response! \n\n" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        q.add(r);
    }

    private void AddUser(String user){
        String url = "http://192.168.254.104/Cuizon_FExam/InsertUser.php";

        RequestQueue q = Volley.newRequestQueue(MainActivity.this);

        StringRequest r = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        makeText(getBaseContext(), "Failed to Add User. \n\n" + error.getMessage(), LENGTH_SHORT).show();
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user", user);
                return param;
            }
        };
        q.add(r);
    }

    private void SumEarn(String user, String amount){
        String url = "http://192.168.254.104/Cuizon_FExam/InsertAmount.php";

        RequestQueue q = Volley.newRequestQueue(MainActivity.this);

        StringRequest r = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        makeText(getBaseContext(), "Failed to Add User. \n\n" + error.getMessage(), LENGTH_SHORT).show();
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user", user);
                param.put("amount", amount);
                return param;
            }
        };
        q.add(r);
    }

}