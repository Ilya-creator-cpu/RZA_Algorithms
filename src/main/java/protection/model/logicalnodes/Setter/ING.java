package protection.model.logicalnodes.Setter;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DataAttribute;

@Getter @Setter
public class ING  {

    private DataAttribute<Integer> setVal = new DataAttribute<Integer>(0);
    @Getter @Setter
    public double delay;

}
