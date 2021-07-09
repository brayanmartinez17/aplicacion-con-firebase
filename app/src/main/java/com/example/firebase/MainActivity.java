package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextInputEditText emailInput, passwordInput, confirmPasswrod;
    Button registerButton;
    FirebaseAuth mauth;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswrod = findViewById(R.id.confirmpassword_input);
        registerButton = findViewById(R.id.btnRegister);
        mauth =FirebaseAuth.getInstance();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = emailInput.getText().toString();
                String Password = passwordInput.getText().toString();
                String confirmPassword = confirmPasswrod.getText().toString();
                if(Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    if(Password.matches(confirmPassword)&& Password.length()>=8){
                        InputMethodManager imm=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
                        /*dialog =  new Dialog(MainActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();*/
                        mauth.createUserWithEmailAndPassword(Email,Password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull  Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(MainActivity.this,"creado Correctamente",Toast.LENGTH_SHORT).show();
                                            FirebaseUser user = mauth.getCurrentUser();
                                            if(user != null){
                                                Intent i = new Intent(MainActivity.this, MainPage.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        }else {
                                            Toast.makeText(MainActivity.this,"Autenticacion fallada",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }else if (Password.length()<8) {
                        passwordInput.setError("Password debe ser de 8 caracteres");
                        passwordInput.requestFocus();
                    }else{
                        confirmPasswrod.setError("password no existe");
                        confirmPasswrod.requestFocus();
                    }
                }else{
                    emailInput.setError("correo invalido");
                    emailInput.requestFocus();
                }
            }
        });
    }
}