package xin.saul.spark

import scalaj.http.Http

import scala.io.Source

object EsIndexHelper {
  def createIndex(indexName: String, nodeURL: String): Unit = {
    Http(s"${nodeURL}/${indexName}/_mapping").asString.code match {
      case 404 =>
        Http(s"${nodeURL}/${indexName}")
          .header("content-type", "application/json")
          .put(Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream("event-mapping.json")).mkString).asString
        println(s"create new index ${indexName}")
      case _ =>
        println(s"Index ${indexName} has been existed")
    }
  }
}
