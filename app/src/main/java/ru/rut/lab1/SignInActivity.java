package ru.rut.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity для входа пользователя в систему.
 * Наследуется от BaseActivity для логирования жизненного цикла.
 * Получает данные от SignUpActivity после успешной регистрации.
 */
public class SignInActivity extends BaseActivity {

    // TextView для отображения информации о зарегистрированном пользователе
    private TextView registeredUserInfo;

    /**
     * Инициализация Activity при создании.
     * Настраивает форму входа и обработчики кнопок.
     * Получает и отображает данные зарегистрированного пользователя.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Устанавливаем тег для логирования
        TAG = "SignInActivity";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Находим TextView для отображения информации о зарегистрированном пользователе
        registeredUserInfo = findViewById(R.id.registered_user_info);

        // Находим кнопки входа и регистрации
        Button signInButton = findViewById(R.id.sign_in_button);
        Button signUpButton = findViewById(R.id.sign_up_button);

        // Получаем данные от SignUpActivity (если они были переданы)
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            
            // Вариант 1: Получение данных в виде стандартных типов (String)
            String userName = receivedIntent.getStringExtra("USER_NAME");
            String userEmail = receivedIntent.getStringExtra("USER_EMAIL");
            
            // Вариант 2: Получение данных как объекта User (Serializable)
            User userSerializable = (User) receivedIntent.getSerializableExtra("USER_OBJECT_SERIALIZABLE");
            
            // Вариант 3: Получение данных как объекта User (Parcelable)
            User userParcelable = receivedIntent.getParcelableExtra("USER_OBJECT_PARCELABLE");
            
            // Отображаем информацию, если данные были переданы
            // Используем приоритет: Parcelable > Serializable > Strings
            if (userParcelable != null) {
                displayUserInfo("Parcelable", userParcelable.getName(), userParcelable.getEmail());
            } else if (userSerializable != null) {
                displayUserInfo("Serializable", userSerializable.getName(), userSerializable.getEmail());
            } else if (userName != null && userEmail != null) {
                displayUserInfo("String", userName, userEmail);
            }
        }

        // Устанавливаем слушатель для кнопки входа
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Находим поля ввода email и пароля
                EditText email = findViewById(R.id.email_input);
                EditText password = findViewById(R.id.password_input);

                // Проверяем, что оба поля заполнены
                if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    // Сейчас просто принимаем любые непустые значения
                    Toast.makeText(SignInActivity.this, "Вход выполнен!", Toast.LENGTH_SHORT).show();
                    
                    // Создаем Intent для перехода на HomeActivity
                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                    // Запускаем HomeActivity
                    startActivity(intent);
                } else {
                    // Если поля не заполнены, показываем сообщение об ошибке
                    Toast.makeText(SignInActivity.this, "Заполните поля", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Устанавливаем слушатель для кнопки регистрации
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создаем Intent для перехода на SignUpActivity
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                // Запускаем SignUpActivity
                startActivity(intent);
            }
        });
    }

    /**
     * Вспомогательный метод для отображения информации о зарегистрированном пользователе
     * @param method метод передачи данных (String, Serializable, Parcelable)
     * @param name имя пользователя
     * @param email email пользователя
     */
    private void displayUserInfo(String method, String name, String email) {
        String infoText = "Зарегистрирован пользователь (" + method + "):\nИмя: " + name + "\nEmail: " + email;
        registeredUserInfo.setText(infoText);
        registeredUserInfo.setVisibility(View.VISIBLE);
    }
}