package com.urbanmart.p3;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WeekdayWeekendMapper
        extends Mapper<Object, Text, Text, DoubleWritable> {

    private final Text outputKey = new Text();
    private final DoubleWritable outputValue =
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

        String branch = fields[1].trim();

        int quantity = Integer.parseInt(fields[4].trim());
        double unitPrice = Double.parseDouble(fields[5].trim());

        String dateString = fields[6].trim();

        // Convert date to LocalDate
        LocalDate date = LocalDate.parse(dateString);

        DayOfWeek day = date.getDayOfWeek();

        String dayType;

        if (day == DayOfWeek.SATURDAY ||
            day == DayOfWeek.SUNDAY) {

            dayType = "Weekend";

        } else {

            dayType = "Weekday";
        }

        // Calculate revenue
        double revenue = quantity * unitPrice;

        // Key = Branch + Weekday/Weekend
        outputKey.set(branch + "|" + dayType);

        outputValue.set(revenue);

        context.write(outputKey, outputValue);
    }
}
