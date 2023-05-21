package protection.model.dataobjects.settings;

import lombok.Getter;
import protection.model.common.DATA;
import protection.model.dataobjects.measurements.AnalogueValue;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description
 */
public class ASG extends DATA {

    @Getter
    private final AnalogueValue setMag = new AnalogueValue();
}
