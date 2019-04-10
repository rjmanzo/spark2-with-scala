package com.sundogsoftware.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.log4j._
import scala.io.Source
import java.nio.charset.CodingErrorAction
import scala.io.Codec
import org.apache.spark.sql.functions._

/** Find the movies with the most ratings. */
object PopularMovies {
  
 /** Load up a Map of movie IDs to movie names. */
  def loadMovieNames() : Map[Int, String] = {
    
    // Handle character encoding issues:
    implicit val codec = Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.REPLACE)
    codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

    // Create a Map of Ints to Strings, and populate it from u.item.
    var movieNames:Map[Int, String] = Map()
    
     val lines = Source.fromFile("../datasets/ml-100k/u.item").getLines()
     for (line <- lines) {
       var fields = line.split('|')
       if (fields.length > 1) {
        movieNames += (fields(0).toInt -> fields(1))
       }
     }
    
     return movieNames
  }
  
  //return MoviesID as key/value pair
  def parser (line : String)={
    val fields = line.split("\t")
    val movieID = fields(1).toInt
    (movieID,1)
  }
 
  /** Our main function where the action happens */
  def main(args: Array[String]) {
   
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)
    
     // Create a SparkContext using every core of the local machine
    val sc = new SparkContext("local[*]", "PopularMovies")   
    
    // Read in each rating line
    val lines = sc.textFile("../datasets/ml-100k/u.data")
    
    // Map to (movieID, 1) tuples. Only care about the Movie that was most rated, so dont care about the Rating itself only how many times is appear.
    //val movies = lines.map(x => (x.split("\t")(1).toInt, 1))
    val movies = lines.map(parser)
    
    // Count up all the 1's for each movie
    val movieCounts = movies.reduceByKey( (x, y) => x + y )    

    // Flip (movieID, count) to (count, movieID)
    val flipped = movieCounts.map( x => (x._2, x._1) )
    
    // Sort ASC
    val sortedMovies = flipped.sortByKey(true)

    
    // Load up the movie ID -> name map
    val names = loadMovieNames()
    //need to convert general map to RDD map using broadcast 
    var nameDict = sc.broadcast(names)
    
    val sortedMoviesbyName = sortedMovies.map(x => (nameDict.value(x._2), x._1) ) 
    
    // Collect and print results
    val results = sortedMoviesbyName.collect()

    results.foreach(println)
  }
  
}

