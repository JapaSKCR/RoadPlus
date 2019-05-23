package com.parse.starter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.starter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HomeAdapter extends ArrayAdapter<ParseObject> {

    private Context context;
    private ArrayList<ParseObject> postagens;


    public HomeAdapter( Context c,  ArrayList<ParseObject> objects) {
        super(c, 0, objects);
        this.context = c;
        this.postagens = objects;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        View view = convertView;

        if( view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_postagem, parent, false);

        }


        if(postagens.size() > 0){

                ImageView imagemPost = view.findViewById(R.id.imagem_post);
                TextView modelo = view.findViewById(R.id.idModelo);
                TextView preco = view.findViewById(R.id.idPreco);

                ParseObject parseObject = postagens.get(position);
                modelo.setText(parseObject.getString("modelo"));
                preco.setText(parseObject.getString("preco"));




                Picasso.with(context)
                    .load(parseObject.getParseFile("imagem").getUrl())
                    .fit()
                     .into(imagemPost);

            }



        return view;
    }
}
