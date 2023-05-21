package protection.model.logicalnodes.commands;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DataAttribute;

public class SPS {

    @Getter @Setter
    private DataAttribute<Boolean> сtVal = new DataAttribute<>(true);


}
