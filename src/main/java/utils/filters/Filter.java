package utils.filters;

import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.MV;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description
 */
public abstract class Filter {

    public abstract void process(MV instMag, CMV result);

}
