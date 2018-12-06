package com.framgia.moneymanagement.data.repository;

import com.framgia.moneymanagement.data.AlarmDataSource;
import com.framgia.moneymanagement.data.model.Alarm;
import com.framgia.moneymanagement.data.source.remote.AlarmRemoteDataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ValueEventListener;

public class AlarmRepository implements AlarmDataSource.Remote {
    private AlarmDataSource.Remote mRemote;

    public AlarmRepository(AlarmDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public void createAlarm(Alarm alarm) {
        mRemote.createAlarm(alarm);
    }

    @Override
    public void getDataAlarm(ValueEventListener valueEventListener) {
        mRemote.getDataAlarm(valueEventListener);
    }

    @Override
    public void deleteAlarm(String id, OnCompleteListener onCompleteListener,
                            OnFailureListener onFailureListener) {
        mRemote.deleteAlarm(id, onCompleteListener, onFailureListener);
    }
}
