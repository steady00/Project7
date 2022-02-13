package com.steady.project7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class TambahKelasActivity extends AppCompatActivity {
    private EditText edit_mulai_kelas, edit_akhir_kelas;
    private Button btn_tambah_kelas;
    private Spinner spinner_ins, spinner_mat;
    private String JSON_STRING;
    private String JSON_STRING_INS;
    private String temp_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kelas);

        edit_mulai_kelas = findViewById(R.id.edit_mulai_kelas);
        edit_akhir_kelas = findViewById(R.id.edit_akhir_kelas);
        btn_tambah_kelas = findViewById(R.id.btn_tambah_kelas);
        spinner_ins = findViewById(R.id.spinner_ins);
        spinner_mat = findViewById(R.id.spinner_mat);

        btn_tambah_kelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasi();
            }
        });
        getJSON();
    }

    private void validasi() {
        final String mulai_kls = edit_mulai_kelas.getText().toString().trim();
        final String akhir_kls = edit_akhir_kelas.getText().toString().trim();

        if(mulai_kls.isEmpty()){
            Toast.makeText(this, "Tanggal Mulai Belum Diisi", Toast.LENGTH_SHORT).show();
        }else if(akhir_kls.isEmpty()){
            Toast.makeText(this, "Tanggal Akhir Belum Diisi!", Toast.LENGTH_SHORT).show();
        }else{
            confirmSimpanDataKelas();
        }
    }

    private void confirmSimpanDataKelas() {
        final String mulai_kls = edit_mulai_kelas.getText().toString().trim();
        final String akhir_kls = edit_akhir_kelas.getText().toString().trim();
        final String id_ins_kls = spinner_ins.getSelectedItem().toString().trim();
        final String id_mat_kls = spinner_mat.getSelectedItem().toString().trim();

        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert Data");
        builder.setMessage("Are you sure want to insert this data? " +
                "\nTgl. Mulai     : " + mulai_kls +
                "\nTgl. Akhir     : " + akhir_kls +
                "\nInstruktur     : " + id_ins_kls +
                "\nMateri         : " + id_mat_kls);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpanDataKelas();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(TambahKelasActivity.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String instrukturApi = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_INSTRUKTUR);
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_MATERI);
                temp_json = instrukturApi;
                Log.d("GetData", result);
                Log.d("GetDataIns", instrukturApi); //terpanggil
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                JSON_STRING = s;
                JSON_STRING_INS = temp_json;
                Log.d("Data_JSON_COM", JSON_STRING);
                Log.d("Data_JSON_INS", JSON_STRING_INS);

                JSONObject jsonObject = null;
                JSONObject jsonObjectIns = null;
                ArrayList<String> arrayList = new ArrayList<>();
                ArrayList<String> arrayListIns = new ArrayList<>();


                try {
                    jsonObject = new JSONObject(JSON_STRING);
                    jsonObjectIns = new JSONObject(JSON_STRING_INS);
                    JSONArray jsonArray = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
                    JSONArray jsonArrayIns = jsonObjectIns.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
                    Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String name = object.getString(Konfigurasi.TAG_JSON_NAMA_MAT);
                        arrayList.add(name);
                    }
                    for (int i = 0; i < jsonArrayIns.length(); i++) {
                        JSONObject object = jsonArrayIns.getJSONObject(i);
                        String nameInstruktur = object.getString(Konfigurasi.TAG_JSON_NAMA_INS);
                        arrayListIns.add(nameInstruktur);
                    }
                    Log.d("Isi:", String.valueOf(arrayListIns));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TambahKelasActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayList);
                ArrayAdapter<String> adapterIns = new ArrayAdapter<String>(TambahKelasActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayListIns);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapterIns.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner_mat.setAdapter(adapter);
                spinner_ins.setAdapter(adapterIns);
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }



    private void simpanDataKelas() {
        //fields apa saja yang akan disimpan
        final String mulai_kls = edit_mulai_kelas.getText().toString().trim();
        final String akhir_kls = edit_akhir_kelas.getText().toString().trim();
        final int id_ins_kls = spinner_ins.getSelectedItemPosition();
        final int id_mat_kls = spinner_mat.getSelectedItemPosition();


        class SimpanDataKelas extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahKelasActivity.this,
                        "Mengubah Data", "Harap tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                String result;

                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_TGL_MULAI, mulai_kls);
                params.put(Konfigurasi.KEY_TGL_AKHIR, akhir_kls);
                params.put(Konfigurasi.KEY_KLS_ID_INS, String.valueOf(id_ins_kls+1));
                params.put(Konfigurasi.KEY_KLS_ID_MAT, String.valueOf(id_mat_kls+1));
                HttpHandler handler = new HttpHandler();
                result = handler.sendPostRequest(Konfigurasi.URL_ADD_KELAS, params);
                Log.d("KIRIM ID INSTRUKTUR", String.valueOf(id_ins_kls+1));
                Log.d("KIRIM ID MATERI", String.valueOf(id_mat_kls+1));


                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                Toast.makeText(TambahKelasActivity.this, "pesan: " + message,
                        Toast.LENGTH_SHORT).show();
                Log.d("Hasil", message);
                startActivity(new Intent(TambahKelasActivity.this, MainActivity.class).putExtra("keyName", "kelas"));
            }
        }
        SimpanDataKelas simpanDataKelas = new SimpanDataKelas();
        simpanDataKelas.execute();
    }
}