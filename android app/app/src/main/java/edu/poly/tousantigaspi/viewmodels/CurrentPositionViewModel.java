package edu.poly.tousantigaspi.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CurrentPositionViewModel extends ViewModel {
    private final MutableLiveData<Integer> currentPosition = new MutableLiveData<>(0);

    public MutableLiveData<Integer> getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Integer currentPosition) {
        this.currentPosition.setValue(currentPosition);
    }
}
