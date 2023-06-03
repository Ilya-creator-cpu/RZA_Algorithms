package protection.model.logicalnodes.Breaker;

import lombok.Getter;
import lombok.Setter;
import protection.model.logicalnodes.commands.DPC;
import protection.model.logicalnodes.commands.SPS;
import protection.model.logicalnodes.common.LN;
@Getter @Setter
public class XCBR extends LN {

    private DPC Pos = new DPC(); /** Switch Position */
    @Override
    public void process() {


    }
}
