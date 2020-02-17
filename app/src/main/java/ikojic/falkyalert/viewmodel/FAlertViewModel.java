package ikojic.falkyalert.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import ikojic.falkyalert.dao.*;
import ikojic.falkyalert.model.FAlert;

public class FAlertViewModel extends AndroidViewModel {

    FAlertDAO fAlertDAO;

    private FAlert fAlert;

    public FAlert getfAlert() {
        return fAlert;
    }

    public void setfAlert(FAlert fAlert) {
        this.fAlert = fAlert;
    }

    private LiveData<List<FAlert>> fAlerti;

    public FAlertViewModel(Application application) {
        super(application);
        fAlertDAO = FAlertBase.getBase(application.getApplicationContext()).falertDAO();

    }

    public LiveData<List<FAlert>> dohvatiFAlert() {
        fAlerti = fAlertDAO.dohvatiFAlert();
        return fAlerti;
    }

    public void dodajNoviFAlert() {

        fAlertDAO.dodajNoviFAlert(fAlert);
    }

    public void promijeniFAlert() {

        fAlertDAO.promijeniFAlert(fAlert);
    }

    public void obrisiFAlert() {

        fAlertDAO.obrisiFAlert(fAlert);
    }

}