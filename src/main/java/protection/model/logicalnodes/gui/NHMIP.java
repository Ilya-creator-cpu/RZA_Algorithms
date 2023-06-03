package protection.model.logicalnodes.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.Series;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import protection.model.logicalnodes.common.LN;
import protection.model.logicalnodes.gui.other.NHMIPoint;
import protection.model.logicalnodes.gui.other.NHMISignal;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Александр Холодов
 * @created 01.2021
 * @project RPA_MPEI
 * @description Узел для построения графиков (XY)
 */
public class NHMIP extends LN {

	private final HashMap<NHMISignal, XYSeries> datasets = new HashMap<>();
	private final XYSeriesCollection dataset = new XYSeriesCollection();
	private final JFrame frame = new JFrame("Сигналы");

	private int notifyCount = 200, updatePoint = 100; // счетчик и период обновления графиков
	private int passCount = 0; // Пропуск переходных режимов

	private NumberAxis xAxis, yAxis;
	private double currentRange = 10, maxRange = 1000;

	public NHMIP(){
		JFreeChart jfreechart = ChartFactory.createScatterPlot(
				"title", "R, Ом", "X, Ом", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		XYPlot plot = jfreechart.getXYPlot();
		plot.setBackgroundPaint(Color.DARK_GRAY);
		NumberAxis rangeAxis = new NumberAxis("X, ом");
		rangeAxis.setAutoRangeIncludesZero(false);
		plot.setRangeCrosshairVisible(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairStroke(new BasicStroke(3.0f));
		plot.setDomainCrosshairStroke(new BasicStroke(3.0f));

		xAxis = (NumberAxis) plot.getRangeAxis();
		yAxis = (NumberAxis) plot.getDomainAxis();

		JFreeChart chart = new JFreeChart("Сигналы", plot);
		chart.setBorderPaint(Color.black);
		chart.setBorderVisible(true);
		chart.setBackgroundPaint(Color.white);
		chart.setAntiAlias(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new ChartPanel(chart));
		frame.setSize(1024,768);
	}

	public void process(){
		if(!frame.isVisible()) frame.setVisible(true);

		if(passCount++ > 100)
			datasets.forEach((a, c) -> c.add((Number) a.getDataX().getValue(), (Number) a.getDataY().getValue(), false));

		/* Период обновления */
		if(notifyCount++ > updatePoint) {
			notifyCount = 0;

			datasets.values().forEach(xySeries -> {
				double value = xySeries.getMaxX(); if(value > currentRange) currentRange = value;
				value =  xySeries.getMaxY(); if(value > currentRange) currentRange = value;
			});
			setCurrentRange(currentRange);
			datasets.values().forEach(Series::fireSeriesChanged);
		}
	}


	/**
	 * Добавить группу сигналов
	 * @param signals - группа сигналов
	 */
	public void addSignals(NHMISignal... signals) {
		for(NHMISignal signal: signals){
			if(!(signal.getDataX().getValue() instanceof Number) || !(signal.getDataY().getValue() instanceof Number)){
				System.err.println("Сигналы должны быть числовые"); break;
			}
			XYSeries series = new XYSeries(signal.getName());
			dataset.addSeries(series);
			datasets.put(signal, series);
		}

		process();
	}


	/** Нарисовать характеристику срабатывания */
	public void drawCharacteristic(String name, List<NHMIPoint<Double, Double>> points) {
		XYSeries series = new XYSeries(name);
		dataset.addSeries(series);
		points.forEach(p -> { if(!p.getValue1().isNaN() && !p.getValue2().isNaN()) series.add(p.getValue1(), p.getValue2(), false); });
		series.fireSeriesChanged();
		process();
		Double maxX = points.stream().max((o1, o2) -> (int) (o1.getValue1() - o2.getValue1())).get().getValue1();
		Double maxy = points.stream().max((o1, o2) -> (int) (o1.getValue2() - o2.getValue2())).get().getValue2();
		double max = Math.max(maxX, maxy);
		if(max > currentRange) { currentRange = max; setCurrentRange(max);  }
	}

	/** Задать диапазон осей */
	private void setCurrentRange(double range){
		if(range > maxRange) range = maxRange;
		else range = Math.round(range * 1.2);
		xAxis.setRange(-range, range);
		yAxis.setRange(-range, range);
	}

	/** Максимальный диапазон осей */
	public void setMaxRange(double maxRange) {
		this.maxRange = maxRange;
	}

	public static List<NHMIPoint<Double, Double>> getNhmiPoints(double x1,double y1, double x2, double y2, double x3, double y3
	, double x4, double y4) {
		List<NHMIPoint<Double, Double>> pointsList = new ArrayList<>();



		for (double x = x1; x >= x2; x -= 0.1) {
			pointsList.add(new NHMIPoint<>(x, y1));
		}
		double y = y2;
		double x = x2;
			while (x <= x3) {
					pointsList.add(new NHMIPoint<>(x, y));
					y = y - (y2-y3)/(Math.abs(x3-x2))/10;
					x = x + 0.1;
			}

			y = y3;
			x = x3;

			while (x <=x4) {
				pointsList.add(new NHMIPoint<>(x,y));
				y = y + (y4 - y3)/(Math.abs(x4 -x3))/10;
				x = x + 0.1;
			}
			x= x4;
			y = y4;

			while (y <= y1) {
				pointsList.add(new NHMIPoint<>(x,y));
				y = y + (y1 - y4)/(Math.abs(x1 -x4))/10;
				x = x + 0.1;
			}

//
//		for (double x = x2; x <= x3; x+=0.1 ) {
//			for (double y = y2; y >= y3; y-= 0.1) {
//
//				pointsList.add(new NHMIPoint<>(x2 +x,y2 - y));
//				System.out.println(x + " : " + y);
//			}


	//	}
		return pointsList;
	}

	public static List<NHMIPoint<Double,Double>> getUnDirectPoints(double x1,double y1, double x2, double y2, double x3, double y3, double x4, double y4) {

		List<NHMIPoint<Double, Double>> pointsList = new ArrayList<>();
		for (double x = x1; x >= x2; x -= 0.1) {
			pointsList.add(new NHMIPoint<>(x, y1));
		}
		double x = x2;
		double y = y2;
		while (x <= x3) {
			pointsList.add(new NHMIPoint<>(x, y));
			y = y - (y2-y3)/(Math.abs(x3-x2))/10;
			x = x + 0.1;
		}

		y = y3;

		for ( x = x3; x <= x4; x += 0.1) {
			pointsList.add(new NHMIPoint<>(x, y));
		}
		x= x4;

		while (y <= y1) {
			pointsList.add(new NHMIPoint<>(x,y));
			y = y + (y1 - y3)/(Math.abs(x1 -x4))/10;
			x = x - 0.1;
		}


		return pointsList;

	}

}
