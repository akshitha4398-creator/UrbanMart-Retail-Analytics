package com.urbanmart.p2;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class BestSellerMapper
        extends Mapper<Object, Text, Text, IntWritable> {

    private final Text productKey = new Text();
    private final IntWritable quantityValue = new IntWritable();

    @Override
    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString().trim();

        // Skip empty lines
        if (line.isEmpty()) {
            return;
        }

        // Skip header
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

        String product = fields[3].trim();
        int quantity = Integer.parseInt(fields[4].trim());

        productKey.set(product);
        quantityValue.set(quantity);

        context.write(productKey, quantityValue);
    }
}
