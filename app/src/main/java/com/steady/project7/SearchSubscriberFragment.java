package com.steady.project7;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchSubscriberFragment extends Fragment {
    Button btn_search_subscriber;
    ListView list_search2;
    EditText edit_search_subscriber;
    private String JSON_STRING;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_subscriber, container, false);

        btn_search_subscriber = view.findViewById(R.id.btn_search_subscriber);
        list_search2 = view.findViewById(R.id.list_search2);
        edit_search_subscriber = view.findViewById(R.id.edit_search_subscriber);

        btn_search_subscriber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJSON();
            }
        });

        return view;
    }

    private void getJSON() {
        String nama_peserta = edit_search_subscriber.getText().toString().trim();
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
                String result = handler.sendGetResponse(Konfigurasi.URL_SEARCH_MATERI, nama_peserta);
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


            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String nama_materi = object.getString(Konfigurasi.TAG_JSON_HASIL_NAMA_MATERI);
                String tgl_mulai = object.getString(Konfigurasi.TAG_JSON_HASIL_TGL_MULAI);
                String tgl_akhir = object.getString(Konfigurasi.TAG_JSON_HASIL_TGL_AKHIR);


                HashMap<String, String> materi = new HashMap<>();
                materi.put(Konfigurasi.TAG_JSON_HASIL_NAMA_MATERI, nama_materi);
                materi.put(Konfigurasi.TAG_JSON_HASIL_TGL_MULAI, tgl_mulai);
                materi.put(Konfigurasi.TAG_JSON_HASIL_TGL_AKHIR, tgl_akhir);

                // ubah format json menjadi array list
                list.add(materi);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // adapter untuk meletakan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list,
                R.layout.activity_list_search_materi,
                new String[]{Konfigurasi.TAG_JSON_HASIL_NAMA_MATERI, Konfigurasi.TAG_JSON_HASIL_TGL_MULAI, Konfigurasi.TAG_JSON_HASIL_TGL_AKHIR},
                new int[]{R.id.txt_hasil_nama_materi, R.id.txt_hasil_tgl_mulai, R.id.txt_hasil_tgl_akhir}
        );
        list_search2.setAdapter(adapter);
    }
}