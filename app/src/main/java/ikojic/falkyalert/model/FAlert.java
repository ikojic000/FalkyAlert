package ikojic.falkyalert.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "falert")
public class FAlert implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String naziv;
    private int tip;
    private String rok;
    private String opis;
    private String slika;

}