package example

import org.json4s._
import skinny.http._


object Hello extends Greeting {

  def main(args: Array[String]): Unit = {
    args.foreach(println)
    val response = HTTP.get("http://weather.livedoor.com/forecast/webservice/json/v1", "city" -> 130010)
    println(response.asString)
    println(greeting)
  }

}

trait Greeting {
  lazy val greeting: String = "hello"
}
