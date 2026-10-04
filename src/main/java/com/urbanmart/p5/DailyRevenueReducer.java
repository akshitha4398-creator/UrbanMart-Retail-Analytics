package com.urbanmart.p5;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DailyRevenueReducer
        extends Reducer<Text, DoubleWritable,
                         Text, DoubleWritable> {

    @Override
    public void reduce(Text key,
                       Iterable<DoubleWritable> values,
                       Context context)
            throws IOException, InterruptedException {

        double totalRevenue = 0.0;

        for (DoubleWritable value : values) {
            totalRevenue += value.get();
        }

        context.write(
                key,
                new DoubleWritable(totalRevenue)
        );
    }
}
