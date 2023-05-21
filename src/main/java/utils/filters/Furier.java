package utils.filters;

import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.MV;
import protection.model.dataobjects.measurements.SEQ;

public class Furier extends Filter {

    private SEQ SeqA = new SEQ();
    private int size = 80;
    private double[] bufferX = new double[size];
    private double[] bufferY = new double[size];
    private double sumX = 0;
    private double sumY = 0;
    private int count = 0;
    private double fx, fy, Fx, Fy;
    private double k = Math.sqrt(2)/size;

    @Override
    public void process(MV mv, CMV cmv) {

        fx = mv.getInstMag().getF().getValue()*Math.sin(2*Math.PI*count/size);
        fy = mv.getInstMag().getF().getValue()*Math.cos(2*Math.PI*count/size);
        sumX += fx - bufferX[count];
        sumY += fy - bufferY[count];
        bufferX[count] = (mv.getInstMag().getF().getValue()*Math.sin(2*Math.PI*count/size));
        bufferY[count] = (mv.getInstMag().getF().getValue()*Math.cos(2*Math.PI*count/size));

        Fx = k * sumX;
        Fy = k * sumY;

        cmv.getCVal().setOrt(Fx,Fy);
        if(++count >= size) count = 0;
    }


}
