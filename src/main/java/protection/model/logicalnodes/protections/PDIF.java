package protection.model.logicalnodes.protections;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DataAttribute;
import protection.model.dataobjects.measurements.HWYE;
import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.protection.ACT;
import protection.model.dataobjects.settings.ASG;
import protection.model.logicalnodes.Setter.ING;
import protection.model.logicalnodes.common.LN;
import protection.model.logicalnodes.measurements.CSD;

import java.util.ArrayList;


@Getter @Setter
public class PDIF extends LN {
    private ArrayList<HWYE> inputHarm = new ArrayList<>();  //Гармонники посчитанные в HWYE

    private WYE DifAClc = new WYE();                        //диф ток посчитанный в rmxu
    private WYE TripPoint = new WYE();

    private ACT Op = new ACT();                             //срабатывание


    private ASG StrVal = new ASG();                         //Уставка по диф.току
    private ASG StrHarm = new ASG();                        //Уставка по гармонике
    private ING MinOpTmms = new ING();                      //Уставка по времени

    private CSD TmASt = new CSD();                          //Тормозная характеристика

    private double CntA = 0;
    private double CntB = 0;
    private double CntC = 0;

    private DataAttribute<Boolean> Blk = new DataAttribute<>(false);

    @Override
    public void process() {




        boolean phsA = DifAClc.getPhsA().getCVal().getMag().getF().getValue() > TripPoint.getPhsA().getCVal().getMag().getF().getValue();
        boolean phsB = DifAClc.getPhsB().getCVal().getMag().getF().getValue() > TripPoint.getPhsB().getCVal().getMag().getF().getValue();
        boolean phsC = DifAClc.getPhsC().getCVal().getMag().getF().getValue() > TripPoint.getPhsC().getCVal().getMag().getF().getValue();


//        System.out.println(DifAClc.getPhsA().getCVal().getMag().getF().getValue() + " : " + TripPoint.getPhsA().getCVal().getMag().getF().getValue());
//        System.out.println(DifAClc.getPhsB().getCVal().getMag().getF().getValue() + " : " + TripPoint.getPhsB().getCVal().getMag().getF().getValue());
//        System.out.println(DifAClc.getPhsC().getCVal().getMag().getF().getValue() + " : " + TripPoint.getPhsC().getCVal().getMag().getF().getValue());

        if (phsA) {
            CntA += 1;
            //System.out.println(DifAClc.getPhsA().getCVal().getMag().getF().getValue() + " : " + TripPoint.getPhsA().getCVal().getMag().getF().getValue());
        }
        else{
            CntA =0;
        }
        if (phsB) {
            CntB += 1;
            //System.out.println(DifAClc.getPhsB().getCVal().getMag().getF().getValue() + " : " + TripPoint.getPhsB().getCVal().getMag().getF().getValue());
        }
        else{
            CntB =0;
        }
        if (phsC) {
            CntC += 1;
            //System.out.println(DifAClc.getPhsC().getCVal().getMag().getF().getValue() + " : " + TripPoint.getPhsC().getCVal().getMag().getF().getValue());
        }
        else{
            CntC =0;
        }

        if(Blk.getValue()) {
            CntA = 0;
            CntB = 0;
            CntC = 0;
        }
        Op.getGeneral().setValue(CntA > MinOpTmms.getDelay()
                || CntB > MinOpTmms.getDelay()
                || CntC > MinOpTmms.getDelay() );
        Op.getPhsA().setValue(CntA > MinOpTmms.getDelay());
        Op.getPhsB().setValue(CntB > MinOpTmms.getDelay());
        Op.getPhsC().setValue(CntC > MinOpTmms.getDelay());

    }
}
