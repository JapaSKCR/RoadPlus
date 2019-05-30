package com.parse.starter.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.Activity.MainActivity;
import com.parse.starter.R;
import com.parse.starter.util.MaskWatcher;
import com.parse.starter.util.MoneyTextWatcher;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsuariosFragment extends Fragment {

    private ArrayAdapter<ParseUser> adapter;
    private ArrayList<ParseUser> users;
    private ParseQuery<ParseUser> query;
    private ImageView foto;
    private TextView descImagem;
    private Button anuncio;
    private EditText valor;
    private EditText modelo;
    private EditText cor;
    private EditText phoneNumber;
    private EditText ano;

    private byte[] byteArray;

    public UsuariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.cadastro_carro, container, false);


        phoneNumber = view.findViewById(R.id.phoneNumber);
        phoneNumber.addTextChangedListener(new MaskWatcher("(##)#########"));

        valor = view.findViewById(R.id.idValor);
        Locale mLocale = new Locale("pt", "BR");
        valor.addTextChangedListener(new MoneyTextWatcher(valor, mLocale));



        descImagem = view.findViewById(R.id.tesxto);
        anuncio = view.findViewById(R.id.idBotaoAnuncio);
        modelo = view.findViewById(R.id.idTextoModelo);
        cor = view.findViewById(R.id.idCor);
        ano = view.findViewById(R.id.idAno);


        Drawable camera = getResources().getDrawable(R.drawable.ic_action_camera);
        camera.setColorFilter(getResources().getColor(R.color.laranja), PorterDuff.Mode.SRC_ATOP);

        foto = view.findViewById(R.id.idFoto);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegarFoto();
            }
        });

        anuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean erro = false;

                if (modelo.getText().toString().trim().equals("")) {
                    erro = true;
                    modelo.setError("Campo Obrigatório");
                }

                if (valor.getText().toString().trim().equals("")) {
                    erro = true;
                    valor.setError("Campo Obrigatório");
                }

                if (ano.getText().toString().trim().equals("")) {
                    erro = true;
                    ano.setError("Campo Obrigatório");
                }

                if (cor.getText().toString().trim().equals("")) {
                    erro = true;
                    cor.setError("Campo Obrigatório");
                }

                if (phoneNumber.getText().toString().trim().equals("")) {
                    erro = true;
                    phoneNumber.setError("Campo Obrigatório");
                }

                if (descImagem.getText().toString().trim().equals("")) {
                    erro = true;
                    descImagem.setHint("Anexe uma foto do veículo");
                    descImagem.setHintTextColor(ContextCompat.getColor(getContext(), R.color.laranja));
                }


                if(erro != true) {

                    gerarAnuncio();

                }

            }
        });

      /* users = new ArrayList<>();
        listUsuarios = view.findViewById(R.id.usuarios_list);
        adapter = new UsuariosAdapter(getActivity(),users);
        listUsuarios.setAdapter(adapter);
        getUsers();

        listUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ParseUser parseUser = users.get(position);

                Intent intent = new Intent(getActivity(), FeedUsuariosActivity.class);
                intent.putExtra("username", parseUser.getUsername());
                intent.putExtra("objectId", parseUser.getObjectId());
                startActivity(intent);


            }
        });*/

        return view;

    }


    private void pegarFoto() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        this.startActivityForResult(intent, 1);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){

            Uri localImagemSelecionada = data.getData();

            try {

                Bitmap imagem = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), localImagemSelecionada);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.PNG, 75, stream);

                Cursor returnCursor =
                        getActivity().getApplicationContext().getContentResolver().query(localImagemSelecionada, null, null, null, null);

                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

                returnCursor.moveToFirst();
                descImagem.setText(returnCursor.getString(nameIndex));
                descImagem.setTextColor(Color.parseColor("#ffffff"));

                byteArray = stream.toByteArray();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void gerarAnuncio(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmaaaahhmmss");
        String nomeImagem = dateFormat.format(new Date());


        ParseFile arquivoParse = new ParseFile("imagem_"+  nomeImagem + ".png", byteArray );

        if(byteArray != null) {

            ParseUser user = ParseUser.getCurrentUser();
            user.put("telefone", phoneNumber.getText().toString());
            ParseObject parseObject = new ParseObject("Imagem");
            parseObject.put("userId", ParseUser.getCurrentUser().getObjectId());
            parseObject.put("imagem", arquivoParse);
            parseObject.put("modelo", modelo.getText().toString());
            parseObject.put("preco", valor.getText().toString());
            parseObject.put("ano", ano.getText().toString());
            parseObject.put("cor", cor.getText().toString());

            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if (e != null) {

                        Toast.makeText(getActivity().getApplicationContext(), "Erro de Upload" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            });

            parseObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if (e == null) {

                        Toast.makeText(getActivity().getApplicationContext(), "Upload Concluido", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);


                    } else {

                        Toast.makeText(getActivity().getApplicationContext(), "Erro de Upload" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            });

        } else {

            Toast.makeText(getActivity().getApplicationContext(), "Sem imagem anexada", Toast.LENGTH_LONG).show();

        }

    }


    private void getUsers(){

        query = ParseUser.getQuery();
        query.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
        query.orderByAscending("username");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if(e == null){

                    if(objects.size() > 0){
                        users.clear();
                        for (ParseUser parseUser : objects){

                            users.add(parseUser);
                        }

                        adapter.notifyDataSetChanged();
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

    }

}
