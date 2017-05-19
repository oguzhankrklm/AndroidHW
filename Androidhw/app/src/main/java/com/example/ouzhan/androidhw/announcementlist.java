package com.example.ouzhan.androidhw;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class announcementlist extends Fragment {
    private ListView listView;

    private static final int TIME_OUT = 50000;
    public List<String> duyuruList = new ArrayList();
    private ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View containerView = inflater.inflate(R.layout.announcementlist, container, false);
        listView =(ListView)containerView.findViewById(R.id.list);

        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, duyuruList);
        new getDuyuruData().execute();
        return containerView;


    }

    private class getDuyuruData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog= new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(false);
            progressDialog.show();

        }



        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc= Jsoup.connect(getString(R.string.DuyuruURL)).timeout(TIME_OUT).get();

                Elements duyuruItem=doc.select("div.caContent > div[class=cncItem]");

                for (int i=0;i<duyuruItem.size();i++){

                    duyuruList.add(duyuruItem.get(i).text());
                    //     String url1 =oyunadi.get(i).attr("abs:href");
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
