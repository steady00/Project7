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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DeleteKelasDetailActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edit_id_kelas_detail,edit_id_kelas_kelas_detail;
    Button btn_delete_peserta_detail_kelas,btn_edit_kelas_detail;
    Spinner edit_nama_peserta_kelas_detail;
    String id;
    private String temp_json, JSON_STRING_PST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_kelas_detail);

        edit_id_kelas_detail = findViewById(R.id.edit_id_kelas_detail);
        edit_id_kelas_kelas_detail = findViewById(R.id.edit_id_kelas_kelas_detail);
        edit_nama_peserta_kelas_detail = findViewById(R.id.edit_nama_peserta_kelas_detail);
        btn_edit_kelas_detail = findViewById(R.id.btn_edit_kelas_detail);
        btn_delete_peserta_detail_kelas = findViewById(R.id.btn_delete_peserta_detail_kelas);

        Intent receiveIntent = getIntent();
        id = receiveIntent.getStringExtra(Konfigurasi.KELAS_KLS_DETAIL_ID);
        edit_id_kelas_detail.setText(id);

        getJSON();
        btn_edit_kelas_detail.setOnClickListener(this);
        btn_delete_peserta_detail_kelas.setOnClickListener(this);

    }

    private void getJSON() {
        // bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DeleteKelasDetailActivity.this,
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_DETAIL_DELETE_PESERTA,id);
                String peserta = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_PESERTA);
                temp_json = peserta;
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);
                Log.d("Hasil", message);


                JSON_STRING_PST = temp_json;

                JSONObject jsonObjectPst = null;
                ArrayList<String> arrayListPst = new ArrayList<>();
                try {
                    //json array data detail peserta di detail kelas
                    JSONObject jsonObject = new JSONObject(message);
                    JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
                    JSONObject nama_peserta = result.getJSONObject(0);

                    //json array nama peserta
                    jsonObjectPst = new JSONObject(JSON_STRING_PST);
                    JSONArray jsonArrayPst = jsonObjectPst.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
                    Log.d("Data_JSON_LIST: ", String.valueOf(jsonArrayPst));

                    arrayListPst.add(0, nama_peserta.getString(Konfigurasi.TAG_JSON_NAMA_PST));

                    for (int i = 0; i < jsonArrayPst.length(); i++) {
                        JSONObject object = jsonArrayPst.getJSONObject(i);
                        String namePeserta = object.getString(Konfigurasi.TAG_JSON_NAMA_PST);
                        arrayListPst.add(namePeserta);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("Data_JSON_LIST: ", String.valueOf(arrayListPst));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeleteKelasDetailActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayListPst);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                edit_nama_peserta_kelas_detail.setAdapter(adapter);

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

    public void displayDetailData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String id_kelas = object.getString(Konfigurasi.TAG_JSON_ID_KELAS_KELAS_DETAIL);
            String id_detail_kelas = object.getString(Konfigurasi.TAG_JSON_ID_KELAS_DETAIL_KELAS_DETAIL);
            //String nama_peserta = object.getString(Konfigurasi.TAG_JSON_NAMA_PST_KELAS_DETAIL);

            edit_id_kelas_kelas_detail.setText(id_kelas);
            edit_id_kelas_detail.setText(id_detail_kelas);
            //edit_nama_peserta_kelas_detail.setText(nama_peserta);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btn_edit_kelas_detail){
            confirmUpdatePeserta();
        } else if( view == btn_delete_peserta_detail_kelas){
            confirmDeletePeserta();
        }
    }

    private void confirmUpdatePeserta() {
        final String id_detail_kelas = edit_id_kelas_detail.getText().toString().trim();
        final String id_kelas = edit_id_kelas_kelas_detail.getText().toString().trim();
        final String nama_peserta = edit_nama_peserta_kelas_detail.getSelectedItem().toString().trim();

        //Confirmation altert dialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Insert Data");
        builder.setMessage("Are you sure want to insert this data? " +
                "\nID Detail Kelas  : " + id_detail_kelas +
                "\nID Kelas         : " + id_kelas +
                "\nNama Peserta     : " + nama_peserta);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateDataPeserta();
            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateDataPeserta() {
        final String id_detail_kelas = edit_id_kelas_detail.getText().toString().trim();
        final String id_kelas = edit_id_kelas_kelas_detail.getText().toString().trim();
        final String nama_peserta = edit_nama_peserta_kelas_detail.getSelectedItem().toString().trim();

        class UpdateDataPeserta extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DeleteKelasDetailActivity.this,
                        "Mengubah Data", "Harap tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                String result;

                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_ID_DETAIL_KELAS, id_detail_kelas);
                params.put(Konfigurasi.KEY_ID_KLS, id_kelas);
                params.put(Konfigurasi.KEY_NAMA_PST, nama_peserta);
                HttpHandler handler = new HttpHandler();
                result = handler.sendPostRequest(Konfigurasi.URL_UPDATE_PESERTA_DETAIL_KELAS, params);


                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                Toast.makeText(DeleteKelasDetailActivity.this, "pesan: " + message,
                        Toast.LENGTH_SHORT).show();
                Log.d("pesan", message);
                startActivity(new Intent(DeleteKelasDetailActivity.this, MainActivity.class).putExtra("keyName", "detail kelas"));
            }
        }
        UpdateDataPeserta updateDataPeserta = new UpdateDataPeserta();
        updateDataPeserta.execute();
    }

    private void confirmDeletePeserta() {
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
                DeletePeserta();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void DeletePeserta() {
        class DeleteData extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DeleteKelasDetailActivity.this,
                        "Menghapus Data", "Harap tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_DELETE_KELAS_DETAIL_PESERTA, id);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DeleteKelasDetailActivity.this,
                        "pesan: " + message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DeleteKelasDetailActivity.this, MainActivity.class).putExtra("keyName", "detail kelas"));
            }
        }
        DeleteData deleteData = new DeleteData();
        deleteData.execute();
    }
}