package utils.filters;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.MV;
import protection.model.logicalnodes.common.LN;


@Getter @Setter
public class HFFT extends Filter {
    private int size = 80;
    private float[] bufferX = new float[size];
    private float[] bufferY = new float[size];
    private float sumX = 0;
    private float sumY = 0;
    private int count = 0;
    private double fx, fy, Fx, Fy;
    private double k = Math.sqrt(2)/size;

    private int freq;

    public HFFT(int F) {

        this.freq = 50 * F;

    }


    @Override
    public void process(MV instMag, CMV result) {

        fx = instMag.getInstMag().getF().getValue()*Math.sin(2*Math.PI*count/size);
        fy = instMag.getInstMag().getF().getValue()*Math.cos(2*Math.PI*count/size);
        sumX += fx - bufferX[count];
        sumY += fy - bufferY[count];
        bufferX[count] = (float) (instMag.getInstMag().getF().getValue()*Math.sin(2*Math.PI*count/size));
        bufferY[count] = (float) (instMag.getInstMag().getF().getValue()*Math.cos(2*Math.PI*count/size));

        Fx = k * sumX;
        Fy = k * sumY;

        result.getCVal().setOrt(Fx,Fy);
        if(++count >= size) count = 0;


    }
}
