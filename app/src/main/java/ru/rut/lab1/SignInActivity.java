package ru.rut.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Находим кнопки
        Button signInButton = findViewById(R.id.sign_in_button);
        Button signUpButton = findViewById(R.id.sign_up_button);

        // Устанавливаем слушатель для кнопки входа
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = findViewById(R.id.email_input);
                EditText password = findViewById(R.id.password_input);

                if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    // Пример проверки (замените на реальную логику проверки)
                    // if (email.getText().toString().equals("test@test.com") && ...)
                    Toast.makeText(SignInActivity.this, "Вход выполнен!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignInActivity.this, "Заполните поля", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Устанавливаем слушатель для кнопки регистрации
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}