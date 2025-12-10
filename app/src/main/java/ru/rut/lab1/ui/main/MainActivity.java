package ru.rut.lab1.ui.main;

import android.os.Bundle;
import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import ru.rut.lab1.R;
import ru.rut.lab1.databinding.FragmentMainBinding;
import ru.rut.lab1.ui.base.BaseActivity;
import ru.rut.lab1.utils.Helpers;

public class MainActivity extends BaseActivity {
    protected String TAG = "MainActivity";
    private NavController navController;
    private FragmentMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // применяет тему из настроек
        Helpers.applyThemeFromPrefs(this);

        super.onCreate(savedInstanceState);
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Находим NavHostFragment из разметки
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            // для Toolbar/ActionBar
            // NavigationUI.setupActionBarWithNavController(this, navController);
        } else {
            Log.e(TAG, "navController is null!");
        }
    }

    // для кнопки "Назад" в ActionBar
    @Override
    public boolean onSupportNavigateUp() {
        return navController != null && navController.navigateUp() || super.onSupportNavigateUp();
    }
}