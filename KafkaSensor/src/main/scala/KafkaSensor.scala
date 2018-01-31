package com.andyburgin.sparkstreaming

import org.apache.log4j.Level
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import Utility._

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.storage.StorageLevel

import org.apache.spark.SparkContext
import org.apache.spark.streaming.kafka._
import kafka.serializer.StringDecoder

import play.api.libs.json._

import java.net.URI
import com.pygmalios.reactiveinflux._
import org.joda.time.DateTime
              
object KafkaSensor {
  
  def main(args: Array[String]) {

    val config = new Settings(ConfigFactory.load())
    val conf = new SparkConf().setAppName(config.AppName)
    
    // set spark master if configured
    if(config.SparkMaster.length() >0 ) {
      conf.setMaster(config.SparkMaster)
    }
    
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(1))
    
    setupLogging()
    
    // get config settings
    val InfluxDBURI = config.InfluxDBURI
    val InfluxDBMeasurement = config.InfluxDBMeasurement

    // kafka params
    val kafkaParams = Map("metadata.broker.list" -> config.KafkaBrokerlist)
    val topics = List(config.KafkaTopics).toSet

    // create set of sensor metrics for the batch from kafka
    val lines = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topics).map(m => Json.parse(m._2))
     
    lines.foreachRDD( rdd => {
      rdd.foreach( { line =>
        // extract sample values for timestamp
        val timestamp:Long = (line\ "timestamp").as[Long]
        val temperature:BigDecimal = (line\ "temperature").as[BigDecimal]
        val pressure:BigDecimal = (line\ "pressure").as[BigDecimal]
        val heading:BigDecimal = (line\ "heading").as[BigDecimal]
        val lightv:Int = (line\ "lightv").as[Int]
        val lightr:Int = (line\ "lightr").as[Int]
        val lightg:Int = (line\ "lightg").as[Int]
        val lightb:Int = (line\ "lightb").as[Int]
        val motionx:BigDecimal = (line\ "motionx").as[BigDecimal]
        val motiony:BigDecimal = (line\ "motiony").as[BigDecimal]
        val motionz:BigDecimal = (line\ "motionz").as[BigDecimal]
        
        //little heartbeat rendering when on cluster
        println("Writing " + timestamp)
        
        //post sensor data to Influx
        val result = withInfluxDb(new URI(InfluxDBURI),InfluxDBMeasurement) { db =>
          val point = Point(
            time = new DateTime(timestamp),
            measurement = "sensor",
            tags = Map(
              "sensor" -> "EnviroPhat",
              "host" -> "10.0.0.99"),
            fields = Map(
              "temperature" -> temperature,
              "pressure" -> pressure,
              "heading" -> heading,
              "lightv" -> lightv,
              "lightr" -> lightr,
              "lightg" -> lightg,
              "lightb" -> lightb,
              "motionx" -> motionx,
              "motiony" -> motiony,
              "motionz" -> motionz)
          )
          db.write(point)
        }
      })
    })
    
    ssc.checkpoint(config.CheckpointPath)
    ssc.start()
    ssc.awaitTermination()
  }
}

