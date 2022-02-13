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

public class TambahMateriActivity extends AppCompatActivity {
    EditText edit_add_nama_materi;
    Button btn_add_materi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_materi);

        edit_add_nama_materi = findViewById(R.id.edit_add_nama_materi);
        btn_add_materi = findViewById(R.id.btn_add_materi);

        btn_add_materi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasi();
            }
        });
    }

    private void validasi(){
        final String add_nama_materi = edit_add_nama_materi.getText().toString().trim();

        if(add_nama_materi.isEmpty()){
            Toast.makeText(this, "Isi Kembali Nama Materi", Toast.LENGTH_SHORT).show();
        }else{
            confirmSimpanDataMateri();
        }
    }

    private void confirmSimpanDataMateri() {
        final String add_nama_materi = edit_add_nama_materi.getText().toString().trim();

        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert Data");
        builder.setMessage("Are you sure want to insert this data? " +
                "\n Materi : " + add_nama_materi);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpanDataMateri();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void simpanDataMateri() {
        final String add_nama_materi = edit_add_nama_materi.getText().toString().trim();

        class SimpanDataMateri extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahMateriActivity.this,
                        "Menyimpan Data", "Harap tunggu..",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_NAMA_MAT, add_nama_materi);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_ADD_MATERI, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(TambahMateriActivity.this, "pesan: " + message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TambahMateriActivity.this, MainActivity.class).putExtra("keyName", "materi"));
            }
        }
        SimpanDataMateri simpanDataMateri = new SimpanDataMateri();
        simpanDataMateri.execute();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}