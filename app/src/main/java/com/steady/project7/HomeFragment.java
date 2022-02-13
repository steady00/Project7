package com.steady.project7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment implements View.OnClickListener {
    Button btn_home_instruktur, btn_home_peserta, btn_home_materi, btn_home_kelas, btn_search_peserta, btn_search_jumlah_peserta;
    ListView list_search1;
    TextView txt_search3;
    EditText edit_serach_peserta, edit_serach_jumlah_peserta;
    private String JSON_STRING;
    private String JSON_STRING_JML;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btn_home_instruktur =  view.findViewById(R.id.btn_home_instruktur);
        btn_home_peserta =  view.findViewById(R.id.btn_home_peserta);
        btn_home_materi =  view.findViewById(R.id.btn_home_materi);
        btn_home_kelas =  view.findViewById(R.id.btn_home_kelas);

        btn_search_peserta = view.findViewById(R.id.btn_search_peserta);
        list_search1 = view.findViewById(R.id.list_search1);
        edit_serach_peserta = view.findViewById(R.id.edit_search_peserta);

        btn_search_jumlah_peserta = view.findViewById(R.id.btn_search_jumlah_peserta);
        txt_search3 =view.findViewById(R.id.txt_search3);
        edit_serach_jumlah_peserta = view.findViewById(R.id.edit_search_jumlah_peserta);

        btn_home_instruktur.setOnClickListener(this);
        btn_home_peserta.setOnClickListener(this);
        btn_home_materi.setOnClickListener(this);
        btn_home_kelas.setOnClickListener(this);
        btn_search_peserta.setOnClickListener(this);
        btn_search_jumlah_peserta.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == btn_home_instruktur){
            startActivity(new Intent(getActivity(), MainActivity.class).putExtra("keyName", "instruktur"));
        } else if ( view == btn_home_peserta){
            startActivity(new Intent(getActivity(), MainActivity.class).putExtra("keyName", "peserta"));
        } else if (view == btn_home_materi){
            startActivity(new Intent(getActivity(), MainActivity.class).putExtra("keyName", "materi"));
        }else if (view == btn_home_kelas){
            startActivity(new Intent(getActivity(), MainActivity.class).putExtra("keyName", "kelas"));
        }else if (view == btn_search_peserta) {
            getJSON();
        }else if (view == btn_search_jumlah_peserta) {
            getJSONJmlPeserta();
        }
    }

    private void getJSONJmlPeserta() {
        String tahun_mulai = edit_serach_jumlah_peserta.getText().toString().trim();
        class GetJSONJmlPeserta extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_SEARCH_JUMLAH, tahun_mulai);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING_JML = message;
                Log.d("DATA_JML: ", JSON_STRING_JML);


                // menampilkan data dalam bentuk list view
                displayDataJml(message);
            }
        }
        GetJSONJmlPeserta getJSONJmlPeserta = new GetJSONJmlPeserta();
        getJSONJmlPeserta.execute();
    }

    private void displayDataJml(String jsoon_jml) {
        String tahun_mulai = edit_serach_jumlah_peserta.getText().toString().trim();
        try {
            JSONObject jsonObject = new JSONObject(jsoon_jml);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String nama_materi = object.getString(Konfigurasi.TAG_JSON_JUMLAH_PESERTA);

            txt_search3.setText("Terdapat " + nama_materi + " peserta pada " + tahun_mulai);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void getJSON() {
        String nama_materi = edit_serach_peserta.getText().toString().trim();
        class GetJSON extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_SEARCH_PESERTA, nama_materi);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA_JSON: ", JSON_STRING);


                // menampilkan data dalam bentuk list view
                displayData();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            Log.d("DATA_JSON: ", JSON_STRING);


            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String nama_peserta = object.getString(Konfigurasi.TAG_JSON_NAMA_PST_KELAS_DETAIL);


                HashMap<String, String> detail_kelas_detail = new HashMap<>();
                detail_kelas_detail.put(Konfigurasi.TAG_JSON_NAMA_PST_KELAS_DETAIL, nama_peserta);

                // ubah format json menjadi array list
                list.add(detail_kelas_detail);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // adapter untuk meletakan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list,
                R.layout.activity_list_search_peserta,
                new String[]{Konfigurasi.TAG_JSON_NAMA_PST_KELAS_DETAIL},
                new int[]{R.id.txt_hasil_nama_pst}
        );
        list_search1.setAdapter(adapter);
    }
}
