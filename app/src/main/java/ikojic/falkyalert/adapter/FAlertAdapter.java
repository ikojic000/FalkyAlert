package ikojic.falkyalert.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;
import java.util.List;
import ikojic.falkyalert.R;
import ikojic.falkyalert.model.FAlert;
import lombok.NonNull;

public class FAlertAdapter extends ArrayAdapter<FAlert> {

    private List<FAlert> alerti;
    private FAlertClickListener fAlertClickListener;
    private int resource;
    private Context context;

    public FAlertAdapter(@NonNull Context context, int resource, FAlertClickListener fAlertClickListener) {
        super(context, resource);

        this.resource = resource;
        this.context = context;
        this.fAlertClickListener = fAlertClickListener;
    }

    private static class ViewHolder {

        private TextView naziv;
        private ImageView slika;
        private TextView rok;
    }

    @androidx.annotation.NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @androidx.annotation.NonNull ViewGroup parent) {

        View view = convertView;
        FAlert fAlert;
        ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                view = inflater.inflate(this.resource, null);

                viewHolder.naziv = view.findViewById(R.id.naziv_tip);
                viewHolder.rok = view.findViewById(R.id.rok);
                viewHolder.slika = view.findViewById(R.id.slika);
            } else {
                viewHolder = (ViewHolder) view.getTag();

            }

            fAlert = getItem(position);

            if (fAlert != null) {
                viewHolder.naziv.setText(fAlert.getNaziv() + " - " + context.getResources().getStringArray(R.array.tip_fAlert)[fAlert.getTip()] + " - " + fAlert.getRok());
                if (fAlert.getSlika() == null) {
                    Picasso.get().load(R.drawable.falkyalert).fit().centerCrop().into(viewHolder.slika);
                } else {
                    Picasso.get().load(fAlert.getSlika()).fit().centerCrop().into(viewHolder.slika);
                }
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fAlertClickListener.onItemClick(fAlert);
                }
            });
        }
        return view;
    }

    @Override
    public int getCount() {
        return alerti == null ? 0 : alerti.size();
    }

    @Nullable
    @Override
    public FAlert getItem(int position) {
        return alerti.get(position);
    }

    public void setAlerti(List<FAlert> alerti) {
        this.alerti = alerti;
    }

    public void osvjeziAlerte() {
        notifyDataSetChanged();
    }
}
