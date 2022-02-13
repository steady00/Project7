package com.steady.project7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class TambahInstrukturActivity extends AppCompatActivity {
    EditText edit_add_nama_instruktur, edit_add_email_instruktur, edit_add_hp_instruktur;
    Button btn_add_instruktur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_instruktur);

        edit_add_nama_instruktur = findViewById(R.id.edit_add_nama_instruktur);
        edit_add_email_instruktur = findViewById(R.id.edit_add_email_instruktur);
        edit_add_hp_instruktur = findViewById(R.id.edit_add_hp_instruktur);
        btn_add_instruktur = findViewById(R.id.btn_add_instruktur);

        btn_add_instruktur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasi();
            }
        });
    }

    private void validasi(){
        final String add_nama_instruktur = edit_add_nama_instruktur.getText().toString().trim();
        final String add_email_instruktur = edit_add_email_instruktur.getText().toString().trim();
        final String add_hp_instruktur = edit_add_hp_instruktur.getText().toString().trim();

        if(add_nama_instruktur.isEmpty()){
            Toast.makeText(this, "Isi Kembali Nama Instruktur", Toast.LENGTH_SHORT).show();
        }else if(add_email_instruktur.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(add_email_instruktur).matches()){
            Toast.makeText(this, "Isi Kembali Alamat Email", Toast.LENGTH_SHORT).show();
        } else if(add_hp_instruktur.isEmpty() || !Patterns.PHONE.matcher(add_hp_instruktur).matches()){
            Toast.makeText(this, "Isi Kembali Nomor Handphone", Toast.LENGTH_SHORT).show();
        }else{
            confirmSimpanDataInstruktur();
        }
    }

    private void confirmSimpanDataInstruktur() {
        final String add_nama_instruktur = edit_add_nama_instruktur.getText().toString().trim();
        final String add_email_instruktur = edit_add_email_instruktur.getText().toString().trim();
        final String add_hp_instruktur = edit_add_hp_instruktur.getText().toString().trim();

        //Confirmation alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert Data");
        builder.setMessage("Are you sure want to insert this data? " +
                "\n Nama : " + add_nama_instruktur +
                "\n Email: " + add_email_instruktur +
                "\n No Hp: " + add_hp_instruktur);
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpanDataInstruktur();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void simpanDataInstruktur() {
        final String add_nama_instruktur = edit_add_nama_instruktur.getText().toString().trim();
        final String add_email_instruktur = edit_add_email_instruktur.getText().toString().trim();
        final String add_hp_instruktur = edit_add_hp_instruktur.getText().toString().trim();

        class SimpanDataInstruktur extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahInstrukturActivity.this,
                        "Menyimpan Data", "Harap tunggu..",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_NAMA_INS, add_nama_instruktur);
                params.put(Konfigurasi.KEY_EMAIL_INS, add_email_instruktur);
                params.put(Konfigurasi.KEY_HP_INS, add_hp_instruktur);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_ADD_INSTRUKTUR, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(TambahInstrukturActivity.this, "pesan: " + message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TambahInstrukturActivity.this, MainActivity.class).putExtra("keyName", "instruktur"));
            }
        }
        SimpanDataInstruktur simpanDataInstruktur = new SimpanDataInstruktur();
        simpanDataInstruktur.execute();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}