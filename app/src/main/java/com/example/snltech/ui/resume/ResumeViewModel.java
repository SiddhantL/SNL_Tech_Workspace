package com.example.snltech.ui.resume;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResumeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ResumeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Resume fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}