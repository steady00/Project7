package com.steady.project7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.steady.project7.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class KelasFragment extends Fragment implements AdapterView.OnItemClickListener {
    //private ActivityMainBinding binding;
    private ListView list_view_kelas;
    private String JSON_STRING;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kelas, container, false);
        list_view_kelas = view.findViewById(R.id.list_view_kelas);
        list_view_kelas.setOnItemClickListener(this);

        getJSON();

        return view;
    }

    public void getJSON()
    {
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
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_KELAS);
                //System.out.println("Result = " + result);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA_JSON: ", JSON_STRING);


                // menampilkan data dalam bentuk list view
                displayAllData();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayAllData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            Log.d("DATA_JSON: ", JSON_STRING);


            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_kls = object.getString(Konfigurasi.TAG_JSON_ID_KLS);
                String mulai_kls = object.getString(Konfigurasi.TAG_JSON_TGL_MULAI);
                String akhir_kls = object.getString(Konfigurasi.TAG_JSON_TGL_AKHIR);
                String materi_kls = object.getString(Konfigurasi.TAG_JSON_KLS_NAMA_MAT);


                HashMap<String, String> kelas = new HashMap<>();
                kelas.put(Konfigurasi.TAG_JSON_ID_KLS, id_kls);
                kelas.put(Konfigurasi.TAG_JSON_TGL_MULAI, mulai_kls);
                kelas.put(Konfigurasi.TAG_JSON_TGL_AKHIR, akhir_kls);
                kelas.put(Konfigurasi.TAG_JSON_KLS_NAMA_MAT, materi_kls);

                // ubah format json menjadi array list
                list.add(kelas);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // adapter untuk meletakan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list,
                R.layout.activity_list_kelas,
                new String[]{Konfigurasi.TAG_JSON_ID_KLS, Konfigurasi.TAG_JSON_TGL_MULAI, Konfigurasi.TAG_JSON_TGL_AKHIR, Konfigurasi.TAG_JSON_KLS_NAMA_MAT},
                new int[]{R.id.txt_id_kls, R.id.txt_mulai_kelas, R.id.txt_akhir_kelas, R.id.txt_kls_nama_mat}
        );
        list_view_kelas.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(getActivity(), DetailKelasActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String klsid= map.get(Konfigurasi.TAG_JSON_ID_KLS).toString();
        myIntent.putExtra(Konfigurasi.KLS_ID, klsid);
        startActivity(myIntent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}