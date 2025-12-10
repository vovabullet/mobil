package ru.rut.lab1.data;

import android. content.Context;
import android.os. Environment;
import android.util.Log;

import java.io.File;
import java. io.FileInputStream;
import java. io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util. List;
import java.util. Locale;

import ru.rut.lab1.data.model.Character;

public class BackupManager {

    private static final String TAG = "BackupManager";
    private static final String DEFAULT_FILENAME = "backup_data";

    private final Context context;
    private String customFileName = null;

    public BackupManager(Context context) {
        this.context = context.getApplicationContext();
    }

    // ============ НАСТРОЙКИ ============

    public void setCustomFileName(String fileName) {
        this.customFileName = fileName;
    }

    public String getBackupFileName() {
        String name = (customFileName != null && !customFileName.isEmpty()) ?  customFileName : DEFAULT_FILENAME;
        return name + ".txt";
    }

    // ============ ПУТИ К ФАЙЛАМ ============

    public File getExternalFile() {
        File documentsDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);
        return new File(documentsDir, getBackupFileName());
    }

    public File getInternalBackupFile() {
        // FIXME создается файл backup_backup_data (два раза бекап)
        return new File(context. getFilesDir(), "backup_" + getBackupFileName());
    }

    // ============ СОЗДАНИЕ БЕКАПА ============

    public boolean createBackup(List<Character> characters) {
        if (characters == null || characters.isEmpty()) {
            Log.w(TAG, "Нет данных для сохранения");
            return false;
        }

        try {
            File externalFile = getExternalFile();

            // создвние директории, если не существует
            if (!externalFile.getParentFile().exists()) {
                externalFile.getParentFile().mkdirs();
            }

            // форматирование текст
            String dataToSave = formatCharactersToText(characters);

            // запись в файл
            FileOutputStream fos = new FileOutputStream(externalFile);
            fos.write(dataToSave.getBytes());
            fos.close();

            Log.i(TAG, "Бекап создан: " + externalFile.getAbsolutePath());
            return true;

        } catch (IOException e) {
            Log.e(TAG, "Ошибка создания бекапа: " + e.getMessage());
            return false;
        }
    }

    // берёт данные из CharacterStorage
    public boolean createBackup() {
        List<Character> characters = CharacterStorage.getInstance().getCharacters();
        return createBackup(characters);
    }

    // ============ УДАЛЕНИЕ С СОХРАНЕНИЕМ КОПИИ ============


    public boolean deleteExternalFile() {
        File externalFile = getExternalFile();

        if (! externalFile.exists()) {
            return false;
        }

        //сначала сохранение во внутреннее хранилище
        saveToInternalStorage(externalFile);

        //потом удаление внешнего файла
        boolean deleted = externalFile.delete();
        Log.i(TAG, "Файл удалён: " + deleted);
        return deleted;
    }

    private void saveToInternalStorage(File sourceFile) {
        try {
            File internalFile = getInternalBackupFile();

            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(internalFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

            fis.close();
            fos.close();

            Log.i(TAG, "Копия сохранена во внутреннее хранилище");

        } catch (IOException e) {
            Log.e(TAG, "Ошибка сохранения копии: " + e.getMessage());
        }
    }

    // ============ ВОССТАНОВЛЕНИЕ ============

    public boolean restoreFromBackup() {
        File internalFile = getInternalBackupFile();
        File externalFile = getExternalFile();

        if (!internalFile.exists()) {
            Log.w(TAG, "Резервная копия не найдена");
            return false;
        }

        try {
            if (!externalFile.getParentFile().exists()) {
                externalFile.getParentFile().mkdirs();
            }

            FileInputStream fis = new FileInputStream(internalFile);
            FileOutputStream fos = new FileOutputStream(externalFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

            fis.close();
            fos.close();

            Log.i(TAG, "Файл восстановлен");
            return true;

        } catch (IOException e) {
            Log.e(TAG, "Ошибка восстановления: " + e.getMessage());
            return false;
        }
    }

    // ============ ИНФОРМАЦИЯ О ФАЙЛАХ ============

    public boolean externalFileExists() {
        return getExternalFile().exists();
    }

    public boolean backupExists() {
        return getInternalBackupFile().exists();
    }

    public FileInfo getExternalFileInfo() {
        File file = getExternalFile();
        if (!file.exists()) {
            return null;
        }
        return new FileInfo(file);
    }

    // ============ ФОРМАТИРОВАНИЕ ДАННЫХ ============

    private String formatCharactersToText(List<Character> characters) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Персонажи Game of Thrones ===\n");
        sb.append("Дата создания: ").append(
                new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale. getDefault())
                        .format(new Date())).append("\n");
        sb.append("Всего: ").append(characters.size()).append(" персонажей\n");
        sb.append("=====================================\n\n");

        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);
            sb.append("--- Персонаж #").append(i + 1).append(" ---\n");
            sb.append("Имя: ").append(c. getName(). isEmpty() ? "Неизвестно" : c.getName()). append("\n");
            sb.append("Культура: ").append(c.getCulture()).append("\n");
            sb.append("Рождение: ").append(c.getBorn()).append("\n");
            sb.append("Титулы: ").append(String.join(", ", c.getTitles())).append("\n");
            sb. append("Прозвища: "). append(String.join(", ", c. getAliases())).append("\n");
            sb.append("Актёры: ").append(String.join(", ", c.getPlayedBy())).append("\n");
            sb.append("\n");
        }

        return sb.toString();
    }

    // ============ ВСПОМОГАТЕЛЬНЫЙ КЛАСС ============

    public static class FileInfo {
        public final String name;
        public final String path;
        public final long size;
        public final long lastModified;

        public FileInfo(File file) {
            this.name = file.getName();
            this.path = file.getAbsolutePath();
            this.size = file.length();
            this.lastModified = file.lastModified();
        }

        public String getFormattedSize() {
            if (size < 1024) return size + " B";
            if (size < 1024 * 1024) return String.format(Locale.getDefault(), "%.2f KB", size / 1024.0);
            return String.format(Locale.getDefault(), "%.2f MB", size / (1024.0 * 1024.0));
        }

        public String getFormattedDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
            return sdf.format(new Date(lastModified));
        }
    }
}