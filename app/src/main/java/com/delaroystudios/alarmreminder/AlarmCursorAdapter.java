package com.delaroystudios.alarmreminder;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.delaroystudios.alarmreminder.data.AlarmReminderContract;

public class AlarmCursorAdapter extends CursorAdapter {

    private TextView mTitleText, mDateAndTimeText, mRepeatInfoText;
    private ImageView mActiveImage , mThumbnailImage;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;

    public AlarmCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.alarm_items, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        mTitleText = (TextView) view.findViewById(R.id.recycle_title);
        mDateAndTimeText = (TextView) view.findViewById(R.id.recycle_date_time);
        mRepeatInfoText = (TextView) view.findViewById(R.id.recycle_repeat_info);
        mActiveImage = (ImageView) view.findViewById(R.id.active_image);
        mThumbnailImage = (ImageView) view.findViewById(R.id.thumbnail_image);

        int titleColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_TITLE);
        int dateColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_DATE);
        int timeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_TIME);
        int repeatColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT);
        int repeatNoColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO);
        int repeatTypeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE);
        int activeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE);

        String title = cursor.getString(titleColumnIndex);
        String date = cursor.getString(dateColumnIndex);
        String time = cursor.getString(timeColumnIndex);
        String repeat = cursor.getString(repeatColumnIndex);
        String repeatNo = cursor.getString(repeatNoColumnIndex);
        String repeatType = cursor.getString(repeatTypeColumnIndex);
        String active = cursor.getString(activeColumnIndex);

        String dateTime = date + " " + time;

        setReminderTitle(title);
        setReminderDateTime(dateTime);
        setReminderRepeatInfo(repeat, repeatNo, repeatType);
        setActiveImage(active);
    }

    public void setReminderTitle(String title) {
        mTitleText.setText(title);
        String letter = "A";
        if(title != null && !title.isEmpty()) {
            letter = title.substring(0, 1);
        }
        int color = mColorGenerator.getRandomColor();
        mDrawableBuilder = TextDrawable.builder()
                .buildRound(letter, color);
        mThumbnailImage.setImageDrawable(mDrawableBuilder);
    }
    public void setReminderDateTime(String datetime) {
        mDateAndTimeText.setText(datetime);
    }
    public void setReminderRepeatInfo(String repeat, String repeatNo, String repeatType) {
        if(repeat.equals("true")){
            mRepeatInfoText.setText("Co każdy " + repeatNo + " " + repeatType + "(s)");
        }else if (repeat.equals("false")) {
            mRepeatInfoText.setText("Powtórzenie wyłączone");
        }
    }
    public void setActiveImage(String active){
        if(active.equals("true")){
            mActiveImage.setImageResource(R.drawable.ic_notifications_on_white_24dp);
        }else if (active.equals("false")) {
            mActiveImage.setImageResource(R.drawable.ic_notifications_off_grey600_24dp);
        }
    }
}