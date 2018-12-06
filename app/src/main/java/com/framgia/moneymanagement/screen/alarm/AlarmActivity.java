package com.framgia.moneymanagement.screen.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.framgia.moneymanagement.R;
import com.framgia.moneymanagement.data.model.Alarm;
import com.framgia.moneymanagement.data.repository.AlarmRepository;
import com.framgia.moneymanagement.data.source.remote.AlarmRemoteDataSource;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener,
        AlarmContact.View, AlarmAdapter.OnItemLongClickListener {
    private AlarmContact.Presenter mPresenter;
    private Toolbar mToolbar;
    private AlarmAdapter mAdapter;
    private Calendar mCalendar;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AlarmActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        findViewById(R.id.button_add_alarm).setOnClickListener(this);
        initViews();
        getData();
    }

    private void initViews() {
        RecyclerView recyclerView = findViewById(R.id.list_alarm);
        mAdapter = new AlarmAdapter(this);
        mCalendar = Calendar.getInstance();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        initToolbar();
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbarAlarm);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle(R.string.title_alarm);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getData() {
        mPresenter = new AlarmPresenter(this, new AlarmRepository(
                new AlarmRemoteDataSource(FirebaseDatabase.getInstance())));
        mPresenter.getAlarm();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_alarm:
                addAlarm();
                break;
            default:
                break;
        }
    }

    private void addAlarm() {
        LayoutInflater inflater = LayoutInflater.from(AlarmActivity.this);
        final View viewDialog = inflater.inflate(R.layout.dialog_alarm, null);
        new AlertDialog.Builder(AlarmActivity.this)
                .setTitle(R.string.title_alarm)
                .setView(viewDialog)
                .setPositiveButton(R.string.title_no, null)
                .setNegativeButton(R.string.title_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText textNoteAlarm = viewDialog.findViewById(R.id.text_note);
                        TimePicker timePicker = viewDialog.findViewById(R.id.time_alarm);
                        final String note = textNoteAlarm.getText().toString().trim();
                        mCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                        mCalendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                        int hour = timePicker.getCurrentHour();
                        int minute = timePicker.getCurrentMinute();
                        DatabaseReference reference =
                                FirebaseDatabase.getInstance().getReference(Alarm.Key.ALARM);
                        String id = reference.push().getKey();
                        Alarm alarm = new Alarm(id, note, hour, minute, true);
                        mPresenter.createAlarm(alarm);
                        pendingIntent(alarm);
                    }
                }).show();
    }

    public void pendingIntent(Alarm alarm) {
        Intent intent = new Intent(AlarmActivity.this, AlarmBroadcastReceiver.class);
        intent.putExtra(AlarmBroadcastReceiver.EXTRA_ALARM, alarm);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this,
                1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onGetDataAlarm(List<Alarm> alarms) {
        mAdapter.addData(alarms);
    }

    @Override
    public void onGetDataAlarmFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteAlarmSucces() {
        Toast.makeText(this, R.string.msg_delele_succes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteAlarmFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void OnLongClickListener(final String idAlarm) {
        new AlertDialog.Builder(AlarmActivity.this
        )
                .setMessage(R.string.title_delete)
                .setPositiveButton(R.string.title_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.deleteAlarm(idAlarm);
                    }
                })
                .setNegativeButton(R.string.title_no, null)
                .show();
    }
}
