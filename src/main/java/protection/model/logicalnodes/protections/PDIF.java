package protection.model.logicalnodes.protections;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.HWYE;
import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.protection.ACD;
import protection.model.dataobjects.protection.ACT;
import protection.model.logicalnodes.Setter.ING;
import protection.model.logicalnodes.commands.SPS;
import protection.model.logicalnodes.common.LN;


@Getter @Setter
public class PDIF extends LN {
    private ACD Str = new ACD(); // Срабатывание защиты
    private ACT Op = new ACT(); // Пуск защиты
    private ING OpDiTmms = new ING(); // Уставка по времени
    private double TimeDelay = 0; // Текущий отсчет времени
    private WYE DifAClc = new WYE(); //Дифференциальный ток
    private WYE RstA = new WYE();   //Ограничение по току
    private int Rst0 = 0;   //начальный тормозной ток
    private HWYE harm = new HWYE(); //Гармоники
    private SPS Blk = new SPS();    //Блокировка
    private int blkHarm = 0;    //Номер гармоники
    private double k = 0;
    private int count = 0;



    @Override
    public void process() {
        boolean phsA = false;
        boolean phsB = false;
        boolean phsC = false;


        Blk.getСtVal().setValue(false);

        if (harm.getPhsAHar().get(blkHarm).getCVal().getMag().getF().getValue()/harm.getPhsAHar().get(1).getCVal().getMag().getF().getValue()*100 > 48||
                harm.getPhsBHar().get(blkHarm).getCVal().getMag().getF().getValue()/harm.getPhsBHar().get(1).getCVal().getMag().getF().getValue()*100 > 48||
                harm.getPhsCHar().get(blkHarm).getCVal().getMag().getF().getValue()/harm.getPhsCHar().get(1).getCVal().getMag().getF().getValue()*100 > 48){

            count++;
        }else count=0;
        if (count > 5){
            Blk.getСtVal().setValue(true);
        }

        if (!Blk.getСtVal().getValue()){
            if (RstA.getPhsA().getCVal().getMag().getF().getValue() > Rst0||
                    RstA.getPhsB().getCVal().getMag().getF().getValue() >     Rst0||
                    RstA.getPhsC().getCVal().getMag().getF().getValue() >     Rst0) {
                phsA = DifAClc.getPhsA().getCVal().getMag().getF().getValue() > k * RstA.getPhsA().getCVal().getMag().getF().getValue();
                phsB = DifAClc.getPhsB().getCVal().getMag().getF().getValue() > k * RstA.getPhsB().getCVal().getMag().getF().getValue();
                phsC = DifAClc.getPhsC().getCVal().getMag().getF().getValue() > k * RstA.getPhsC().getCVal().getMag().getF().getValue();
            }
        }
        boolean general = phsA || phsB || phsC;

        Str.getGeneral().setValue(general);
        Str.getPhsA().setValue(phsA);
        Str.getPhsB().setValue(phsB);
        Str.getPhsC().setValue(phsC);

        if (general){
            TimeDelay+=1;
        }

        Op.getGeneral().setValue(TimeDelay > OpDiTmms.getSetVal().getValue());
        Op.getPhsA().setValue(TimeDelay > OpDiTmms.getSetVal().getValue());
        Op.getPhsB().setValue(TimeDelay > OpDiTmms.getSetVal().getValue());
        Op.getPhsC().setValue(TimeDelay > OpDiTmms.getSetVal().getValue());



    }


}
