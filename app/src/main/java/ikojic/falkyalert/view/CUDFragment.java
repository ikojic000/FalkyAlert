package ikojic.falkyalert.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ikojic.falkyalert.R;
import ikojic.falkyalert.viewmodel.FAlertViewModel;

public class CUDFragment extends Fragment {

    static final int SLIKANJE = 1;

    private String trenutnaPutanjaSlike;

    @BindView(R.id.naziv)
    EditText naziv;
    @BindView(R.id.tip_fAlert)
    Spinner tipFAlerta;
    @BindView(R.id.rok)
    EditText rok;
    @BindView(R.id.opis)
    EditText opis;
    @BindView(R.id.slika)
    ImageView slika;
    @BindView(R.id.noviFAlert)
    Button noviFAlert;
    @BindView(R.id.slikati)
    Button slikati;
    @BindView(R.id.mijenjanjeFAlert)
    Button mijenjanjeFAlert;
    @BindView(R.id.obrisiFAlert)
    Button obrisiFAlert;

    FAlertViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_cud, container, false);
        ButterKnife.bind(this, view);

        model = ((MainActivity) getActivity()).getModel();

        if (model.getfAlert().getId() == 0) {
            definirajNoviFAlert();
            return view;
        }

        definirajMijenjanjeBrisanjeFAlert();

        return view;

    }

    private void definirajMijenjanjeBrisanjeFAlert() {
        noviFAlert.setVisibility(View.GONE);
        tipFAlerta.setSelection(model.getfAlert().getTip());
        naziv.setText(model.getfAlert().getNaziv());
        rok.setText(model.getfAlert().getRok());
        opis.setText(model.getfAlert().getOpis());
        Picasso.get().load(model.getfAlert().getSlika()).error(R.drawable.falkyalert).into(slika);


        slikati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slikaj();
            }
        });

        mijenjanjeFAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mijenjanjeFAlert();
            }
        });

        obrisiFAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obrisiFAlert();
            }
        });
    }

    private void mijenjanjeFAlert() {
        model.getfAlert().setNaziv(naziv.getText().toString());
        model.getfAlert().setTip(tipFAlerta.getSelectedItemPosition());
        model.getfAlert().setRok(rok.getText().toString());
        model.getfAlert().setOpis(opis.getText().toString());
        model.promijeniFAlert();
        nazad();
    }

    private void definirajNoviFAlert() {
        mijenjanjeFAlert.setVisibility(View.GONE);
        obrisiFAlert.setVisibility(View.GONE);
        slikati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slikaj();
            }
        });
        noviFAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noviFAlert();
            }
        });
    }

    private void noviFAlert() {
        model.getfAlert().setNaziv(naziv.getText().toString());
        tipFAlerta.setSelection(0);
        model.getfAlert().setTip(tipFAlerta.getSelectedItemPosition());
        model.getfAlert().setRok(rok.getText().toString());
        model.getfAlert().setOpis(opis.getText().toString());
        model.dodajNoviFAlert();
        nazad();
    }

    private void obrisiFAlert() {
        model.getfAlert().setNaziv(naziv.getText().toString());
        model.getfAlert().setTip(tipFAlerta.getSelectedItemPosition());
        model.getfAlert().setRok(rok.getText().toString());
        model.getfAlert().setOpis(opis.getText().toString());
        model.obrisiFAlert();
        nazad();
    }

    @OnClick(R.id.nazad)
    public void nazad() {
        ((MainActivity) getActivity()).read();
    }


    private void slikaj() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) == null) {
            Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
            return;

        }

        File slika = null;
        try {
            slika = kreirajDatotekuSlike();
        } catch (IOException ex) {
            Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
            return;
        }

        if (slika == null) {
            Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
            return;
        }

        Uri slikaURI = FileProvider.getUriForFile(getActivity(),"ikojic.falkyalert.provider", slika);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, slikaURI);
        startActivityForResult(takePictureIntent, SLIKANJE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SLIKANJE && resultCode == Activity.RESULT_OK) {

            model.getfAlert().setSlika("file://" + trenutnaPutanjaSlike);
            model.promijeniFAlert();
            Picasso.get().load(model.getfAlert().getSlika()).into(slika);

        }
    }

    private File kreirajDatotekuSlike() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imeSlike = "FalkyAlert" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imeSlike,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        trenutnaPutanjaSlike = image.getAbsolutePath();
        return image;
    }


}
