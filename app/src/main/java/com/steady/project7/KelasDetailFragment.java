package com.steady.project7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.steady.project7.databinding.FragmentKelasDetailBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class KelasDetailFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView list_view_kelas_detail;
    private String JSON_STRING;
    FloatingActionButton floatingActionButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kelas_detail, container, false);
        list_view_kelas_detail = view.findViewById(R.id.list_view_kelas_detail);
        list_view_kelas_detail.setOnItemClickListener(this);
        floatingActionButton = view.findViewById(R.id.btn_add_kelas);
        floatingActionButton.setOnClickListener(this);

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
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_KELAS_DETAIL);
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
                JSONObject object = result.getJSONObject(i);;
                String id_kls_kelas_detail = object.getString(Konfigurasi.TAG_JSON_ID_KELAS_KLS_DETAIL);
                String nama_materi_kelas_detail = object.getString(Konfigurasi.TAG_JSON_MATERI_KLS_DETAIL);
                String jumlah_peserta = object.getString(Konfigurasi.TAG_JSON_JUMLAH_PST);


                HashMap<String, String> kelas_detail = new HashMap<>();
                kelas_detail.put(Konfigurasi.TAG_JSON_ID_KELAS_KLS_DETAIL, id_kls_kelas_detail);
                kelas_detail.put(Konfigurasi.TAG_JSON_MATERI_KLS_DETAIL, nama_materi_kelas_detail);
                kelas_detail.put(Konfigurasi.TAG_JSON_JUMLAH_PST, jumlah_peserta);

                // ubah format json menjadi array list
                list.add(kelas_detail);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // adapter untuk meletakan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list,
                R.layout.activity_list_kelas_detail,
                new String[]{Konfigurasi.TAG_JSON_ID_KELAS_KLS_DETAIL, Konfigurasi.TAG_JSON_MATERI_KLS_DETAIL, Konfigurasi.TAG_JSON_JUMLAH_PST},
                new int[]{R.id.txt_id_kls_detail_kls, R.id.txt_materi_kelas_detail, R.id.txt_jumlah_pst}
        );
        list_view_kelas_detail.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(getActivity(), DetailKelasDetailActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String kelas_kls_detail_id = map.get(Konfigurasi.TAG_JSON_ID_KELAS_KLS_DETAIL).toString();
        myIntent.putExtra(Konfigurasi.KELAS_KLS_DETAIL_ID, kelas_kls_detail_id);
        startActivity(myIntent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), TambahPesertaKelasDetailActivity.class));
    }
}