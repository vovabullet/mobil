package ru.rut.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity для регистрации нового пользователя.
 * Наследуется от BaseActivity для логирования жизненного цикла.
 */
public class SignUpActivity extends BaseActivity {

    /**
     * Инициализация Activity при создании.
     * Настраивает форму регистрации и обработчик кнопки.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Устанавливаем тег для логирования
        TAG = "SignUpActivity";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Находим кнопку регистрации по ID
        Button registerButton = findViewById(R.id.register_button);

        // Устанавливаем слушатель нажатия на кнопку регистрации
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Находим все поля ввода формы регистрации
                EditText name = (EditText) findViewById(R.id.name_input);
                EditText email = (EditText) findViewById(R.id.email_input);
                EditText password = (EditText) findViewById(R.id.password_input);
                EditText age = (EditText) findViewById(R.id.age_input);

                // Флаг валидности введенных данных
                boolean valid = true;
                
                // Проверка имени (не должно быть пустым)
                if (name.getText().toString().isEmpty()) {
                    name.setError("Имя обязательно");
                    valid = false;
                }
                
                // Проверка email (должен содержать @)
                if (!email.getText().toString().contains("@")) {
                    email.setError("Неверный email");
                    valid = false;
                }
                
                // Проверка пароля (минимум 6 символов)
                if (password.getText().toString().length() < 6) {
                    password.setError("Пароль слишком короткий");
                    valid = false;
                }
                
                // Проверка возраста (должен быть больше 0)
                if (age.getText().toString().isEmpty() || Integer.parseInt(age.getText().toString()) <= 0) {
                    age.setError("Неверный возраст");
                    valid = false;
                }
                
                // Если все поля валидны, передаем данные обратно в SignInActivity
                if (valid) {
                    Toast.makeText(SignUpActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                    
                    // Создаем Intent для возврата в SignInActivity
                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                    
                    // Вариант 1: Передача данных с использованием стандартных типов (String)
                    intent.putExtra("USER_NAME", name.getText().toString());
                    intent.putExtra("USER_EMAIL", email.getText().toString());
                    intent.putExtra("USER_PASSWORD", password.getText().toString());
                    
                    // Вариант 2: Передача данных с использованием объекта User (Serializable)
                    User user = new User(
                        name.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString()
                    );
                    intent.putExtra("USER_OBJECT_SERIALIZABLE", user);
                    
                    // Вариант 3: Передача данных с использованием объекта User (Parcelable)
                    intent.putExtra("USER_OBJECT_PARCELABLE", user);
                    
                    // Запускаем SignInActivity с переданными данными
                    startActivity(intent);
                    // Закрываем текущую Activity
                    finish();
                }
            }
        });
    }
}