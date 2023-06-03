package utils.filters;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.MV;
import protection.model.logicalnodes.common.LN;


@Getter @Setter
public class HFFT extends Filter {
    private int size = 20;
    private double[] bufferX = new double[size];
    private double[] bufferY = new double[size];
    private double sumX = 0;
    private double sumY = 0;
    private int count = 0;
    private double fx, fy, Fx, Fy;
    private double k = Math.sqrt(2)/size;

    private int freq;

    public HFFT(int F) {

        this.freq = 50 * F;

    }


    @Override
    public void process(MV instMag, CMV result) {

        fx += k *instMag.getInstMag().getF().getValue()*Math.sin(2*Math.PI*freq*0.02*count/size) - bufferX[count];
        fy += k * instMag.getInstMag().getF().getValue()*Math.cos(2*Math.PI*freq*0.02*count/size) - bufferY[count];
        bufferX[count] = (k * instMag.getInstMag().getF().getValue()*Math.sin(2*Math.PI*freq*0.02*count/size));
        bufferY[count] = (k * instMag.getInstMag().getF().getValue()*Math.cos(2*Math.PI*freq*0.02*count/size));


        result.getCVal().ToVector(fx,fy);
        if(++count >= size) count = 0;


    }
}
