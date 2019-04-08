object LearningScala2 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(141); 
  // Flow control
  
  // If / else syntax
  if (1 > 3) println("Impossible!") else println("The world makes sense.");$skip(96); 
  
  if (1 > 3) {
  	println("Impossible!")
  } else {
  	println("The world makes sense.")
  };$skip(68); 
  
  // Matching - like switch in other languages:
  val number = 3;System.out.println("""number  : Int = """ + $show(number ));$skip(146); 
  number match {
  	case 1 => println("One")
  	case 2 => println("Two")
  	case 3 => println("Three")
  	case _ => println("Something else")
 	};$skip(87); 
 	
 	// For loops
 	for (x <- 1 to 4) {
 		val squared = x * x
 		println(squared)
 	};$skip(81); 
                                                  
  // While loops
  var x = 10;System.out.println("""x  : Int = """ + $show(x ));$skip(47); 
  while (x >= 0) {
  	println(x)
  	x -= 1
  };$skip(59); 
                                                  
  x = 0;$skip(42); 
  do { println(x); x+=1 } while (x <= 10);$skip(154); val res$0 = 
                                                  
   // Expressions
   // "Returns" the final value in a block automatically
   
   {val x = 10; x + 20};System.out.println("""res0: Int = """ + $show(res$0));$skip(82); 
                                                
	 println({val x = 10; x + 20});$skip(267); 
	 
	 // EXERCISE
	 // Write some code that prints out the first 10 values of the Fibonacci sequence.
	 // This is the sequence where every number is the sum of the two numbers before it.
	 // So, the result should be 0, 1, 1, 2, 3, 5, 8, 13, 21, 34
	 var fibo_n1 = 0;System.out.println("""fibo_n1  : Int = """ + $show(fibo_n1 ));$skip(18); 
	 var fibo_n2 = 1;System.out.println("""fibo_n2  : Int = """ + $show(fibo_n2 ));$skip(18); 
	 var fibo_n3 = 1;System.out.println("""fibo_n3  : Int = """ + $show(fibo_n3 ));$skip(295); 
	 //loop over x
	 for (x <- 1 to 10) {
	 	
		 x match {
		 	case 1 => println(fibo_n1)
		 	case 2 => {
		 		println(fibo_n1 + fibo_n2)
		 		fibo_n3 = fibo_n1
		 	}
			case _ => {
				println(fibo_n1 + fibo_n2)
				fibo_n3 = fibo_n1
				fibo_n1 = fibo_n2
				fibo_n2 = fibo_n3 + fibo_n2
			}
		}}
	 	
	 }
	 
	   
}
