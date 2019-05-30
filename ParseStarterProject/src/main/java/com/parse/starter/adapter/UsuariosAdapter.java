package com.parse.starter.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.parse.ParseUser;
import java.util.ArrayList;


public class UsuariosAdapter extends ArrayAdapter<ParseUser> {

    private Context context;
    private ArrayList<ParseUser> usuarios;

    public UsuariosAdapter(Context c, ArrayList<ParseUser> objects) {
        super(c, 0, objects);
        this.context = c;
        this.usuarios = objects;
    }

}
