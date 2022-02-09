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

public class DetailKelasActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edit_id_kelas, edit_tgl_mulai_kelas, edit_tgl_akhir_kelas, edit_nama_materi_kelas, edit_nama_instruktur_kelas;
    String id;
    Button btn_update_kelas, btn_delete_kelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kelas);

        edit_id_kelas = findViewById(R.id.edit_id_kelas);
        edit_tgl_mulai_kelas = findViewById(R.id.edit_tgl_mulai_kelas);
        edit_tgl_akhir_kelas = findViewById(R.id.edit_tgl_akhir_kelas);
        edit_nama_materi_kelas = findViewById(R.id.edit_nama_materi_kelas);
        edit_nama_instruktur_kelas = findViewById(R.id.edit_nama_instruktur_kelas);
        btn_delete_kelas = findViewById(R.id.btn_delete_kelas);
        btn_update_kelas = findViewById(R.id.btn_update_kelas);

        Intent receiveIntent = getIntent();
        id = receiveIntent.getStringExtra(Konfigurasi.INS_ID);
        edit_id_kelas.setText(id);

        // mengambil data JSON
        getJSON();
        btn_update_kelas.setOnClickListener(this);
        btn_delete_kelas.setOnClickListener(this);
    }

    private void getJSON() {
        // bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasActivity.this,
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_DETAIL_KELAS, id);
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

            String id_kelas = object.getString(Konfigurasi.TAG_JSON_ID_KLS);
            String tgl_masuk_kelas = object.getString(Konfigurasi.TAG_JSON_TGL_MULAI);
            String tgl_akhir_kelas = object.getString(Konfigurasi.TAG_JSON_TGL_AKHIR);
            String nama_materi_kelas = object.getString(Konfigurasi.TAG_JSON_KLS_NAMA_INS);
            String nama_instruktur_kelas = object.getString(Konfigurasi.TAG_JSON_KLS_NAMA_MAT);

            edit_id_kelas.setText(id_kelas);
            edit_tgl_mulai_kelas.setText(tgl_masuk_kelas);
            edit_tgl_akhir_kelas.setText(tgl_akhir_kelas);
            edit_nama_materi_kelas.setText(nama_materi_kelas);
            edit_nama_instruktur_kelas.setText(nama_instruktur_kelas);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btn_update_kelas){
            updateDataKelas();
        } else if( view == btn_delete_kelas){
            confirmDeleteDataKelas();
            //Toast.makeText(this, "Button Delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDeleteDataKelas() {
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
                DeleteDataKelas();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void DeleteDataKelas() {
        class DeleteDataPegawai extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasActivity.this,
                        "Menghapus Data", "Harap tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_DELETE_KELAS, id);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailKelasActivity.this,
                        "pesan: " + message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailKelasActivity.this, KelasFragment.class));
                //System.exit(1);
            }
        }
        DeleteDataPegawai deleteDataPegawai = new DeleteDataPegawai();
        deleteDataPegawai.execute();
    }

    private void updateDataKelas() {
        // data apa saja yang akan diubah

        final String id_kelas = edit_id_kelas.getText().toString().trim();
        final String tgl_masuk_kelas = edit_tgl_mulai_kelas.getText().toString().trim();
        final String tgl_akhir_kelas = edit_tgl_akhir_kelas.getText().toString().trim();
        final String nama_materi_kelas = edit_nama_materi_kelas.getText().toString().trim();
        final String nama_instruktur_kelas = edit_nama_instruktur_kelas.getText().toString().trim();

        class UpdateDataPegawai extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasActivity.this,
                        "Mengubah Data", "Harap tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_ID_KLS, id_kelas);
                params.put(Konfigurasi.KEY_TGL_MULAI, tgl_masuk_kelas);
                params.put(Konfigurasi.KEY_TGL_AKHIR, tgl_akhir_kelas);
                params.put(Konfigurasi.KEY_KLS_NAMA_INS, nama_materi_kelas);
                params.put(Konfigurasi.KEY_KLS_NAMA_MAT, nama_instruktur_kelas);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_UPDATE_KELAS, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailKelasActivity.this,
                        "pesan: " + message, Toast.LENGTH_SHORT).show();
                // redirect ke LihatDataActivity
                //System.exit(1);
                startActivity(new Intent(DetailKelasActivity.this, KelasFragment.class));
            }
        }
        UpdateDataPegawai updateDataPegawai = new UpdateDataPegawai();
        updateDataPegawai.execute();
    }
}