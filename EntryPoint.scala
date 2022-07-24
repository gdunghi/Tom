object EntryPoint extends App {

  import scalaj.http._
  val response: HttpResponse[String] =
    Http("http://feeds.bbci.co.uk/news/rss.xml").asString

  import scala.xml.XML
  val elements = XML.loadString(response.body)
  val titles = elements.\\("item").\("title")
  for (title <- titles) {
    println(title.text)
  }
}
