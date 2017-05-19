package com.example.ouzhan.androidhw;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oğuzhan on 18.05.2017.
 */
public class foodlist extends Fragment {
    private ListView listView;

    private static final int TIME_OUT = 50000;

    private List<String> menuList = new ArrayList();
    private ArrayAdapter<String> adapter;

    private ProgressDialog progressDialog;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View containerView = inflater.inflate(R.layout.foodlist, container, false);
        listView = (ListView) containerView.findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, menuList);
        new getMenuData().execute();
        return containerView;


    }

    private class getMenuData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(false);
            progressDialog.show();

        }


        @Override
        protected Void doInBackground(Void... voids) {

            try {
               Document doc = Jsoup.connect("http://ybu.edu.tr/sks/").timeout(TIME_OUT).get();

              Elements menuItem = doc.select("table h5");

                for (int i = 0; i < menuItem.size(); i++) {

                    menuList.add(menuItem.get(i).text());


               }


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            listView.setAdapter(adapter);
            progressDialog.dismiss();

        }
    }
}
