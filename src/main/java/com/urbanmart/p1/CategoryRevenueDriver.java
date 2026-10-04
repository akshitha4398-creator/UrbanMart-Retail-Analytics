package com.urbanmart.p1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class CategoryRevenueDriver {

    public static void main(String[] args)
            throws Exception {

        if (args.length != 2) {

            System.err.println(
                "Usage: CategoryRevenueDriver <input> <output>"
            );

            System.exit(2);
        }

        Configuration configuration =
                new Configuration();

        Job job = Job.getInstance(
                configuration,
                "UrbanMart Problem 1 - Category Revenue"
        );

        // Set Driver class
        job.setJarByClass(
                CategoryRevenueDriver.class
        );

        // Set Mapper
        job.setMapperClass(
                CategoryRevenueMapper.class
        );

        // Set Reducer
        job.setReducerClass(
                CategoryRevenueReducer.class
        );

        // Mapper output types
        job.setMapOutputKeyClass(
                Text.class
        );

        job.setMapOutputValueClass(
                DoubleWritable.class
        );

        // Reducer output types
        job.setOutputKeyClass(
                Text.class
        );

        job.setOutputValueClass(
                DoubleWritable.class
        );

        /*
         * One reducer is used so that the branch output
         * is easy to demonstrate in the college lab.
         */
        job.setNumReduceTasks(1);

        /*
         * Register MultipleOutputs.
         */
        MultipleOutputs.addNamedOutput(
                job,
                "branch",
                TextOutputFormat.class,
                Text.class,
                DoubleWritable.class
        );

        // HDFS input path
        FileInputFormat.addInputPath(
                job,
                new Path(args[0])
        );

        // HDFS output path
        FileOutputFormat.setOutputPath(
                job,
                new Path(args[1])
        );

        // Start MapReduce job
        boolean success =
                job.waitForCompletion(true);

        System.exit(success ? 0 : 1);
    }
}
