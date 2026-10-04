package com.urbanmart.p1;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Mapper;

public class CategoryRevenueMapper
        extends Mapper<Object, Text, Text, DoubleWritable> {

    private final Text outputKey = new Text();
    private final DoubleWritable outputValue =
            new DoubleWritable();

    @Override
    public void map(
            Object key,
            Text value,
            Context context)
            throws IOException, InterruptedException {

        String line = value.toString().trim();

        // Skip empty line
        if (line.isEmpty()) {
            return;
        }

        // Skip CSV header
        if (line.startsWith("transactionId")) {
            return;
        }

        String[] fields = line.split(",");

        // Expected:
        // 0 transactionId
        // 1 branch
        // 2 category
        // 3 product
        // 4 quantity
        // 5 unitPrice
        // 6 date

        if (fields.length != 7) {
            return;
        }

        String branch = fields[1].trim();
        String category = fields[2].trim();

        double quantity =
                Double.parseDouble(fields[4].trim());

        double unitPrice =
                Double.parseDouble(fields[5].trim());

        // Revenue = quantity * unitPrice
        double revenue = quantity * unitPrice;

        // Example:
        // Bangalore|Electronics
        outputKey.set(branch + "|" + category);

        outputValue.set(revenue);

        context.write(outputKey, outputValue);
    }
}
