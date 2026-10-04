package com.urbanmart.p4;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class AverageTransactionDriver {

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println(
                "Usage: AverageTransactionDriver <input> <output>"
            );
            System.exit(2);
        }

        Configuration configuration =
                new Configuration();

        Job job = Job.getInstance(
                configuration,
                "UrbanMart Problem 4 - Highest Average Transaction Value"
        );

        job.setJarByClass(
                AverageTransactionDriver.class
        );

        job.setMapperClass(
                AverageTransactionMapper.class
        );

        job.setReducerClass(
                AverageTransactionReducer.class
        );

        job.setMapOutputKeyClass(
                Text.class
        );

        job.setMapOutputValueClass(
                Text.class
        );

        job.setOutputKeyClass(
                Text.class
        );

        job.setOutputValueClass(
                Text.class
        );

        // One reducer so we can find the overall highest branch
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
