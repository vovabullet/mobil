package ru.rut.lab1.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import ru.rut.lab1.databinding.FragmentTronesBinding;
import ru.rut.lab1.ui.adapter.CharacterAdapter;
import ru.rut.lab1.data.repository.CharacterRepository;

public class TronesFragment extends Fragment {

    private FragmentTronesBinding binding;
    private CharacterAdapter adapter;
    private CharacterRepository repository;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private Disposable charactersSubscription;

    // вариант 6
    private int currentBatchNumber = 6;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTronesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new CharacterAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        repository = new CharacterRepository(requireContext());

        // подписка на Room (реактивное обновление списка)
        subscribeToBatch(currentBatchNumber);

        // холодный старт - если в Room пусто, автоматически грузим из API и сохраняем
        runWarmStart(currentBatchNumber);

        binding.btnRefresh.setOnClickListener(v -> {
            // обновить - всегда перезагружаем из API и перезаписываем в Room
            binding.progressBar.setVisibility(View.VISIBLE);
            disposables.add(
                    repository.refresh(currentBatchNumber)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doFinally(() -> binding.progressBar.setVisibility(View.GONE))
                            .subscribe(
                                    () -> Toast.makeText(getContext(), "Обновлено", Toast.LENGTH_SHORT).show(),
                                    err -> Toast.makeText(getContext(), "Оштбка обновления: " + err.getMessage(), Toast.LENGTH_LONG).show()
                            )
            );
        });

        binding.btnLoadMore.setOnClickListener(v -> {
            // загрузить ещё - меняем порядковый номер набора
            currentBatchNumber++;

            subscribeToBatch(currentBatchNumber);
            runWarmStart(currentBatchNumber);
        });
    }

    private void subscribeToBatch(int batchNumber) {
        // отменяем старую подписку на предыдущий batch
        if (charactersSubscription != null && !charactersSubscription.isDisposed()) {
            charactersSubscription.dispose();
        }

        charactersSubscription = repository.observeCharacters(batchNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        characters -> adapter.setCharacters(characters),
                        err -> Toast.makeText(getContext(), "Ошибка БД: " + err.getMessage(), Toast.LENGTH_LONG).show()
                );

        disposables.add(charactersSubscription);
    }

    private void runWarmStart(int batchNumber) {
        binding.progressBar.setVisibility(View.VISIBLE);
        disposables.add(
                repository.warmStartIfEmpty(batchNumber)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(() -> binding.progressBar.setVisibility(View.GONE))
                        .subscribe(
                                () -> { /* данные уже были или успешно загрузились */ },
                                err -> Toast.makeText(getContext(), "Ошибка загрузки: " + err.getMessage(), Toast.LENGTH_LONG).show()
                        )
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
        binding = null;
    }
}