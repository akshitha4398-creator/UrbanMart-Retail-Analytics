package com.urbanmart.p5;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DailyRevenueDriver {

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println(
                "Usage: DailyRevenueDriver <input> <output>"
            );
            System.exit(2);
        }

        Configuration configuration =
                new Configuration();

        Job job = Job.getInstance(
                configuration,
                "UrbanMart Problem 5 - Daily Revenue Trend"
        );

        job.setJarByClass(
                DailyRevenueDriver.class
        );

        job.setMapperClass(
                DailyRevenueMapper.class
        );

        job.setReducerClass(
                DailyRevenueReducer.class
        );

        job.setMapOutputKeyClass(
                Text.class
        );

        job.setMapOutputValueClass(
                DoubleWritable.class
        );

        job.setOutputKeyClass(
                Text.class
        );

        job.setOutputValueClass(
                DoubleWritable.class
        );

        // One reducer
        job.setNumReduceTasks(1);

        FileInputFormat.addInputPath(
                job,
                new Path(args[0])
        );

        FileOutputFormat.setOutputPath(
                job,
                new Path(args[1])
        );

        boolean success =
                job.waitForCompletion(true);

        System.exit(success ? 0 : 1);
    }
}
