import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.IntegerDocument;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LineChart_AWT extends ApplicationFrame {
    static ArrayList hs, ts;

    public LineChart_AWT( String applicationTitle , String chartTitle ) {
        super(applicationTitle);
        hs = new ArrayList<Integer>();
        ts = new ArrayList<Integer>();
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Время","Высота",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }

    private DefaultCategoryDataset createDataset( ) {
        try {
            Scanner sc = new Scanner(new File("hs.txt"));
            while(sc.hasNextLine()) {
               hs.add(Integer.valueOf(sc.nextLine()));
                //System.out.print(sc.nextLine());
            }
        }
        catch (IOException e){
            System.out.println("Данные отсутствуют! Сначала запустите анимацию падения мяча.");
        }

        try {
            Scanner sc = new Scanner(new File("ts.txt"));
            while(sc.hasNextLine()) {
                ts.add(0, Integer.valueOf(sc.nextLine()));
            }
        }
        catch (IOException e){
            System.out.println("Данные отсутствуют! Сначала запустите анимацию падения мяча.");
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

        for (int i = 0; i < ts.size(); i++){
            dataset.addValue((Integer)hs.get(i), "высота", ts.get(i).toString());
        }
        return dataset;
    }

    public static void main( String[ ] args ) {
        LineChart_AWT chart = new LineChart_AWT(
                "График" ,
                "Падение мяча");

        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }
}