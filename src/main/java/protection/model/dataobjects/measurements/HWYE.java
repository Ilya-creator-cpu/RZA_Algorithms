package protection.model.dataobjects.measurements;
import lombok.Getter;
import lombok.Setter;
import protection.model.common.DATA;
import protection.model.common.DataAttribute;
import protection.model.logicalnodes.common.LN;

import java.util.ArrayList;


@Getter @Setter
public class HWYE extends DATA {

    private ArrayList<CMV> phsAHar = new ArrayList<>();
    private ArrayList<CMV> phsBHar = new ArrayList<>();
    private ArrayList<CMV> phsCHar = new ArrayList<>();
    private ArrayList<CMV> neutHar = new ArrayList<>();

    private int numberOfHarm = 5;
    private DataAttribute<Integer> numHar = new DataAttribute<>(5);


    public HWYE() {
        for (int i = 0; i < 5; i++) {
            phsAHar.add(new CMV());
            phsBHar.add(new CMV());
            phsCHar.add(new CMV());
            neutHar.add(new CMV());
        }
    }
}
