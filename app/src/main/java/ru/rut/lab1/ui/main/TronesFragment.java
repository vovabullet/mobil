package ru.rut.lab1.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ru.rut.lab1.R;
import ru.rut.lab1.data.BackupManager;
import ru.rut.lab1.data.CharacterStorage;
import ru.rut.lab1.data.model.Character;
import ru.rut.lab1.data.repository.CharacterRepository;
import ru.rut.lab1.ui.adapter.CharacterAdapter;

public class TronesFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private CharacterAdapter adapter;
    private CharacterRepository repository;
    private BackupManager backupManager;

    // вариант 6
    private static final int START_ID = 251;
    private static final int END_ID = 300;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trones, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        adapter = new CharacterAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        repository = new CharacterRepository();
        backupManager = new BackupManager(requireContext());

        loadCharacters();
    }

    private void loadCharacters() {
        progressBar.setVisibility(View.VISIBLE);

        repository.loadCharacters(START_ID, END_ID, new CharacterRepository.CharacterCallback() {
            @Override
            public void onSuccess(List<Character> characters) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        adapter.setCharacters(characters);

                        // сохранение в общее хранилище
                        CharacterStorage.getInstance().setCharacters(characters);

                        // авто-бекап
                        boolean backupSuccess = backupManager.createBackup(characters);
                        if (backupSuccess) {
                            Toast.makeText(getContext(), "Бекап создан", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Бекап не создан!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onError(String message) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }
}