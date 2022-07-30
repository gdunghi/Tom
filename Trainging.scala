object EntryPoint extends App {

  import scalaj.http._
  import scala.io.Source
  import java.io.FileWriter
  import scala.xml.XML
  import scala.xml.NodeSeq

  val urls = Source.fromFile("source.txt").getLines()

  val contents = urls
    .map(url => getContent("ukraine", url))
    .toList
    .reduce((a, b) => {
      a ++ b
    })

  write("ukraine-" + time + ".txt", contents)

  def getContent(q: String, url: String): Seq[String] = {
    val res: HttpResponse[String] = Http(url).asString
    val elements = XML.loadString(res.body)
    val titles = elements \\ "item" \ "title"
    val result = titles
      .filter(title => title.text.toLowerCase() contains q)
      .map(title => title.text)
    result
  }

  def write(fileName: String, contents: Seq[String]) = {
    val fw = new FileWriter(fileName, true);
    for (title <- contents) {
      fw.write(title + "\n")
    }
    fw.close()
  }

  // val fw = new FileWriter("ukraine-24-07-2022-9-37.txt", true);
  // for (uri <- urls) {

  //   val response: HttpResponse[String] =
  //     Http(uri).asString

  //   val elements = XML.loadString(response.body)
  //   val titles = elements \\ "item" \ "title"

  //   val ukraineNews =
  //     titles.filter(news => news.text.toLowerCase() contains "ukraine")

  //   for (title <- ukraineNews) {
  //     fw.write(title.text + "\n")
  //   }

  // }
  // fw.close()

}
// (fetchFeeds andThen filter andThen saveToFile)(sources: Seq[String])
