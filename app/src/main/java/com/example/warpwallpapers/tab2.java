package com.example.warpwallpapers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class tab2 extends Fragment  {
    private RecyclerView tab1;
    private adapter adapt;
    private ArrayList<modal>list;
    private ProgressBar loading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        loading = view.findViewById(R.id.loading);
        tab1 = view.findViewById(R.id.firstfragmet);
        list = new ArrayList<>();
        adapt = new adapter(getContext() , list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext() , 4);
        tab1.setLayoutManager(gridLayoutManager);
        tab1.setAdapter(adapt);
        getdata();

        return view;

    }

    public void getdata(){

        StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search?query=america?page=1&per_page=80", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.setVisibility(View.GONE);
                tab1.setVisibility(View.VISIBLE);

                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr = obj.getJSONArray("photos");

                    for(int i  = 0 ; i < arr.length() ; i++){
                        int id = arr.getJSONObject(i).getInt("id");
                        String medium = arr.getJSONObject(i).getJSONObject("src").getString("medium");
                        String orignal = arr.getJSONObject(i).getJSONObject("src").getString("portrait");
                        String photographer = arr.getJSONObject(i).getString("photographer");
                        int photgrapher_id = arr.getJSONObject(i).getInt("photographer_id");
                        String gg = Integer.toString(photgrapher_id);
                        list.add(new modal(id , orignal , medium , photographer , gg));
                        adapt.notifyDataSetChanged();
                    }

                }catch (JSONException e){
                    String p = e.toString();
                    Log.d("tt" , p);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String err = error.toString();
                Log.d("tt" , err);
                Toast.makeText(getContext(), "Please check your Connection", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("Authorization" , "3G8b0dJB2Dw0xRHyJQYwL4pIc6kzWErU5qTOOKJUSIkHXGshkaZ1JPSV");
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }

//    @Override
//    public void onClick(Integer position) {
//        Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
//    }
}