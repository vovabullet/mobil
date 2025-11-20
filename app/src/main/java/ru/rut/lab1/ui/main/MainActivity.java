package ru.rut.lab1.ui.main;

import android.os.Bundle;
import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import ru.rut.lab1.R;
import ru.rut.lab1.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {
    protected String TAG = "MainActivity";
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        // Находим NavHostFragment из разметки
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            // Если будет Toolbar/ActionBar
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