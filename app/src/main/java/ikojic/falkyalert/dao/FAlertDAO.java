package ikojic.falkyalert.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import ikojic.falkyalert.model.FAlert;

@Dao
public interface FAlertDAO {

    @Query("SELECT * FROM falert ORDER BY id DESC")
    LiveData<List<FAlert>> dohvatiFAlert();

    @Insert
    void dodajNoviFAlert(FAlert fAlert);

    @Update
    void promijeniFAlert(FAlert fAlert);

    @Delete
    void obrisiFAlert(FAlert fAlert);
}
