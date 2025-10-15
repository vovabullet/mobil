package ru.rut.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Activity для приветственного экрана (onboarding).
 * Наследуется от BaseActivity для логирования жизненного цикла.
 */
public class OnboardActivity extends BaseActivity {
    
    /**
     * Инициализация Activity при создании.
     * Устанавливает layout и настраивает кнопку перехода на экран входа.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // тег для логирования
        TAG = "OnboardActivity";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        // кнопка "Далее" по ID из XML
        Button nextButton = findViewById(R.id.next_button);
        
        // обработчик нажатия на кнопку
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent для перехода на SignInActivity
                Intent intent = new Intent(OnboardActivity.this, SignInActivity.class);
                // новуа Activity
                startActivity(intent);
            }
        });
    }
}