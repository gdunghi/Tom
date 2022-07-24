println("Hello world")
1 + 1
var x = 0
val y = 1
x = 9

if (x == 0) println("Zero")
else if (x < 0) println("Negative")
else println("Positive")

val listOfInt = List(1, 2, 3, 4, 5)

for (element <- listOfInt) println(element)

listOfInt.foreach(element => println(element))
listOfInt.foreach(println)
val reuslt = for {
  e <- listOfInt
  if e > 3
} yield e

var url = "http://feeds.bbci.co.uk/news/rss.xml"

import scalaj.http._

val response: HttpResponse[String] =
  Http("http://foo.com/search").param("q", "monkeys").asString
response.body
