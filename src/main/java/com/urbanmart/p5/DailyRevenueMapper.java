package com.urbanmart.p5;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DailyRevenueMapper
        extends Mapper<Object, Text, Text, DoubleWritable> {

    private final Text dateKey = new Text();
    private final DoubleWritable revenueValue =
            new DoubleWritable();

    @Override
    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString().trim();

        // Skip empty lines
        if (line.isEmpty()) {
            return;
        }

        // Skip CSV header
        if (line.startsWith("transactionId")) {
            return;
        }

        String[] fields = line.split(",");

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

        int quantity =
                Integer.parseInt(fields[4].trim());

        double unitPrice =
                Double.parseDouble(fields[5].trim());

        String date =
                fields[6].trim();

        // Calculate revenue
        double revenue =
                quantity * unitPrice;

        // Group by date
        dateKey.set(date);
        revenueValue.set(revenue);

        context.write(
                dateKey,
                revenueValue
        );
    }
}
