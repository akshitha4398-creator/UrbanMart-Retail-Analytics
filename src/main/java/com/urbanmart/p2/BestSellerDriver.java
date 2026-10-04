package com.urbanmart.p2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class BestSellerDriver {

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println(
                "Usage: BestSellerDriver <input> <output>"
            );
            System.exit(2);
        }

        Configuration configuration = new Configuration();

        Job job = Job.getInstance(
                configuration,
                "UrbanMart Problem 2 - Best Selling Product"
        );

        job.setJarByClass(BestSellerDriver.class);

        job.setMapperClass(BestSellerMapper.class);
        job.setReducerClass(BestSellerReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // One reducer gives one overall best-selling product
        job.setNumReduceTasks(1);

        FileInputFormat.addInputPath(
                job,
                new Path(args[0])
        );

        FileOutputFormat.setOutputPath(
                job,
                new Path(args[1])
        );

        boolean success = job.waitForCompletion(true);

        System.exit(success ? 0 : 1);
    }
}
