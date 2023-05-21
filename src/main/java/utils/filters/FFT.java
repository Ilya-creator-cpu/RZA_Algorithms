package utils.filters;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.MV;

@Getter @Setter
public class FFT extends Filter{
    private int size = 80;
    private float[] bufferX = new float[size];
    private float[] bufferY = new float[size];
    private float sumX = 0;
    private float sumY = 0;
    private int count = 0;
    private double fx, fy, Fx, Fy;
    private double k = Math.sqrt(2)/size;






    @Override
    public void process(MV instMag, CMV result) {
        fx = instMag.getInstMag().getF().getValue()*Math.sin(2*Math.PI*count/size);
        fy = instMag.getInstMag().getF().getValue()*Math.cos(2*Math.PI*count/size);
        sumX += fx - bufferX[count];
        sumY += fy - bufferY[count];
        bufferX[count] = (float)(instMag.getInstMag().getF().getValue()*Math.sin(2*Math.PI*count/size));
        bufferY[count] = (float)(instMag.getInstMag().getF().getValue()*Math.cos(2*Math.PI*count/size));

        Fx = k * sumX;
        Fy = k * sumY;

        result.getCVal().getMag().getF().setValue(Math.sqrt(Math.pow(Fx,2) + Math.pow(Fy,2)));
        if(++count >= size) count = 0;



//        System.out.println(result.getCVal().getMag().getF().getValue() + " " + (Math.pow(valueSin,2) + Math.pow(valueCos,2)));
    }
}
