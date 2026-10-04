package com.urbanmart.p3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WeekdayWeekendDriver {

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println(
                "Usage: WeekdayWeekendDriver <input> <output>"
            );
            System.exit(2);
        }

        Configuration configuration = new Configuration();

        Job job = Job.getInstance(
                configuration,
                "UrbanMart Problem 3 - Weekday vs Weekend Revenue"
        );

        job.setJarByClass(WeekdayWeekendDriver.class);

        job.setMapperClass(WeekdayWeekendMapper.class);
        job.setReducerClass(WeekdayWeekendReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DoubleWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

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

        boolean success = job.waitForCompletion(true);

        System.exit(success ? 0 : 1);
    }
}
