package com.parse.starter.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ListView listaPostagens;
    private ArrayList<ParseObject> postagens;
    private ArrayAdapter<ParseObject> adapter;
    private ParseQuery<ParseObject> query;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        postagens = new ArrayList<>();
        listaPostagens = view.findViewById(R.id.home_postagens);
        adapter = new HomeAdapter(getActivity(), postagens );
        listaPostagens.setAdapter(adapter);

        getPosts();

        return view;
    }

    private void getPosts() {

        query = ParseQuery.getQuery("Imagem");
        query.whereEqualTo("userId", ParseUser.getCurrentUser().getObjectId());
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){

                    postagens.clear();
                    if(objects.size() > 0){

                        for (ParseObject parseObject : objects){

                            postagens.add(parseObject);


                        }
                        adapter.notifyDataSetChanged();

                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

    }

    public void atualizaPosts(){
        getPosts();
    }




}
