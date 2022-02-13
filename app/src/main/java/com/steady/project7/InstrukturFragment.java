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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.steady.project7.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class InstrukturFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    //private ActivityMainBinding binding;
    private ListView list_view_instruktur;
    private String JSON_STRING;
    FloatingActionButton floatingActionButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instruktur, container, false);
        list_view_instruktur = view.findViewById(R.id.list_view_instruktur);
        list_view_instruktur.setOnItemClickListener(this);
        floatingActionButton = view.findViewById(R.id.btn_add_instruktur);
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
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_ALL_INSTRUKTUR);
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
                String id_ins = object.getString(Konfigurasi.TAG_JSON_ID_INS);
                String nama_ins = object.getString(Konfigurasi.TAG_JSON_NAMA_INS);


                HashMap<String, String> instruktur = new HashMap<>();
                instruktur.put(Konfigurasi.TAG_JSON_ID_INS, id_ins);
                instruktur.put(Konfigurasi.TAG_JSON_NAMA_INS, nama_ins);

                // ubah format json menjadi array list
                list.add(instruktur);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // adapter untuk meletakan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list,
                R.layout.activity_list_instruktur,
                new String[]{Konfigurasi.TAG_JSON_ID_INS, Konfigurasi.TAG_JSON_NAMA_INS},
                new int[]{R.id.txt_id_kls, R.id.txt_kls_nama_mat}
        );
        list_view_instruktur.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(getActivity(), DetailInstrukturActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String insid= map.get(Konfigurasi.TAG_JSON_ID_INS).toString();
        myIntent.putExtra(Konfigurasi.INS_ID, insid);
        startActivity(myIntent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), TambahInstrukturActivity.class));
    }
}