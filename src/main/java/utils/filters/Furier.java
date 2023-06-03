package utils.filters;

import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.MV;
import protection.model.dataobjects.measurements.SEQ;

public class Furier extends Filter {


    private int size = 80;
    private double[] bufferX = new double[size];
    private double[] bufferY = new double[size];
    private double sumX = 0;
    private double sumY = 0;
    private int count = 0;
    private double fx, fy;
    private double k = Math.sqrt(2)/size;

    @Override
    public void process(MV mv, CMV cmv) {

        fx += k *mv.getInstMag().getF().getValue()*Math.sin(2*Math.PI*count/size) - bufferX[count];
        fy += k * mv.getInstMag().getF().getValue()*Math.cos(2*Math.PI*count/size) - bufferY[count];
        bufferX[count] = (k * mv.getInstMag().getF().getValue()*Math.sin(2*Math.PI*count/size));
        bufferY[count] = (k * mv.getInstMag().getF().getValue()*Math.cos(2*Math.PI*count/size));


        cmv.getCVal().setOrt(fx,fy);
        if(++count >= size) count = 0;
    }


}
