package com.framgia.moneymanagement.data.source.remote;

import com.framgia.moneymanagement.data.AlarmDataSource;
import com.framgia.moneymanagement.data.model.Alarm;
import com.framgia.moneymanagement.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AlarmRemoteDataSource implements AlarmDataSource.Remote {
    private FirebaseDatabase mDatabase;

    public AlarmRemoteDataSource(FirebaseDatabase database) {
        mDatabase = database;
    }

    @Override
    public void createAlarm(Alarm alarm) {
        DatabaseReference reference = mDatabase.getReference(User.Key.USER);
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                child(Alarm.Key.ALARM).
                child(alarm.getId()).
                setValue(alarm);
    }

    @Override
    public void getDataAlarm(ValueEventListener valueEventListener) {
        mDatabase.getReference(User.Key.USER)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(Alarm.Key.ALARM)
                .addValueEventListener(valueEventListener);
    }

    @Override
    public void deleteAlarm(String id, OnCompleteListener onCompleteListener,
                            OnFailureListener onFailureListener) {
        mDatabase.getReference(User.Key.USER).
                child(FirebaseAuth.getInstance().getUid()).
                child(Alarm.Key.ALARM).
                child(id).
                removeValue().
                addOnCompleteListener(onCompleteListener).
                addOnFailureListener(onFailureListener);
    }
}
