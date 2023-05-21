package protection.model.dataobjects.measurements;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DATA;
import protection.model.common.Quality;
import protection.model.common.Timestamp;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description
 */
public class MV extends DATA {

    @Getter @Setter
    private AnalogueValue instMag = new AnalogueValue();

    @Getter @Setter
    private AnalogueValue frequency = new AnalogueValue();


    @Getter @Setter
    private Quality q = new Quality();
    @Getter @Setter
    private Timestamp t = new Timestamp();
}
