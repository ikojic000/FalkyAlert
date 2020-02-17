package ikojic.falkyalert.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import ikojic.falkyalert.model.FAlert;

//singleton
@Database(entities = {FAlert.class}, version = 1, exportSchema = false)
public abstract class FAlertBase extends RoomDatabase {

    public abstract FAlertDAO falertDAO();

    private static FAlertBase INSTANCE;

    public static FAlertBase getBase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FAlertBase.class, "falertBase").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
