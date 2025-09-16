package ru.rut.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Находим кнопку по ID
        Button registerButton = findViewById(R.id.register_button);

        // Устанавливаем слушатель нажатия
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Находим все поля ввода
                EditText name = (EditText) findViewById(R.id.name_input);
                EditText email = (EditText) findViewById(R.id.email_input);
                EditText password = (EditText) findViewById(R.id.password_input);
                EditText age = (EditText) findViewById(R.id.age_input);

                boolean valid = true;
                if (name.getText().toString().isEmpty()) {
                    name.setError("Имя обязательно");
                    valid = false;
                }
                if (!email.getText().toString().contains("@")) {
                    email.setError("Неверный email");
                    valid = false;
                }
                if (password.getText().toString().length() < 6) {
                    password.setError("Пароль слишком короткий");
                    valid = false;
                }
                if (age.getText().toString().isEmpty() || Integer.parseInt(age.getText().toString()) <= 0) {
                    age.setError("Неверный возраст");
                    valid = false;
                }
                if (valid) {
                    Toast.makeText(SignUpActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}