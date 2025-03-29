package org.example.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvPlotter extends JFrame {

    public CsvPlotter(String filename) {
        int index = filename.lastIndexOf('/');
        String title = (index != -1) ? filename.substring(index + 1) : "Graph";

        setTitle("Graphic from CSV");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        XYSeries series = new XYSeries("Function from CSV");

        // Читаем CSV и добавляем точки
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace(',', '.');  // Если используется ',' вместо '.'
                String[] values = line.split(";");
                if (values.length >= 2) {  // Проверка, что строка корректная
                    double x = Double.parseDouble(values[0]);
                    double y = Double.parseDouble(values[1]);
                    series.add(x, y);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        // Создаем график
        JFreeChart chart = ChartFactory.createXYLineChart(
                title, "X", "Y", dataset
        );

        // Настройка отображения точек и линий
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesLinesVisible(0, false);  // Включаем линии
        renderer.setSeriesShapesVisible(0, true); // Включаем точки

        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);
    }
}
