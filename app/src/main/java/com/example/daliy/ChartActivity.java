package com.example.daliy;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class ChartActivity extends Activity {


    private LineChartView mchart;
    private Map<String, Integer> table = new TreeMap<>();
    private LineChartData mdata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);
        mchart = findViewById(R.id.chart);
        List<CostBean> allData = (List<CostBean>) getIntent().getSerializableExtra("cost_list");
        generateValues(allData);
        generateData();
        mchart.setLineChartData(mdata);
    }

    private void generateData() {
        List<Line> lines=new ArrayList<>();
        List<PointValue> pointValues = new ArrayList<>();
        int X = 0;
        for (Integer value : table.values()) {
            pointValues.add(new PointValue(X, value));
            X++;
        }
        Line line = new Line(pointValues);
        line.setColor(ChartUtils.COLORS[0]).setCubic(true);
        line.setShape(ValueShape.CIRCLE);
        line.setPointColor(ChartUtils.COLORS[1]);
        lines.add(line);
        mdata.getLines();
    }

    private void generateValues(List<CostBean> allData) {
        if (allData != null) {
            for (int i = 0; i < allData.size(); i++) {
                CostBean costBean = allData.get(i);
                String costDate = costBean.costDate;
                int costMoney = Integer.parseInt(costBean.costMoney);

                if (!table.containsKey(costDate)) {
                    table.put(costDate, costMoney);
                } else {
                    int originMoney = table.get(costMoney);
                    table.put(costDate, originMoney + costMoney);
                }
            }
        }
    }
}
