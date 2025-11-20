package ru.rut.lab1.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import ru.rut.lab1.R;

/**
 * Fragment для входа пользователя в систему
 */
public class SignInFragment extends Fragment {
    // TextView для отображения информации о зарегистрированном пользователе
    private TextView registeredUserInfo;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        // TextView для отображения информации о зарегистрированном пользователе
        registeredUserInfo = view.findViewById(R.id.registered_user_info);

        NavController navController = NavHostFragment.findNavController(SignInFragment.this);

        // кнопки входа и регистрации
        Button signInButton = view.findViewById(R.id.sign_in_button);
        Button signUpButton = view.findViewById(R.id.sign_up_button);

        // данные от SignUpActivity (если они были переданы)
        SignInFragmentArgs args = SignInFragmentArgs.fromBundle(getArguments() != null ? getArguments() : new Bundle());
        // TODO переименовать id, чтобы названия методов генерились в camelCase
        String userName = args.getUSERNAME();
        String userEmail = args.getUSEREMAIL();

        if (userName != null && userEmail != null) {
            displayUserInfo("SafeArgs", userName, userEmail);
        }

        // слушатель для кнопки входа
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // поля ввода email и пароля
                EditText email = view.findViewById(R.id.email_input);
                EditText password = view.findViewById(R.id.password_input);

                // оба поля заполнены ?
                if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    // Сейчас просто принимаем любые непустые значения
                    Toast.makeText(requireContext(), "Вход выполнен!", Toast.LENGTH_SHORT).show();

                    // переход на другой фрагмент
                    navController.navigate(R.id.action_signInFragment_to_homeFragment);

                } else {
                    Toast.makeText(requireContext(), "Заполните поля", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // слушатель для кнопки регистрации
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });

        return view;
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
