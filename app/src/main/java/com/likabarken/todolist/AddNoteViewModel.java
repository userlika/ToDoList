package com.likabarken.todolist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNoteViewModel extends AndroidViewModel {

    private NotesDao notesDao;

    // viewModel и Activity могут общаться при помощи объекта LiveData
    private MutableLiveData<Boolean> shouldCloseScreen = new MutableLiveData<>();
    //Disposable позволяет управлять жизненным циклом подписок
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    //private Disposable disposable;


    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        notesDao = NoteDatabase.getInstance(application).notesDao();
    }

    public LiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }

    public void saveNote(Note note) {
        // Чтобы метод add выполнился - на него надо подписаться
        // Все объекты в RxJava используют механизм callback-ов(н-р, слушатель клика или свайпа)
        Disposable disposable = notesDao.add(note)
                .subscribeOn(Schedulers.io()) // добавление в базу в фоновом потоке, аргумент - поток
                .observeOn(AndroidSchedulers.mainThread()) // переключить поток на главный поток
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Log.d("AddNoteViewModel", "subscribe");
                        shouldCloseScreen.setValue(true);
                    }
                });

        compositeDisposable.add(disposable);
        // setValue можно вызывать только на главном потоке
    }

    // В момент уничтожения ViewModel - у нее вызывается метод onCleared()

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
