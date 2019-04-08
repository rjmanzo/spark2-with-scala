package com.sundogsoftware.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._

/** Count up how many of each word occurs in a book, using regular expressions and sorting the final results */
object WordCountBetterFilteringAndSorted {
 
  /** Our main function where the action happens */
  def main(args: Array[String]) {
   
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)
    
     // Create a SparkContext using the local machine
    val sc = new SparkContext("local", "WordCountBetterSorted")   
    
    // Load each line of my book into an RDD
    val input = sc.textFile("../datasets/book.txt")
    
    // Split using a regular expression that extracts words
    val words = input.flatMap(x => x.split("\\W+"))
    
    // Normalize everything to lowercase
    val lowercaseWords = words.map(x => x.toLowerCase())
    
    //List of Common english word
    val CommondWord: Array[String] = Array("a", "about", "above", "after", "again", "against", "ain", "all", "am", "an", "and", 
     "any", "are", "aren", "aren't", "as", "at", "be", "because", "been", "before", "being", "below",
     "between", "both", "but", "by", "can", "couldn", "couldn't", "d", "did", "didn", "didn't", "do", "does",
     "doesn", "doesn't", "doing", "don", "don't", "down", "during", "each", "few", "for", "from", "further", "had",
     "hadn", "hadn't", "has", "hasn", "hasn't", "have", "haven", "haven't", "having", "he", "her", "here", "hers", "herself",
     "him", "himself", "his", "how", "i", "if", "in", "into", "is", "isn", "isn't", "it", "it's", "its", "itself", "just", "ll",
     "m", "ma", "me", "mightn", "mightn't", "more", "most", "mustn", "mustn't", "my", "myself", "needn", "needn't", "no", "nor",
     "not", "now", "o", "of", "off", "on", "once", "only", "or", "other", "our", "ours", "ourselves", "out", "over", "own", "re",
     "s", "same", "shan", "shan't", "she", "she's", "should", "should've", "shouldn", "shouldn't", "so", "some", "such", "t", "than",
     "that", "that'll", "the", "their", "theirs", "them", "themselves", "then", "there", "these", "they", "this", "those", "through", "to",
     "too", "under", "until", "up", "ve", "very", "was", "wasn", "wasn't", "we", "were", "weren", "weren't", "what", "when", "where", "which",
     "while", "who", "whom", "why", "will", "with", "won", "won't", "wouldn", "wouldn't", "y", "you", "you'd", "you'll", "you're", "you've",
     "your", "yours", "yourself", "yourselves")   
     
    // Filtering English commond words
    val lowercaseWordsFiltered = lowercaseWords.filter(!CommondWord.contains(_))   
    
    // Count of the occurrences of each word
    val wordCounts = lowercaseWordsFiltered.map(x => (x, 1)).reduceByKey( (x,y) => x + y )
    
    // Flip (word, count) tuples to (count, word) and then sort by key (the counts)
    val wordCountsSorted = wordCounts.map( x => (x._2, x._1) ).sortByKey()
    
    // Print the results, flipping the (count, word) results to word: count as we go.
    for (result <- wordCountsSorted) {
      val count = result._1
      val word = result._2
      println(s"$word: $count")
    }
    
  }
  
}

