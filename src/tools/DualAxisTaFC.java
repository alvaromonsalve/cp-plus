/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tools;

import java.util.Date;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 *
 * @author Administrador
 */
public class DualAxisTaFC {
    
    
     final TimeSeries series = new TimeSeries("Per Minute Data", Minute.class);
     final Hour hour = new Hour(new Date());

     
     
     
    
    private final Object categoriaTa[]={new String[] {"12:30","80","85","100"},
        new String[] {"12:35","70","75","110"},
        new String[] {"12:41","80","85","100"},
        new String[] {"12:45","90","95","105"},
        new String[] {"12:50","96","100","121"},
        new String[] {"12:57","100","105","116"}};
    
   
    private final Object categoriaFC[]={new String[] {"12:30","90"},
        new String[] {"12:35","70"},
        new String[] {"12:41","100"},
        new String[] {"12:45","90"},
        new String[] {"12:50","96"},
        new String[] {"12:57","100"}};
    
    public DualAxisTaFC(){

    }
    
    public TimeSeriesCollection createDatasetTA(){
        series.add(new Minute(30, hour), 70);
        series.add(new Minute(35, hour), 80);
        series.add(new Minute(40, hour), 90);
        series.add(new Minute(45, hour), 80);
        series.add(new Minute(50, hour), 96);
        series.add(new Minute(56, hour), 69);
        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        dataset.setDomainIsPointsInTime(true);
        
        
        
  
//        final String seriesTAS="TaS";
//        final String seriesTAD="TaD";
//        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        for (Object categoriaTa1 : categoriaTa) {
//            String[] subString = (String[]) categoriaTa1;
//            dataset.addValue(Integer.parseInt(subString[1]), seriesTAD, subString[0].toString());
//            dataset.addValue(Integer.parseInt(subString[2]), seriesTAS, subString[0].toString());
//        }
        
        return dataset;
    }
    
    public CategoryDataset createDatasetFC(){
        final String seriesFC="FC";
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Object categoriaTa1 : categoriaFC) {
            String[] subString = (String[]) categoriaTa1;
            dataset.addValue(Integer.parseInt(subString[1]), seriesFC, subString[0].toString());
        }
        return dataset;
    }
    
}
