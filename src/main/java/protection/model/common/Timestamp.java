package protection.model.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description
 */
public class Timestamp extends DATA {

    @Getter @Setter
    private long value = 0L;
}
