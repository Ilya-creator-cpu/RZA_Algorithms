package protection.model.dataobjects.protection;

import lombok.Getter;
import lombok.Setter;
import protection.model.Direction.Direction;
import protection.model.common.DataAttribute;

import static protection.model.Direction.Direction.BACKWARD;
import static protection.model.Direction.Direction.FORWARD;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description
 */
public class ACD extends ACT {

    @Getter @Setter
    private DataAttribute<Direction> dirGeneral = new DataAttribute<Direction>();
    {
        dirGeneral.setName("dirGeneral");
        dirGeneral.setValue(FORWARD);
        dirGeneral.setParent(this);
        this.getChildren().add(dirGeneral);
    }

    @Getter @Setter
    private DataAttribute<Direction> dirPhsA = new DataAttribute<>();

    @Getter @Setter
    private DataAttribute<Direction> dirPhsB = new DataAttribute<>();

    @Getter @Setter
    private DataAttribute<Direction> dirPhsC = new DataAttribute<>();


}
