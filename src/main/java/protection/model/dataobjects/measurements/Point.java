package protection.model.dataobjects.measurements;


import lombok.Data;
import protection.model.common.DataAttribute;

@Data
public class Point {
    private DataAttribute<Double> xVal = new DataAttribute<>((double)0);
    private DataAttribute<Double> yVal = new DataAttribute<>((double) 0);

    public Point(Double x, Double y){
        xVal.setValue(x);
        yVal.setValue(y);
    }
}
