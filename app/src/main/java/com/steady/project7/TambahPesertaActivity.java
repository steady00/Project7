package com.steady.project7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class TambahPesertaActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edit_add_nama_peserta, edit_add_email_peserta, edit_add_hp_peserta, edit_add_ins_peserta;
    Button btn_add_peserta, btn_lihat_peserta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_peserta);

        edit_add_nama_peserta = findViewById(R.id.edit_add_nama_peserta);
        edit_add_email_peserta = findViewById(R.id.edit_add_email_peserta);
        edit_add_hp_peserta = findViewById(R.id.edit_add_hp_peserta);
        edit_add_ins_peserta = findViewById(R.id.edit_add_ins_peserta);
        btn_add_peserta = findViewById(R.id.btn_add_peserta);
        btn_lihat_peserta = findViewById(R.id.btn_lihat_peserta);

        btn_add_peserta.setOnClickListener(this);
        btn_lihat_peserta.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btn_add_peserta){
            simpanDataPeserta();
        } else if (view == btn_lihat_peserta){
            startActivity(new Intent(TambahPesertaActivity.this, MainActivity.class).putExtra("keyName", "peserta"));
        }

    }

    private void simpanDataPeserta() {
        final String add_nama_peserta = edit_add_nama_peserta.getText().toString().trim();
        final String add_email_peserta = edit_add_email_peserta.getText().toString().trim();
        final String add_hp_peserta = edit_add_hp_peserta.getText().toString().trim();
        final String add_ins_peserta = edit_add_ins_peserta.getText().toString().trim();

        class SimpanDataPeserta extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahPesertaActivity.this,
                        "Menyimpan Data", "Harap tunggu..",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_NAMA_PST, add_nama_peserta);
                params.put(Konfigurasi.KEY_EMAIL_PST, add_email_peserta);
                params.put(Konfigurasi.KEY_HP_PST, add_hp_peserta);
                params.put(Konfigurasi.KEY_INSTANSI_PST, add_ins_peserta);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_ADD_PESERTA, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(TambahPesertaActivity.this, "pesan: " + message, Toast.LENGTH_SHORT).show();
                clearText();
            }
        }
        SimpanDataPeserta simpanDataPeserta = new SimpanDataPeserta();
        simpanDataPeserta.execute();
    }

    private void clearText() {
        edit_add_nama_peserta.setText("");
        edit_add_email_peserta.setText("");
        edit_add_hp_peserta.setText("");
        edit_add_ins_peserta.setText("");
        edit_add_nama_peserta.requestFocus();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

}