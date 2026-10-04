package com.urbanmart.p4;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AverageTransactionReducer
        extends Reducer<Text, Text, Text, Text> {

    private String highestBranch = "";
    private double highestAverage = 0.0;

    @Override
    public void reduce(Text key,
                       Iterable<Text> values,
                       Context context)
            throws IOException, InterruptedException {

        double totalValue = 0.0;
        int transactionCount = 0;

        for (Text value : values) {

            String[] parts = value.toString().split(",");

            double transactionValue =
                    Double.parseDouble(parts[0]);

            int count =
                    Integer.parseInt(parts[1]);

            totalValue += transactionValue;
            transactionCount += count;
        }

        double averageValue =
                totalValue / transactionCount;

        // Keep track of the branch with the highest average
        if (averageValue > highestAverage) {
            highestAverage = averageValue;
            highestBranch = key.toString();
        }
    }

    @Override
    protected void cleanup(Context context)
            throws IOException, InterruptedException {

        context.write(
                new Text(highestBranch),
                new Text(
                    String.valueOf(highestAverage)
                )
        );
    }
}
