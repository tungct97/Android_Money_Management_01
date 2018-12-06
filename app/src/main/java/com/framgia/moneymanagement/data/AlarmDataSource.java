package com.framgia.moneymanagement.data;

import com.framgia.moneymanagement.data.model.Alarm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ValueEventListener;

public interface AlarmDataSource {
    interface Remote{
        void createAlarm(Alarm alarm);

        void getDataAlarm(ValueEventListener valueEventListener);

        void deleteAlarm(String id,
                         OnCompleteListener onCompleteListener,
                         OnFailureListener onFailureListener);
    }
}
