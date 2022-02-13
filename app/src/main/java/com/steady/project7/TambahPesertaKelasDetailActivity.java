package com.steady.project7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TambahPesertaKelasDetailActivity extends AppCompatActivity {
    //String id;
    Spinner add_id_peserta,add_id_kelas;
    Button btn_tambah_peserta_kelas;
    private String JSON_STRING;
    private String JSON_STRING_KLS;
    private String temp_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_peserta_kelas_detail);

        //Intent receiveIntent = getIntent();
        //id = receiveIntent.getStringExtra(Konfigurasi.KELAS_KLS_DETAIL_ID);
        //add_id_kelas.setText(id);

        add_id_kelas = findViewById(R.id.add_id_kelas);
        add_id_peserta = findViewById(R.id.add_id_peserta);
        btn_tambah_peserta_kelas = findViewById(R.id.btn_tambah_peserta_kelas);

        btn_tambah_peserta_kelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanDataKelas();
            }
        });

        getJSON();

    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(TambahPesertaKelasDetailActivity.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String resultkelas = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_KELAS);
                String resultpeserta = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_PESERTA);
                temp_json = resultkelas;
                Log.d("GetData", resultpeserta);
                return resultpeserta;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();


                JSON_STRING_KLS = temp_json;
                JSON_STRING = s;
                Log.d("Data_JSON_COM", JSON_STRING);

                JSONObject jsonObjectKls = null;
                JSONObject jsonObject = null;
                ArrayList<String> arrayListKls = new ArrayList<>();
                ArrayList<String> arrayList = new ArrayList<>();


                try {
                    jsonObjectKls = new JSONObject(JSON_STRING_KLS);
                    jsonObject = new JSONObject(JSON_STRING);
                    JSONArray jsonArray = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
                    JSONArray jsonArrayKls = jsonObjectKls.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
                    Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String name = object.getString(Konfigurasi.TAG_JSON_NAMA_PST);
                        arrayList.add(name);
                    }
                    for (int i = 0; i < jsonArrayKls.length(); i++) {
                        JSONObject object = jsonArrayKls.getJSONObject(i);
                        String nameInstruktur = object.getString(Konfigurasi.TAG_JSON_ID_KLS);
                        arrayListKls.add(nameInstruktur);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ArrayAdapter<String> adapterKls = new ArrayAdapter<String>(TambahPesertaKelasDetailActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayListKls);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TambahPesertaKelasDetailActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapterKls.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                add_id_kelas.setAdapter(adapterKls);
                add_id_peserta.setAdapter(adapter);
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void simpanDataKelas() {
        //fields apa saja yang akan disimpan
        final String id_kelas = add_id_kelas.getSelectedItem().toString().trim();
        final int id_peserta = add_id_peserta.getSelectedItemPosition();


        class SimpanDataKelas extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahPesertaKelasDetailActivity.this,
                        "Mengubah Data", "Harap tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                String result;

                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_ID_KLS, id_kelas);
                params.put(Konfigurasi.KEY_ID_PST, String.valueOf(id_peserta+1));
                HttpHandler handler = new HttpHandler();
                result = handler.sendPostRequest(Konfigurasi.URL_ADD_PESERTA_DETAIL_KELAS, params);
                Log.d("KIRIM ID KELAS", id_kelas);
                Log.d("KIRIM ID PESERTA", String.valueOf(id_peserta+1));


                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                Toast.makeText(TambahPesertaKelasDetailActivity.this, "pesan: " + message,
                        Toast.LENGTH_SHORT).show();
                Log.d("Hasil", message);
                startActivity(new Intent(TambahPesertaKelasDetailActivity.this, MainActivity.class).putExtra("keyName", "detail kelas"));

            }
        }
        SimpanDataKelas simpanDataKelas = new SimpanDataKelas();
        simpanDataKelas.execute();
    }

}