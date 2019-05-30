package com.parse.starter.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.starter.R;
import com.parse.starter.util.ParseErros;

public class CadastroActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText email;
    private EditText senha;
    private EditText confirmarSenha;
    private Button cadastrar;
    private TextView textoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        usuario =  findViewById(R.id.idNomeUsuario);
        email =  findViewById(R.id.idEmail);
        senha = findViewById(R.id.idSenhaCadastro);
        confirmarSenha = findViewById(R.id.idConfirmarSenha);
        cadastrar = findViewById(R.id.idBotaoCadastrar);
        textoLogin = findViewById(R.id.faca_login);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });
        textoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLoginUsuario();
            }
        });

    }

    private void cadastrarUsuario() {

        boolean erro = false;

        String pass = senha.getText().toString();
        String match = confirmarSenha.getText().toString();


        if (pass.isEmpty()) {
            erro = true;
            senha.setError("Campo Obrigatório");
        }

        if (match.isEmpty()) {
            erro = true;
            confirmarSenha.setError("Campo Obrigatório");
        }


        if (usuario.getText().toString().isEmpty()) {
            erro = true;
            usuario.setError("Campo Obrigatório");
        }

        if (email.getText().toString().isEmpty()) {
            erro = true;
            email.setError("Campo Obrigatório");
        }

        if(erro != true) {


            ParseUser user = new ParseUser();
            user.setUsername(usuario.getText().toString());
            user.setEmail(email.getText().toString());

            if (pass.equals(match)) {


                user.setPassword(senha.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null) {

                            Toast.makeText(getApplicationContext(), "Usuário criado com Sucesso", Toast.LENGTH_LONG).show();
                            abrirViewPrincipal();
                        } else {

                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            } else {

                Toast.makeText(getApplicationContext(), "Email inválido", Toast.LENGTH_LONG).show();

            }

        }

    }

    public void abrirLoginUsuario(){

        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void abrirViewPrincipal(){

        Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }




}
