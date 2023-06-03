package protection.model.logicalnodes.Breaker;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.protection.ACT;
import protection.model.logicalnodes.commands.DPC;
import protection.model.logicalnodes.common.LN;


public class CSWI extends LN {

    @Setter @Getter
    private ACT OpOpn1 = new ACT();
    @Setter @Getter
    private ACT OpOpn2 = new ACT();
    @Setter @Getter
    private ACT OpOpn3 = new ACT();
    @Setter @Getter
    private ACT OpOpn4 = new ACT();
    @Setter @Getter
    private ACT OpOpn5 = new ACT();
    @Setter @Getter
    private ACT OpOpn6 = new ACT();
    @Setter @Getter
    private ACT Opcls = new ACT();
    @Getter @Setter
    private DPC Pos = new DPC();




    @Override
    public void process() {



        if (OpOpn1.getGeneral().getValue() || OpOpn2.getGeneral().getValue()
        || OpOpn3.getGeneral().getValue() || OpOpn4.getGeneral().getValue()
                || OpOpn5.getGeneral().getValue() || OpOpn6.getGeneral().getValue()
        ) {

            Pos.getCtVal().setValue(false);

        }
        else Pos.getCtVal().setValue(true);


    }
}
