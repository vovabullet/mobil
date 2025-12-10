package ru.rut.lab1.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import ru.rut.lab1.R;
import ru.rut.lab1.data.BackupManager;
import ru.rut.lab1.data.CharacterStorage;

public class SettingsFragment extends Fragment {

    // конст для SharedPreferences
    private static final String PREFS_NAME = "app_settings";
    private static final String KEY_NICKNAME = "nickname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NOTIFICATIONS = "notifications";
    private static final String KEY_DARK_THEME = "dark_theme";
    private static final String KEY_FONT_SIZE = "font_size";
    private static final String KEY_BACKUP_FILENAME = "backup_filename";

    // Значения по умолчанию
    private static final String DEFAULT_NICKNAME = "";
    private static final String DEFAULT_EMAIL = "";
    private static final boolean DEFAULT_NOTIFICATIONS = true;
    private static final boolean DEFAULT_DARK_THEME = false;
    private static final int DEFAULT_FONT_SIZE = 5;
    private static final String DEFAULT_BACKUP_FILENAME = "backup_6"; // 6 вариант

    // UI элементы
    private TextInputEditText etNickname, etEmail, etBackupFileName;
    private SwitchMaterial switchNotifications, switchDarkTheme;
    private SeekBar seekBarFontSize;
    private TextView tvFileStatus, tvFileName, tvFileSize, tvFileDate, tvBackupStatus;
    private Button btnCreateBackup, btnDeleteFile, btnRestoreBackup, btnSaveSettings;

    private SharedPreferences sharedPreferences;
    private BackupManager backupManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        backupManager = new BackupManager(requireContext());

        initViews(view);
        loadSettings();
        setupListeners();
        updateFileInfo();
    }

    private void initViews(View view) {
        etNickname = view.findViewById(R.id.etNickname);
        etEmail = view.findViewById(R. id.etEmail);

        switchNotifications = view.findViewById(R.id.switchNotifications);
        switchDarkTheme = view.findViewById(R.id.switchDarkTheme);
        seekBarFontSize = view.findViewById(R.id.seekBarFontSize);

        etBackupFileName = view.findViewById(R.id.etBackupFileName);
        tvFileStatus = view.findViewById(R.id.tvFileStatus);
        tvFileName = view.findViewById(R.id.tvFileName);
        tvFileSize = view.findViewById(R.id.tvFileSize);
        tvFileDate = view.findViewById(R.id.tvFileDate);
        tvBackupStatus = view.findViewById(R.id.tvBackupStatus);

        btnCreateBackup = view.findViewById(R.id.btnCreateBackup);
        btnDeleteFile = view.findViewById(R.id.btnDeleteFile);
        btnRestoreBackup = view.findViewById(R.id.btnRestoreBackup);
        btnSaveSettings = view.findViewById(R.id.btnSaveSettings);
    }

    /**
     * Загрузка настроек из SharedPreferences
     */
    private void loadSettings() {
        etNickname.setText(sharedPreferences.getString(KEY_NICKNAME, DEFAULT_NICKNAME));
        etEmail.setText(sharedPreferences. getString(KEY_EMAIL, DEFAULT_EMAIL));
        switchNotifications.setChecked(sharedPreferences.getBoolean(KEY_NOTIFICATIONS, DEFAULT_NOTIFICATIONS));
        switchDarkTheme.setChecked(sharedPreferences.getBoolean(KEY_DARK_THEME, DEFAULT_DARK_THEME));
        seekBarFontSize.setProgress(sharedPreferences.getInt(KEY_FONT_SIZE, DEFAULT_FONT_SIZE));
        etBackupFileName.setText(sharedPreferences.getString(KEY_BACKUP_FILENAME, DEFAULT_BACKUP_FILENAME));

        // имя файла в BackupManager
        backupManager.setCustomFileName(etBackupFileName.getText().toString());

        applyTheme(switchDarkTheme.isChecked());
    }

    private void applyTheme(boolean isDarkTheme) {
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void saveSettings() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_NICKNAME, etNickname.getText().toString());
        editor.putString(KEY_EMAIL, etEmail.getText().toString());
        editor.putBoolean(KEY_NOTIFICATIONS, switchNotifications.isChecked());
        editor.putBoolean(KEY_DARK_THEME, switchDarkTheme.isChecked());
        editor.putInt(KEY_FONT_SIZE, seekBarFontSize.getProgress());
        editor.putString(KEY_BACKUP_FILENAME, etBackupFileName.getText().toString().trim());

        editor.apply();

        applyTheme(switchDarkTheme.isChecked());
        backupManager.setCustomFileName(etBackupFileName.getText().toString().trim());

        Toast.makeText(requireContext(), "Настройки сохранены", Toast.LENGTH_SHORT).show();
    }

    private void setupListeners() {
        // Кнопка сохранения
        btnSaveSettings.setOnClickListener(v -> saveSettings());

        btnCreateBackup.setOnClickListener(v -> {
            // есть ли данные
            if (!CharacterStorage.getInstance().hasData()) {
                Toast.makeText(getContext(), "Персонажи не загружены!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Обновляем имя файла перед сохранением
            backupManager.setCustomFileName(etBackupFileName.getText().toString());

            // создание бекап
            boolean success = backupManager.createBackup();

            if (success) {
                Toast.makeText(getContext(), "Файл создан!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Ошибка создания файла", Toast.LENGTH_SHORT).show();
            }

            updateFileInfo();
        });

        btnDeleteFile.setOnClickListener(v -> {
            boolean success = backupManager.deleteExternalFile();

            if (success) {
                Toast.makeText(getContext(), "Файл удалён (копия сохранена)", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Файл не найден", Toast.LENGTH_SHORT).show();
            }

            updateFileInfo();
        });

        btnRestoreBackup. setOnClickListener(v -> {
            boolean success = backupManager.restoreFromBackup();

            if (success) {
                Toast.makeText(getContext(), "Файл восстановлен", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Резервная копия не найдена!", Toast.LENGTH_SHORT).show();
            }

            updateFileInfo();
        });
    }

    private void updateFileInfo() {
        // Информация о внешнем файле
        BackupManager.FileInfo fileInfo = backupManager.getExternalFileInfo();

        if (fileInfo != null) {
            tvFileStatus.setText("Статус файла: создан");
            tvFileName.setVisibility(View. VISIBLE);
            tvFileName.setText("Имя: " + fileInfo.name);
            tvFileSize.setVisibility(View. VISIBLE);
            tvFileSize.setText("Размер: " + fileInfo.getFormattedSize());
            tvFileDate.setVisibility(View.VISIBLE);
            tvFileDate.setText("Изменён: " + fileInfo.getFormattedDate());

            btnDeleteFile.setEnabled(true);
            btnCreateBackup.setText("Обновить");
        } else {
            tvFileStatus.setText("Статус файла: не создан");
            tvFileName.setVisibility(View.GONE);
            tvFileSize.setVisibility(View.GONE);
            tvFileDate.setVisibility(View.GONE);

            btnDeleteFile.setEnabled(false);
            btnCreateBackup.setText("Создать");
        }

        // Информация о резервной копии
        if (backupManager.backupExists()) {
            tvBackupStatus.setText("Резервная копия: имеется");
            btnRestoreBackup.setEnabled(true);
        } else {
            tvBackupStatus.setText("Резервная копия: отсутствует");
            btnRestoreBackup.setEnabled(false);
        }
    }

    /**
     * Автосохранение при уходе с экрана
     */
    @Override
    public void onPause() {
        super.onPause();
//        saveSettings();
    }
}