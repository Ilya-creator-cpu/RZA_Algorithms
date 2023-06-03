package protection.model.logicalnodes.commands;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.HWYE;
import protection.model.dataobjects.protection.ACD;
import protection.model.logicalnodes.common.LN;


@Getter @Setter
public class PHAR extends LN {


    private ACD Str = new ACD();

    private HWYE HA = new HWYE();

    private SPS BlK = new SPS();


    private int count;


    @Override
    public void process() {
        BlK.getСtVal().setValue(false);

        if (HA.getPhsAHar().get(4).getCVal().getMag().getF().getValue()/ HA.getPhsAHar().get(0).getCVal().getMag().getF().getValue()*100 > 30 ||
        HA.getPhsBHar().get(4).getCVal().getMag().getF().getValue()/ HA.getPhsBHar().get(0).getCVal().getMag().getF().getValue()*100 > 30
        ||   HA.getPhsCHar().get(4).getCVal().getMag().getF().getValue()/HA.getPhsCHar().get(0).getCVal().getMag().getF().getValue()*100 > 30 )
            count++;
        else
            count = 0;
        if (count > 5) {

            BlK.getСtVal().setValue(true);
        }


    }
}
