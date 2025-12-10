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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.rut.lab1.R;
import ru.rut.lab1.data.BackupManager;
import ru.rut.lab1.data.CharacterStorage;
import ru.rut.lab1.data.SettingsDataStore;
import ru.rut.lab1.databinding.FragmentSettingsBinding;
import ru.rut.lab1.utils.Helpers;

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
//    private static final String DEFAULT_NICKNAME = "";
//    private static final String DEFAULT_EMAIL = "";
//    private static final boolean DEFAULT_NOTIFICATIONS = true;
//    private static final boolean DEFAULT_DARK_THEME = false;
//    private static final int DEFAULT_FONT_SIZE = 5;
//    private static final String DEFAULT_BACKUP_FILENAME = "backup_6"; // 6 вариант

    // UI элементы (с биндингом больше не нужно
//    private TextInputEditText etNickname, etEmail, etBackupFileName;
//    private SwitchMaterial switchNotifications, switchDarkTheme;
//    private SeekBar seekBarFontSize;
//    private TextView tvFileStatus, tvFileName, tvFileSize, tvFileDate, tvBackupStatus;
//    private Button btnCreateBackup, btnDeleteFile, btnRestoreBackup, btnSaveSettings;

    private FragmentSettingsBinding binding;
    private SharedPreferences sharedPreferences; // Для темы, уведомлений, размера шрифта
    private SettingsDataStore dataStore; // Для nickname и email
    private BackupManager backupManager;
    // Для управления подписками RxJava
    private CompositeDisposable disposables = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        dataStore = new SettingsDataStore(requireContext());
        backupManager = new BackupManager(requireContext());
        disposables = new CompositeDisposable();

//        initViews(view);
        loadSettings();
        setupListeners();
        updateFileInfo();
    }

    // с биндингом это больше не нужно
//    private void initViews(View view) {
//        etNickname = view.findViewById(R.id.etNickname);
//        etEmail = view.findViewById(R. id.etEmail);
//
//        switchNotifications = view.findViewById(R.id.switchNotifications);
//        switchDarkTheme = view.findViewById(R.id.switchDarkTheme);
//        seekBarFontSize = view.findViewById(R.id.seekBarFontSize);
//
//        etBackupFileName = view.findViewById(R.id.etBackupFileName);
//        tvFileStatus = view.findViewById(R.id.tvFileStatus);
//        tvFileName = view.findViewById(R.id.tvFileName);
//        tvFileSize = view.findViewById(R.id.tvFileSize);
//        tvFileDate = view.findViewById(R.id.tvFileDate);
//        tvBackupStatus = view.findViewById(R.id.tvBackupStatus);
//
//        btnCreateBackup = view.findViewById(R.id.btnCreateBackup);
//        btnDeleteFile = view.findViewById(R.id.btnDeleteFile);
//        btnRestoreBackup = view.findViewById(R.id.btnRestoreBackup);
//        btnSaveSettings = view.findViewById(R.id.btnSaveSettings);
//    }

    /**
     * Загрузка настроек из SharedPreferences
     */
    private void loadSettings() {
        // DataStore:  nickname и email
        disposables.add(
                dataStore.getNickname()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        . subscribe(
                                nickname -> binding.etNickname.setText(nickname),
                                error -> {
                                    error.printStackTrace();
                                    Toast.makeText(getContext(), "Ошибка загрузки nickname", Toast.LENGTH_SHORT).show();
                                }
                        )
        );
        disposables.add(
                dataStore.getEmail()
                        . subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                email -> binding.etEmail.setText(email),
                                error -> {
                                    error.printStackTrace();
                                    Toast.makeText(getContext(), "Ошибка загрузки email", Toast.LENGTH_SHORT).show();
                                }
                        )
        );

        // SharedPreferences: остальное
        boolean notifications = sharedPreferences.getBoolean("notifications", true);
        boolean darkTheme = sharedPreferences.getBoolean("dark_theme", false);
        int fontSize = sharedPreferences.getInt("font_size", 14);

        binding.switchNotifications.setChecked(notifications);
        binding.switchDarkTheme.setChecked(darkTheme);
        binding.seekBarFontSize.setProgress(fontSize);

        // имя файла в BackupManager
        // вот так вроде правильнее
