package ru.rut.lab1.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import ru.rut.lab1.R;

/**
 * Fragment для регистрации нового пользователя
 */
public class SignUpFragment extends Fragment {
    /**
     * Инициализация Activity при создании.
     * Настраивает форму регистрации и обработчик кнопки.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        // Находим кнопку регистрации по ID
        Button registerButton = view.findViewById(R.id.register_button);

        NavController navController = NavHostFragment.findNavController(SignUpFragment.this);

        // Устанавливаем слушатель нажатия на кнопку регистрации
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Находим все поля ввода формы регистрации
                EditText name = (EditText) view.findViewById(R.id.name_input);
                EditText email = (EditText) view.findViewById(R.id.email_input);
                EditText password = (EditText) view.findViewById(R.id.password_input);
                EditText age = (EditText) view.findViewById(R.id.age_input);

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
//                // Проверка пароля (минимум 6 символов)
//                if (password.getText().toString().length() < 6) {
//                    password.setError("Пароль слишком короткий");
//                    valid = false;
//                }
                // Проверка возраста (должен быть больше 0)
                if (age.getText().toString().isEmpty()) {
                    age.setError("Возраст обязателен");
                    valid = false;
                } else {
                    try {
                        int ageValue = Integer.parseInt(age.getText().toString());
                        if (ageValue <= 0) {
                            age.setError("Неверный возраст");
                            valid = false;
                        }
                    } catch (NumberFormatException e) {
                        age.setError("Возраст должен быть числом");
                        valid = false;
                    }
                }

                // Если все поля валидны, передаем данные обратно в SignInActivity
                if (valid) {
                    Toast.makeText(getContext(), "Регистрация успешна", Toast.LENGTH_SHORT).show();

                    String userName = name.getText().toString();
                    String userEmail = email.getText().toString();

                    SignUpFragmentDirections.ActionSignUpFragmentToSignInFragment action =
                            SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                                    .setUSERNAME(userName)
                                    .setUSEREMAIL(userEmail);

                    navController.navigate(action);
                }
            }
        });

        return view;
    }
}