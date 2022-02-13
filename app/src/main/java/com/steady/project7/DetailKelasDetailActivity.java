package com.steady.project7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailKelasDetailActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView list_view_detail_kelas_detail;
    private String JSON_STRING;
    String id;
    //FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kelas_detail);

        list_view_detail_kelas_detail = findViewById(R.id.list_view_detail_kelas_detail);
        list_view_detail_kelas_detail.setOnItemClickListener(this);
        //floatingActionButton = findViewById(R.id.btn_add_kelas);
        //floatingActionButton.setOnClickListener(this);

        Intent receiveIntent = getIntent();
        id = receiveIntent.getStringExtra(Konfigurasi.KELAS_KLS_DETAIL_ID);

        getJSON();
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasDetailActivity.this,
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_DETAIL_KELAS_DETAIL,id);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA_JSON: ", JSON_STRING);


                // menampilkan data dalam bentuk list view
                displayAllData();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayAllData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            Log.d("DATA_JSON: ", JSON_STRING);


            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_kls_detail_kelas = object.getString(Konfigurasi.TAG_JSON_ID_KELAS_KELAS_DETAIL);
                String id_detail_kls_detail_kelas = object.getString(Konfigurasi.TAG_JSON_ID_KELAS_DETAIL_KELAS_DETAIL);
                String nama_peserta = object.getString(Konfigurasi.TAG_JSON_NAMA_PST_KELAS_DETAIL);


                HashMap<String, String> detail_kelas_detail = new HashMap<>();
                detail_kelas_detail.put(Konfigurasi.TAG_JSON_ID_KELAS_KELAS_DETAIL, id_kls_detail_kelas);
                detail_kelas_detail.put(Konfigurasi.TAG_JSON_ID_KELAS_DETAIL_KELAS_DETAIL, id_detail_kls_detail_kelas);
                detail_kelas_detail.put(Konfigurasi.TAG_JSON_NAMA_PST_KELAS_DETAIL, nama_peserta);

                // ubah format json menjadi array list
                list.add(detail_kelas_detail);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // adapter untuk meletakan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list,
                R.layout.activity_list_detail_kelas_detail,
                new String[]{Konfigurasi.TAG_JSON_ID_KELAS_KELAS_DETAIL, Konfigurasi.TAG_JSON_ID_KELAS_DETAIL_KELAS_DETAIL, Konfigurasi.TAG_JSON_NAMA_PST_KELAS_DETAIL},
                new int[]{R.id.txt_id_kls_detail_kelas, R.id.txt_id_detail_kelas_detail_kelas, R.id.txt_nama_pst_kelas_detail}
        );
        list_view_detail_kelas_detail.setAdapter(adapter);
    }

    /*@Override
    public void onClick(View view) {
        startActivity(new Intent(DetailKelasDetailActivity.this, TambahPesertaKelasDetailActivity.class));
    }*/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(DetailKelasDetailActivity.this, DeleteKelasDetailActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String detailklsid= map.get(Konfigurasi.TAG_JSON_ID_KELAS_DETAIL_KELAS_DETAIL).toString();
        myIntent.putExtra(Konfigurasi.KELAS_KLS_DETAIL_ID, detailklsid);
        startActivity(myIntent);
    }
}