//        String backupFileName = sharedPreferences.getString(KEY_BACKUP_FILENAME, "backup_6");
//        binding.etBackupFileName.setText(backupFileName);
//        backupManager.setCustomFileName(backupFileName);
        backupManager.setCustomFileName(binding.etBackupFileName.getText().toString());
    }

    // TODO УДАЛИТЬ
    private void applyTheme(boolean isDarkTheme) {
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void saveSettings() {
        // DataStore
        String nickname = binding.etNickname.getText().toString();
        String email = binding. etEmail.getText().toString();
        disposables.add(
                dataStore.saveUserData(nickname, email)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers. mainThread())
                        .subscribe(
                                preferences -> Toast.makeText(getContext(), "Настройки сохранены", Toast.LENGTH_SHORT).show(),
                                error -> Toast.makeText(getContext(), "Ошибка сохранения", Toast.LENGTH_SHORT).show()
                        )
        );

        // SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(KEY_NOTIFICATIONS, binding.switchNotifications.isChecked());
        editor.putBoolean(KEY_DARK_THEME, binding.switchDarkTheme.isChecked());
        editor.putInt(KEY_FONT_SIZE, binding.seekBarFontSize.getProgress());
        editor.putString(KEY_BACKUP_FILENAME, binding.etBackupFileName.getText().toString().trim());

        editor.apply();

        Helpers.applyTheme(binding.switchDarkTheme.isChecked());
        backupManager.setCustomFileName(binding.etBackupFileName.getText().toString().trim());

        Toast.makeText(requireContext(), "Настройки сохранены", Toast.LENGTH_SHORT).show();
    }

    private void setupListeners() {
        // Кнопка сохранения
        binding.btnSaveSettings.setOnClickListener(v -> saveSettings());

        binding.btnCreateBackup.setOnClickListener(v -> {
            // есть ли данные
            if (!CharacterStorage.getInstance().hasData()) {
                Toast.makeText(getContext(), "Персонажи не загружены!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Обновляем имя файла перед сохранением
            backupManager.setCustomFileName(binding.etBackupFileName.getText().toString());

            // создание бекап
            boolean success = backupManager.createBackup();

            if (success) {
                Toast.makeText(getContext(), "Файл создан!", Toast.LENGTH_SHORT).show();
                binding.tvBackupStatus.setText("Бекап создан!");
            } else {
                Toast.makeText(getContext(), "Ошибка создания файла", Toast.LENGTH_SHORT).show();
                binding.tvBackupStatus.setText("Ошибка создания файла");
            }

            updateFileInfo();
        });

        binding.btnDeleteFile.setOnClickListener(v -> {
            boolean success = backupManager.deleteExternalFile();

            if (success) {
                Toast.makeText(getContext(), "Файл удалён (копия сохранена)", Toast.LENGTH_SHORT).show();
                binding.tvFileStatus.setText("Файл удалён (копия сохранена)");
            } else {
                Toast.makeText(getContext(), "Файл не найден", Toast.LENGTH_SHORT).show();
                binding.tvFileStatus.setText("Файл не найден");
            }

            updateFileInfo();
        });

        binding.btnRestoreBackup.setOnClickListener(v -> {
            boolean success = backupManager.restoreFromBackup();

            if (success) {
                Toast.makeText(getContext(), "Файл восстановлен", Toast.LENGTH_SHORT).show();
                binding.tvFileStatus.setText("Файл восстановлен");
            } else {
                Toast.makeText(getContext(), "Резервная копия не найдена!", Toast.LENGTH_SHORT).show();
                binding.tvFileStatus.setText("Резервная копия не найдена!");
            }

            updateFileInfo();
        });
    }

    private void updateFileInfo() {
        // Информация о внешнем файле
        BackupManager.FileInfo fileInfo = backupManager.getExternalFileInfo();

        if (fileInfo != null) {
//            binding.tvFileStatus.setText("Статус файла: создан");
            binding.tvFileName.setVisibility(View. VISIBLE);
            binding.tvFileName.setText("Имя: " + fileInfo.name);
            binding.tvFileSize.setVisibility(View. VISIBLE);
            binding.tvFileSize.setText("Размер: " + fileInfo.getFormattedSize());
            binding.tvFileDate.setVisibility(View.VISIBLE);
            binding.tvFileDate.setText("Изменён: " + fileInfo.getFormattedDate());

            binding.btnDeleteFile.setEnabled(true);
            binding.btnCreateBackup.setText("Обновить");
        } else {
//            binding.tvFileStatus.setText("Статус файла: не создан");
            binding.tvFileName.setVisibility(View.GONE);
            binding.tvFileSize.setVisibility(View.GONE);
            binding.tvFileDate.setVisibility(View.GONE);

            binding.btnDeleteFile.setEnabled(false);
            binding.btnCreateBackup.setText("Создать");
        }

        // Информация о резервной копии
        if (backupManager.backupExists()) {
            binding.tvBackupStatus.setText("Резервная копия: имеется");
            binding.btnRestoreBackup.setEnabled(true);
        } else {
            binding.tvBackupStatus.setText("Резервная копия: отсутствует");
            binding.btnRestoreBackup.setEnabled(false);
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

    @Override
    public void onDestroyView() {
        super. onDestroyView();
        // Очищаем подписки
        if (disposables != null) {
            disposables.clear();
        }
        binding = null;
    }
}