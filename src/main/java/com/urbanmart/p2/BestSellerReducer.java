package com.urbanmart.p2;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class BestSellerReducer
        extends Reducer<Text, IntWritable, Text, IntWritable> {

    private String bestProduct = "";
    private int highestQuantity = 0;

    @Override
    public void reduce(Text key,
                       Iterable<IntWritable> values,
                       Context context)
            throws IOException, InterruptedException {

        int totalQuantity = 0;

        for (IntWritable value : values) {
            totalQuantity += value.get();
        }

        if (totalQuantity > highestQuantity) {
            highestQuantity = totalQuantity;
            bestProduct = key.toString();
        }
    }

    @Override
    protected void cleanup(Context context)
            throws IOException, InterruptedException {

        context.write(
                new Text(bestProduct),
                new IntWritable(highestQuantity)
        );
    }
}
