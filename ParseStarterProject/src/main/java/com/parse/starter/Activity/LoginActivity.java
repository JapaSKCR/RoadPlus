package com.parse.starter.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.util.ParseErros;

public class LoginActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText senha;
    private Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       //ParseUser.logOut();

        verificarUsuarioLogado();

        usuario = findViewById(R.id.idUsuario);
        senha = findViewById(R.id.idSenhaLogin);
        login = findViewById(R.id.idBotaoLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usuario.getText().toString();
                String password = senha.getText().toString();

                verificarLogin(user, password);
            }
        });

    }

    private void verificarLogin(String user, String password) {

        ParseUser.logInInBackground(user, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){

                    abrirViewPrincipal();

                } else {

                    Toast.makeText(getApplicationContext(), "Erro ao fazer login"+ e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void abrirCadastroUsuario(View view){

        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);

    }

    private void verificarUsuarioLogado(){

        if(ParseUser.getCurrentUser() != null){
            abrirViewPrincipal();
        }

    }

    private void abrirViewPrincipal(){

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}
