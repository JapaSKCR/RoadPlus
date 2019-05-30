package com.parse.starter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


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
                final TextView telefone = view.findViewById(R.id.idtelefoneContato);
                 final TextView user = view.findViewById(R.id.idUser);
                 TextView cor = view.findViewById(R.id.idCor);


            ParseObject parseObject = postagens.get(position);
                String textoModelo = parseObject.getString("modelo");
                String ano = parseObject.getString("ano");
                modelo.setText(textoModelo + " " + ano );
                preco.setText(parseObject.getString("preco"));
                cor.setText(parseObject.getString("cor"));



            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("objectId", parseObject.getString("userId"));
            query.findInBackground(new FindCallback<ParseUser>() {
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {

                        telefone.setText(objects.get(0).getString("telefone"));
                        user.setText(objects.get(0).getUsername());

                    } else {
                        // Something went wrong.
                    }
                }
            });

                Picasso.with(context)
                    .load(parseObject.getParseFile("imagem").getUrl())
                    .fit()
                     .into(imagemPost);

            }



        return view;
    }
}
