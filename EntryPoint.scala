object EntryPoint extends App {

  import scalaj.http._
  import scala.io.Source
  import java.io.FileWriter
  import scala.xml.XML
  import scala.xml.NodeSeq

  val urls = Source.fromFile("source.txt").getLines().toList
  val filterUkraine = filter("Ukraine") _

  (fetchFeeds _ andThen filterUkraine andThen saveToFile) (urls)

  def getContent(url: String): Seq[String] = {
    val res: HttpResponse[String] = Http(url).asString
    val elements = XML.loadString(res.body)
    val titles = elements \\ "item" \ "title"
    val result = titles.map(title => title.text)
    result
  }

  def fetchFeeds(urls: Seq[String]): Seq[String] = {
    val contents = urls
      .map(url => getContent(url)).toList
      .reduce((a, b) => {
        a ++ b
      })
    contents

  }

  def filter(keyword: String)(contents: Seq[String]): Seq[String] = {
    val result = contents
      .filter(content => content.toLowerCase() contains keyword.toLowerCase())
    result
  }

  def saveToFile(contents: Seq[String]) = {
    import java.text.SimpleDateFormat
    import java.util.Calendar
    val format = new SimpleDateFormat("d-M-y-hh-mm")
    val fileName = "ukraine-" + format.format(Calendar.getInstance().getTime()) + ".txt"
    val fw = new FileWriter(fileName, true);
    for (title <- contents) {
      fw.write(title + "\n")
    }
    fw.close()
  }


}
