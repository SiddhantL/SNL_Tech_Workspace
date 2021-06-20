package com.example.snltech.ui.blog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BlogViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BlogViewModel() {
        mText = new MutableLiveData<>();
      //  mText.setValue("This is blog fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}