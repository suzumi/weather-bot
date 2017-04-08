package example

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._
import skinny.http._
import org.joda.time.{DateTime, DateTimeZone}
import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}



object WeatherBot {

  implicit val formats = DefaultFormats

  case class Forecast(
    dateLabel: String,
    telop: String,
    date: String,
    temperature: Temperature,
    image: Image
  )
  case class Temp(celsius: String, fahrenheit: String)
  case class Forecasts(forecasts: List[Forecast])
  case class Temperature(min: Option[Temp], max: Option[Temp])
  case class Image(width: Int, url: String, title: String, height: Int)
  case class Weather(title: String, description: String)

  def main(args: Array[String]): Unit = {
    val aggregateForecastSummary: Seq[String] = Nil
    val response = HTTP.get("http://weather.livedoor.com/forecast/webservice/json/v1", "city" -> 130010)
    val json = parse(response.asString)

    val descriptionJson = json \ "description" \ "text"
    // サマリ
    val description = s"*今日の天気概要*:\n${descriptionJson.extract[String]}"

    val forecastsJson = (json \ "forecasts").extract[List[Forecast]]
//    val forecasts = forecastsJson

    // 予報
    val forecastsList = forecastsJson.foldLeft(aggregateForecastSummary){ (acc, forecast) =>
      val build = s"""
        | *${forecast.dateLabel}*
        | *${forecast.telop}*
        | *最高気温：${forecast.temperature.max.map(x => x.celsius).getOrElse("")}℃*
        | *最低気温：${forecast.temperature.min.map(x => x.celsius).getOrElse("")}℃*
      """.stripMargin

      acc :+ build

    }
    val forecasts = forecastsList.flatten
    println(forecasts)

    val url = "https://hooks.slack.com/services/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"
    val reqJson = ("text" -> s"$forecasts\n$description")

    HTTP.post(url, compact(render(reqJson)))

  }

  def serialise(value: Any): Array[Byte] = {
    val stream: ByteArrayOutputStream = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(stream)
    oos.writeObject(value)
    oos.close
    stream.toByteArray
  }

}
