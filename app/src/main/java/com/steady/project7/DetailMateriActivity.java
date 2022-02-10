package com.steady.project7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailMateriActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edit_id_materi, edit_nama_materi;
    String id;
    Button btn_update_materi, btn_delete_materi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_materi);

        edit_id_materi = findViewById(R.id.edit_id_materi);
        edit_nama_materi = findViewById(R.id.edit_nama_materi);
        btn_delete_materi = findViewById(R.id.btn_delete_materi);
        btn_update_materi = findViewById(R.id.btn_update_materi);

        Intent receiveIntent = getIntent();
        id = receiveIntent.getStringExtra(Konfigurasi.MAT_ID);
        edit_id_materi.setText(id);

        // mengambil data JSON
        getJSON();
        btn_update_materi.setOnClickListener(this);
        btn_delete_materi.setOnClickListener(this);
    }

    private void getJSON() {
        // bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailMateriActivity.this,
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_DETAIL_MATERI, id);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

    private void displayDetailData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String nama_materi = object.getString(Konfigurasi.TAG_JSON_NAMA_MAT);

            edit_nama_materi.setText(nama_materi);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btn_update_materi){
            updateDataMateri();
        } else if( view == btn_delete_materi){
            confirmDeleteDataMateri();
            //Toast.makeText(this, "Button Delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDeleteDataMateri() {
        // Confirmasi dengan Allert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menghapus Data");
        builder.setMessage("Apakah anda yakin menghapus data ini ?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DeleteDataMateri();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void DeleteDataMateri() {
        class DeleteDataPegawai extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailMateriActivity.this,
                        "Menghapus Data", "Harap tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_DELETE_MATERI, id);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailMateriActivity.this,
                        "pesan: " + message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailMateriActivity.this, MainActivity.class).putExtra("keyName", "materi"));
                //System.exit(1);
            }
        }
        DeleteDataPegawai deleteDataPegawai = new DeleteDataPegawai();
        deleteDataPegawai.execute();
    }

    private void updateDataMateri() {
        // data apa saja yang akan diubah

        final String id_materi = edit_id_materi.getText().toString().trim();
        final String nama_materi = edit_nama_materi.getText().toString().trim();

        class UpdateDataPegawai extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailMateriActivity.this,
                        "Mengubah Data", "Harap tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_ID_MAT, id_materi);
                params.put(Konfigurasi.KEY_NAMA_MAT, nama_materi);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_UPDATE_MATERI, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailMateriActivity.this,
                        "pesan: " + message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailMateriActivity.this, MainActivity.class).putExtra("keyName", "materi"));
            }
        }
        UpdateDataPegawai updateDataPegawai = new UpdateDataPegawai();
        updateDataPegawai.execute();
    }
}