package ru.rut.lab1.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.rut.lab1.R;

/**
 * Fragment для приветственного экрана (onboarding)
 */
public class OnboardFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboard, container, false);

        // кнопка "Далее" по ID из XML
        Button nextButton = view.findViewById(R.id.next_button);

        // обработчик нажатия на кнопку
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).openFragment(new SignInFragment());
            }
        });

        return view;
    }
}
