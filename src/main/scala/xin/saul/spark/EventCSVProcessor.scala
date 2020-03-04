package xin.saul.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.ScalaReflection
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StructType
import org.elasticsearch.spark.sql._

object EventCSVProcessor {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName("Order CSV Processor")
      .config("spark.driver.host", "127.0.0.1")
      .master("local")
      .getOrCreate()

    run(spark, args(0), args(1), args(2))
    spark.stop()
  }

  def run(spark: SparkSession, inputPath: String, index: String, eventDescriptionPath: String): Unit = {

    val esConf = Map(
      "es.index.auto.create" -> "false",
      "es.nodes.wan.only" -> "true",
      "es.nodes" -> "127.0.0.1",
      "es.port" -> "9200"
    )

    EsIndexHelper.createIndex(index, s"http://${esConf("es.nodes")}:${esConf("es.port")}")

    val loactionTransfer = udf((long: Int, lat: Int) => if (long.isNaN || lat.isNaN) null else s"${long},${lat}")
    val quadClassTransfer = udf((quadClass: Long) => quadClass match {
      case 1 => "Verbal Cooperation"
      case 2 => "Material Cooperation"
      case 3 => "Verbal Conflict"
      case 4 => "Material Conflict"
      case _ => "UNKNOWN"
    })


    //https://www.gdeltproject.org/data/lookups/CAMEO.eventcodes.txt
    val eventDescriptionDF = spark.read.format("csv").option("delimiter", "\t").option("header", "true")
      .load(eventDescriptionPath)

    val originalEventDF = spark.read
      .format("csv")
      .option("delimiter", "\t")
      .schema(ScalaReflection.schemaFor[Event].dataType.asInstanceOf[StructType])
      .load(inputPath)

    val eventDF = originalEventDF.join(eventDescriptionDF, originalEventDF("EventCode") === eventDescriptionDF("CAMEOEVENTCODE"), "left")

    eventDF
      .withColumn("Actor1Geo_Location", loactionTransfer(col("Actor1Geo_Lat"), col("Actor1Geo_Long")))
      .withColumn("Actor2Geo_Location", loactionTransfer(col("Actor2Geo_Lat"), col("Actor2Geo_Long")))
      .withColumn("ActionGeo_Location", loactionTransfer(col("ActionGeo_Lat"), col("ActionGeo_Long")))
      .withColumn("QuadClass", quadClassTransfer(col("QuadClass")))
      .withColumn("EventDescription", col("EVENTDESCRIPTION"))
      .drop("Actor1Geo_Lat", "Actor1Geo_Long", "Actor2Geo_Long", "Actor2Geo_Lat", "ActionGeo_Long", "ActionGeo_Lat", "CAMEOEVENTCODE")
      .saveToEs(s"${index}/event", esConf)
  }
}
