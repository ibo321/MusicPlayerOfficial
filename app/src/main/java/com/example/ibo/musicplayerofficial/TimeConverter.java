package com.example.ibo.musicplayerofficial;

import android.os.Build;

import java.time.LocalDate;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

/**
 * Created by Ibrahim Delice on 09,March,2019
 */
public class TimeConverter {


    private TimeConverter() {
        throw new AssertionError();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter()
    public static LocalDate fromLong(@Nullable Long epoch) {
        return epoch == null ? null : LocalDate.ofEpochDay(epoch);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static Long localDateToEpoch(@Nullable LocalDate localDate) {
        return localDate == null ? null : localDate.toEpochDay();
    }

}
