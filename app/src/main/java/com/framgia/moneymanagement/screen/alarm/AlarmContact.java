package com.framgia.moneymanagement.screen.alarm;

import com.framgia.moneymanagement.data.model.Alarm;

import java.util.List;

public interface AlarmContact {
    interface View {
        void onGetDataAlarm(List<Alarm> listIncome);

        void onGetDataAlarmFail(String msg);

        void onDeleteAlarmSucces();

        void onDeleteAlarmFail(String msg);
    }

    interface Presenter {
        void createAlarm(Alarm alarm);

        void getAlarm();

        void deleteAlarm(String id);
    }
}
