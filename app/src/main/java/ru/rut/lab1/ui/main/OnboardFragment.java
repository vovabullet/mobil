package ru.rut.lab1.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import ru.rut.lab1.R;
import ru.rut.lab1.databinding.FragmentOnboardBinding;

/**
 * Fragment для приветственного экрана (onboarding)
 */
public class OnboardFragment extends Fragment {
    private FragmentOnboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOnboardBinding. inflate(inflater, container, false);

        // обработчик нажатия на кнопку
        binding.nextButton.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(OnboardFragment.this);
            navController. navigate(R.id.action_onboardFragment_to_signInFragment);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
