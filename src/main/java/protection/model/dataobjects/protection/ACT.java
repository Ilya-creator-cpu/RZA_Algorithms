package protection.model.dataobjects.protection;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DATA;
import protection.model.common.DataAttribute;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description
 */
public class ACT extends DATA {

    private boolean fail;

    @Getter @Setter
    private DataAttribute<Boolean> general = new DataAttribute<>(false);

    @Getter @Setter
    private DataAttribute<Boolean> phsA = new DataAttribute<>(false);

    @Getter @Setter
    private DataAttribute<Boolean> phsB = new DataAttribute<>(false);

    @Getter @Setter
    private DataAttribute<Boolean> phsC = new DataAttribute<>(false);


}
