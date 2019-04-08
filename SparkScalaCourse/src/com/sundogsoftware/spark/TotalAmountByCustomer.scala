package com.sundogsoftware.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._

object TotalAmountByCustomer {
  
  def parseLine(line:String)= {
    val fields = line.split(",")
    val customerID = fields(0).toInt
    val amount = fields(2).toFloat    
    (customerID, amount)
  }
  
  /** Our main function where the action happens */
  def main(args: Array[String]) {
    
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)
        
    // Create a SparkContext using every core of the local machine, named TotalAmountByCustomer
    val sc = new SparkContext("local[*]", "TotalAmountByCustomer")
   
    // Load up each line of the ratings data into an RDD
    val lines = sc.textFile("../datasets/customer-orders.csv") 
    
    // Convert to (stationID, entryType, temperature) tuples
    val parsedLines = lines.map(parseLine) 
     
    // Reduce by customerID sumarizing the amount
    val amountbyCustomer = parsedLines.reduceByKey( (x,y) => x + y)
    
    // Collect, format, and print the results
    val results = amountbyCustomer.collect()
    
   
    for (result <- results.sorted) {
       val customerID = result._1
       val amount = result._2       
       println(s"The CustomerID $customerID has an Amount of: $amount") 
    }
    
    /*MAPREDUCE SOLUTION NOT USING THE sorted function 
    
    // Flip (word, count) tuples to (count, word) and then sort by key (the counts)
    val wordCountsSorted = wordCounts.map( x => (x._2, x._1) ).sortByKey()
    
    // Print the results, flipping the (count, word) results to word: count as we go.
    for (result <- wordCountsSorted) {
      val count = result._1
      val word = result._2
      println(s"$word: $count")
    }*/   
    

    
  }
  
}