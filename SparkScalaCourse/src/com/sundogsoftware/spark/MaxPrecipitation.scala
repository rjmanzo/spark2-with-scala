package com.sundogsoftware.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._
import scala.math.max

/** Find the maximum temperature by weather station for a year */
object MaxPrecipitation {
  
  def parseLine(line:String)= {
    val fields = line.split(",")
    val stationID = fields(0)
    val entryType = fields(2)
    val preps = fields(3).toFloat
    (stationID, entryType, preps)
  }
    /** Our main function where the action happens */
  def main(args: Array[String]) {
   
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)
    
    // Create a SparkContext using every core of the local machine
    val sc = new SparkContext("local[*]", "MaxPrecipitations")
    
    val lines = sc.textFile("../datasets/1800.csv")
    val parsedLines = lines.map(parseLine)
    val maxPreps = parsedLines.filter(x => x._2 == "PRCP")
    val stationPreps = maxPreps.map(x => (x._1, x._3.toFloat))
    val maxPrepsByStation = stationPreps.reduceByKey( (x,y) => max(x,y))
    val results = maxPrepsByStation.collect()
    
    for (result <- results.sorted) {
       val station = result._1
       val prep = result._2
       //val formattedTemp = f"$temp%.2f F"
       println(s"$station max precipitation: $prep") 
    }
      
  }
}