package protection.model.logicalnodes.measurements;


import lombok.Data;
import protection.model.common.DataAttribute;
import protection.model.dataobjects.measurements.Point;


import java.util.ArrayList;

@Data
public class CSD {

    private ArrayList<Point> crvPvs = new ArrayList<>();

    private DataAttribute<Integer> numPts = new DataAttribute<>(0);

}
