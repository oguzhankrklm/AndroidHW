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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class newslist extends Fragment {

    private static final int TIME_OUT = 50000;

    private ListView listView;
    public List<String> haberList = new ArrayList();
    private ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;



    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View containerView = inflater.inflate(R.layout.newslist, container, false);
        listView =(ListView)containerView.findViewById(R.id.list);
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, haberList);
        new getHaberData().execute();


        return containerView;
    }



    private class getHaberData extends AsyncTask<Void, Void, Void> {

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
                Document doc= Jsoup.connect(getString(R.string.HaberURL)).timeout(TIME_OUT).get();

                Elements haberItem=doc.select("div.cnContent > div[class=cncItem]");

                for (int i=0;i<haberItem.size();i++){


                    haberList.add(haberItem.get(i).text());


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
