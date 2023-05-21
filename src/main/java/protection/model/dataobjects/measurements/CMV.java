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


public class CMV extends DATA {

    @Getter @Setter
    private Vector CVal = new Vector();

    @Getter @Setter
    private Quality q = new Quality();
    @Getter @Setter
    private Timestamp t = new Timestamp();


}
