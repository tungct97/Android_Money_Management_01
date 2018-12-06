package com.framgia.moneymanagement.screen.alarm;

import android.support.annotation.NonNull;

import com.framgia.moneymanagement.data.model.Alarm;
import com.framgia.moneymanagement.data.repository.AlarmRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AlarmPresenter implements AlarmContact.Presenter {
    private AlarmContact.View mView;
    private AlarmRepository mAlarmRepository;

    public AlarmPresenter(AlarmContact.View view, AlarmRepository alarmRepository) {
        mView = view;
        mAlarmRepository = alarmRepository;
    }

    @Override
    public void createAlarm(Alarm alarm) {
        mAlarmRepository.createAlarm(alarm);
    }

    @Override
    public void getAlarm() {
        mAlarmRepository.getDataAlarm(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Alarm> alarmList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Alarm alarm = snapshot.getValue(Alarm.class);
                    alarmList.add(alarm);
                }
                mView.onGetDataAlarm(alarmList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mView.onGetDataAlarmFail(databaseError.getMessage());
            }
        });
    }

    @Override
    public void deleteAlarm(String id) {
        mAlarmRepository.deleteAlarm(id, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    mView.onDeleteAlarmSucces();
                }
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mView.onDeleteAlarmFail(e.getMessage());
            }
        });
    }
}
