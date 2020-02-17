package ikojic.falkyalert.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ikojic.falkyalert.R;
import ikojic.falkyalert.adapter.FAlertAdapter;
import ikojic.falkyalert.adapter.FAlertClickListener;
import ikojic.falkyalert.model.FAlert;
import ikojic.falkyalert.viewmodel.FAlertViewModel;

public class ReadFragment extends Fragment {

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lista)
    ListView listView;

    private FAlertViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read,
                container, false);
        ButterKnife.bind(this,view);

        model = ((MainActivity)getActivity()).getModel();

        definirajListu();
        definirajSwipe();
        osvjeziPodatke();

        return view;
    }

    private void osvjeziPodatke(){
        model.dohvatiFAlert().observe(this, new Observer<List<FAlert>>() {
            @Override
            public void onChanged(@Nullable List<FAlert> fAlertList) {
                swipeRefreshLayout.setRefreshing(false);
                ((FAlertAdapter)listView.getAdapter()).setAlerti(fAlertList);
                ((FAlertAdapter) listView.getAdapter()).osvjeziAlerte();

            }
        });
    }
    private void definirajSwipe() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                osvjeziPodatke();
            }
        });

    }

    private void definirajListu() {

        listView.setAdapter( new FAlertAdapter(getActivity(), R.layout.red_liste, new FAlertClickListener() {
            @Override
            public void onItemClick(FAlert fAlert) {
                model.setfAlert(fAlert);
                ((MainActivity)getActivity()).cud();
            }
        }));
    }

    @OnClick(R.id.fab)
    public void noviFAlert(){
        model.setfAlert(new FAlert());
        ((MainActivity)getActivity()).cud();
    }


}