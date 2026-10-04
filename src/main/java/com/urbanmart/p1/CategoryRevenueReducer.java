package com.urbanmart.p1;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Reducer;

import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class CategoryRevenueReducer
        extends Reducer<Text, DoubleWritable,
                         Text, DoubleWritable> {

    private MultipleOutputs<Text, DoubleWritable> multipleOutputs;

    @Override
    protected void setup(Context context)
            throws IOException, InterruptedException {

        multipleOutputs =
                new MultipleOutputs<>(context);
    }

    @Override
    public void reduce(
            Text key,
            Iterable<DoubleWritable> values,
            Context context)
            throws IOException, InterruptedException {

        double totalRevenue = 0.0;

        // Add all revenue values
        for (DoubleWritable value : values) {

            totalRevenue += value.get();
        }

        // key = Bangalore|Electronics
        String[] parts =
                key.toString().split("\\|", 2);

        String branch = parts[0];
        String category = parts[1];

        /*
         * Write separate output for every branch.
         *
         * Example:
         * branch/Bangalore/part-r-00000
         */

        multipleOutputs.write(
                "branch",
                new Text(category),
                new DoubleWritable(totalRevenue),
                branch + "/part"
        );
    }

    @Override
    protected void cleanup(Context context)
            throws IOException, InterruptedException {

        multipleOutputs.close();
    }
}
