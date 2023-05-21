package protection.model.dataobjects.measurements;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DATA;
import protection.model.common.DataAttribute;

@Getter @Setter
public class SEQ extends DATA {

    private CMV C1 = new CMV();

    private CMV C2 = new CMV();

    private CMV C3 = new CMV();
}
