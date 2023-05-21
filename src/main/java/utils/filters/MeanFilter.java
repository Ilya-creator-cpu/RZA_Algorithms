package utils.filters;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.MV;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description
 */
public class MeanFilter extends Filter {

    public static int bSize = 80;

    private final double[] buffer = new double[bSize];

    private int bCount = 0;

    private double integral = 0d;

    @Override
    public void process(MV instMag, CMV result) {

        double value = Math.abs(instMag.getInstMag().getF().getValue());

        integral += value - buffer[bCount];
        buffer[bCount] = value;

        bCount++;
        if(bCount >= bSize) bCount = 0;

        result.getCVal().getMag().getF().setValue(1.1104 * integral/bSize);
        System.out.println();
    }
}
