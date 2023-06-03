package utils.filters;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import protection.model.common.DATA;
import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.MV;
import protection.model.dataobjects.measurements.SEQ;



@Getter @Setter
public class FrequencyFurier extends DATA {
    private SEQ SeqA = new SEQ();
    private int size = 20;
    private double[] bufferX = new double[4000];
    private double[] bufferY = new double[4000];
    private double sumX = 0;
    private double sumY = 0;
    private int count = 0;
    private double fx, fy, Fx, Fy;
    private double k = Math.sqrt(2)/size;


    private double frequency;

    private int new_size;


 //   private double K = Math.exp(-1.5);



    public FrequencyFurier(double frequency) {
        this.frequency = frequency;


    }


    public void process(MV mv, CMV cmv) {


    //    frequency  = K*frequency + (1-K)*bufferFrequency;

        new_size = (int) (1/(Math.round(frequency)*0.00025));
        size = new_size;
        k = Math.sqrt(2)/new_size;


        fx += k *mv.getInstMag().getF().getValue()*Math.sin(2*Math.PI*count/size) - bufferX[count];
        fy += k * mv.getInstMag().getF().getValue()*Math.cos(2*Math.PI*count/size) - bufferY[count];
        bufferX[count] = (k * mv.getInstMag().getF().getValue()*Math.sin(2*Math.PI*count/size));
        bufferY[count] = (k * mv.getInstMag().getF().getValue()*Math.cos(2*Math.PI*count/size));


        cmv.getCVal().ToVector(fx,fy);
        if(++count >= size) count = 0;

    }
}
