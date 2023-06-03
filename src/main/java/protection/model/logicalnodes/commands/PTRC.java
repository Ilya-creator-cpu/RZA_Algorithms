package protection.model.logicalnodes.commands;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.protection.ACT;
import protection.model.logicalnodes.common.LN;


@Getter @Setter
public class PTRC extends LN {


    private ACT Tr = new ACT();

    private ACT Op = new ACT();

    private SPS Blk1 = new SPS();

    private SPS Blk2 = new SPS();


    private SPS Blk3 = new SPS();

    private ACT OpCls1 = new ACT();

    private ACT OpCls2 = new ACT();


    private ACT OpOpn = new ACT();
    @Override
    public void process() {



        if (Blk1.getСtVal().getValue() || Blk2.getСtVal().getValue() || Blk3.getСtVal().getValue()) {

            Op.getGeneral().setValue(true);

        } else
            Op.getGeneral().setValue(false);
        if (OpCls1.getGeneral().getValue() || OpCls2.getGeneral().getValue()) {

            OpOpn.getGeneral().setValue(false);

        } else
            OpOpn.getGeneral().setValue(true);
    }
}
