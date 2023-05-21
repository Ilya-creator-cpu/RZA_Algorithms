package protection.model.dataobjects.measurements;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DATA;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description
 */
public class WYE extends DATA {

    @Getter @Setter
    private CMV phsA = new CMV();

    @Getter @Setter
    private CMV phsB = new CMV();

    @Getter @Setter
    private CMV phsC = new CMV();

    @Getter @Setter
    private CMV neut = new CMV();
}
