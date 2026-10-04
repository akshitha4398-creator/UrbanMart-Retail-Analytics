package com.urbanmart.p4;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class AverageTransactionMapper
        extends Mapper<Object, Text, Text, Text> {

    private final Text branchKey = new Text();
    private final Text transactionData = new Text();

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

        String branch = fields[1].trim();

        int quantity = Integer.parseInt(fields[4].trim());
        double unitPrice = Double.parseDouble(fields[5].trim());

        // Transaction value
        double transactionValue = quantity * unitPrice;

        branchKey.set(branch);

        // Send transaction value and count
        transactionData.set(
                transactionValue + ",1"
        );

        context.write(
                branchKey,
                transactionData
        );
    }
}
