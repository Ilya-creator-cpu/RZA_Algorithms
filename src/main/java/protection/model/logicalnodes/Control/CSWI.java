package protection.model.logicalnodes.Control;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.protection.ACT;
@Getter
@Setter
public class CSWI {

    private ACT Open = new ACT();

    private ACT Close = new ACT();


}
