package com.steady.project7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailInstrukturActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edit_id_instruktur, edit_nama_instruktur, edit_email_instruktur, edit_hp_instruktur;
    String id;
    Button btn_update_instruktur, btn_delete_instruktur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_instruktur);

        edit_id_instruktur = findViewById(R.id.edit_id_instruktur);
        edit_nama_instruktur = findViewById(R.id.edit_nama_instruktur);
        edit_email_instruktur = findViewById(R.id.edit_email_instruktur);
        edit_hp_instruktur = findViewById(R.id.edit_hp_instruktur);
        btn_delete_instruktur = findViewById(R.id.btn_delete_instruktur);
        btn_update_instruktur = findViewById(R.id.btn_update_instruktur);

        Intent receiveIntent = getIntent();
        id = receiveIntent.getStringExtra(Konfigurasi.INS_ID);
        edit_id_instruktur.setText(id);

        // mengambil data JSON
        getJSON();
        btn_update_instruktur.setOnClickListener(this);
        btn_delete_instruktur.setOnClickListener(this);
    }

    private void getJSON() {
        // bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailInstrukturActivity.this,
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_DETAIL_INSTRUKTUR, id);
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

            String nama_instruktur = object.getString(Konfigurasi.TAG_JSON_NAMA_INS);
            String email_instruktur = object.getString(Konfigurasi.TAG_JSON_EMAIL_INS);
            String hp_instruktur = object.getString(Konfigurasi.TAG_JSON_HP_INS);

            edit_nama_instruktur.setText(nama_instruktur);
            edit_email_instruktur.setText(email_instruktur);
            edit_hp_instruktur.setText(hp_instruktur);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btn_update_instruktur){
            validasi();
        } else if( view == btn_delete_instruktur){
            confirmDeleteDataInstruktur();
        }
    }

    private void confirmDeleteDataInstruktur() {
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
                DeleteDataInstruktur();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void DeleteDataInstruktur() {
        class DeleteDataPegawai extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailInstrukturActivity.this,
                        "Menghapus Data", "Harap tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_DELETE_INSTRUKTUR, id);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailInstrukturActivity.this,
                        "pesan: " + message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailInstrukturActivity.this, MainActivity.class).putExtra("keyName", "instruktur"));
            }
        }
        DeleteDataPegawai deleteDataPegawai = new DeleteDataPegawai();
        deleteDataPegawai.execute();
    }


    private void validasi(){
        final String nama_instruktur = edit_nama_instruktur.getText().toString().trim();
        final String email_instruktur = edit_email_instruktur.getText().toString().trim();
        final String hp_instruktur = edit_hp_instruktur.getText().toString().trim();

        if(nama_instruktur.isEmpty()){
            Toast.makeText(this, "Isi Kembali Nama Instruktur", Toast.LENGTH_SHORT).show();
        }else if(email_instruktur.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email_instruktur).matches()){
            Toast.makeText(this, "Isi Kembali Alamat Email", Toast.LENGTH_SHORT).show();
        } else if(hp_instruktur.isEmpty() || !Patterns.PHONE.matcher(hp_instruktur).matches()){
            Toast.makeText(this, "Isi Kembali Nomor Handphone", Toast.LENGTH_SHORT).show();
        }else{
            confirmUpdateDataInstruktur();
        }
    }

    private void confirmUpdateDataInstruktur() {
        final String id_instruktur = edit_id_instruktur.getText().toString().trim();
        final String nama_instruktur = edit_nama_instruktur.getText().toString().trim();
        final String email_instruktur = edit_email_instruktur.getText().toString().trim();
        final String hp_instruktur = edit_hp_instruktur.getText().toString().trim();

        //Confirmation altert dialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Insert Data");
        builder.setMessage("Are you sure want to change this data? " +
                "\n Id : " + id_instruktur +
                "\n Nama: " + nama_instruktur +
                "\n Email: " + email_instruktur +
                "\n No. Hp: " + hp_instruktur);
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateDataInstruktur();
            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateDataInstruktur() {
        // data apa saja yang akan diubah
        final String id_instruktur = edit_id_instruktur.getText().toString().trim();
        final String nama_instruktur = edit_nama_instruktur.getText().toString().trim();
        final String email_instruktur = edit_email_instruktur.getText().toString().trim();
        final String hp_instruktur = edit_hp_instruktur.getText().toString().trim();

        class UpdateDataPegawai extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailInstrukturActivity.this,
                        "Mengubah Data", "Harap tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_ID_INS, id_instruktur);
                params.put(Konfigurasi.KEY_NAMA_INS, nama_instruktur);
                params.put(Konfigurasi.KEY_EMAIL_INS, email_instruktur);
                params.put(Konfigurasi.KEY_HP_INS, hp_instruktur);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_UPDATE_INSTRUKTUR, params);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailInstrukturActivity.this,
                        "pesan: " + message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailInstrukturActivity.this, MainActivity.class).putExtra("keyName", "instruktur"));
            }
        }
        UpdateDataPegawai updateDataPegawai = new UpdateDataPegawai();
        updateDataPegawai.execute();
    }
}