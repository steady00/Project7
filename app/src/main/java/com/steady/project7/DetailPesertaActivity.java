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

public class DetailPesertaActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edit_id_peserta, edit_nama_peserta, edit_email_peserta, edit_hp_peserta, edit_ins_peserta;
    String id;
    Button btn_update_peserta, btn_delete_peserta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_peserta);


        edit_id_peserta = findViewById(R.id.edit_id_instruktur);
        edit_nama_peserta = findViewById(R.id.edit_nama_instruktur);
        edit_email_peserta = findViewById(R.id.edit_email_instruktur);
        edit_hp_peserta = findViewById(R.id.edit_hp_instruktur);
        edit_ins_peserta = findViewById(R.id.edit_ins_peserta);
        btn_delete_peserta = findViewById(R.id.btn_delete_instruktur);
        btn_update_peserta = findViewById(R.id.btn_update_instruktur);

        Intent receiveIntent = getIntent();
        id = receiveIntent.getStringExtra(Konfigurasi.PST_ID);
        edit_id_peserta.setText(id);

        // mengambil data JSON
        getJSON();
        btn_update_peserta.setOnClickListener(this);
        btn_delete_peserta.setOnClickListener(this);
    }

    private void getJSON() {
        // bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailPesertaActivity.this,
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_DETAIL_PESERTA, id);
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

            String nama_peserta = object.getString(Konfigurasi.TAG_JSON_NAMA_PST);
            String email_peserta = object.getString(Konfigurasi.TAG_JSON_EMAIL_PST);
            String hp_peserta = object.getString(Konfigurasi.TAG_JSON_HP_PST);
            String ins_peserta = object.getString(Konfigurasi.TAG_JSON_INSTANSI_PST);

            edit_nama_peserta.setText(nama_peserta);
            edit_email_peserta.setText(email_peserta);
            edit_hp_peserta.setText(hp_peserta);
            edit_ins_peserta.setText(ins_peserta);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btn_update_peserta){
            updateDataPeserta();
        } else if( view == btn_delete_peserta){
            confirmDeleteDataPeserta();
            //Toast.makeText(this, "Button Delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDeleteDataPeserta() {
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
                DeleteDataPeserta();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void DeleteDataPeserta() {
        class DeleteDataPegawai extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailPesertaActivity.this,
                        "Menghapus Data", "Harap tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_DELETE_PESERTA, id);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailPesertaActivity.this,
                        "pesan: " + message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailPesertaActivity.this, PesertaFragment.class));
                //System.exit(1);
            }
        }
        DeleteDataPegawai deleteDataPegawai = new DeleteDataPegawai();
        deleteDataPegawai.execute();
    }

    private void updateDataPeserta() {
        // data apa saja yang akan diubah

        final String id_peserta = edit_id_peserta.getText().toString().trim();
        final String nama_peserta = edit_nama_peserta.getText().toString().trim();
        final String email_peserta = edit_email_peserta.getText().toString().trim();
        final String hp_peserta = edit_hp_peserta.getText().toString().trim();
        final String ins_peserta = edit_ins_peserta.getText().toString().trim();

        class UpdateDataPegawai extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailPesertaActivity.this,
                        "Mengubah Data", "Harap tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_ID_PST, id_peserta);
                params.put(Konfigurasi.KEY_NAMA_PST, nama_peserta);
                params.put(Konfigurasi.KEY_EMAIL_PST, email_peserta);
                params.put(Konfigurasi.KEY_HP_PST, hp_peserta);
                params.put(Konfigurasi.KEY_INSTANSI_PST, ins_peserta);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_UPDATE_PESERTA, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailPesertaActivity.this,
                        "pesan: " + message, Toast.LENGTH_SHORT).show();
                // redirect ke LihatDataActivity
                //System.exit(1);
                startActivity(new Intent(DetailPesertaActivity.this, PesertaFragment.class));
            }
        }
        UpdateDataPegawai updateDataPegawai = new UpdateDataPegawai();
        updateDataPegawai.execute();
    }


}