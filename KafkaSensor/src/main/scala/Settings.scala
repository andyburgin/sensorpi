package com.andyburgin.sparkstreaming

import com.typesafe.config.Config

class Settings(config: Config) {
  val AppName = config.getString("KafkaSensor.AppName")
  val SparkMaster = config.getString("KafkaSensor.SparkMaster")
  val KafkaBrokerlist = config.getString("KafkaSensor.KafkaBrokerlist")
  val KafkaTopics = config.getString("KafkaSensor.KafkaTopics")
  val InfluxDBURI = config.getString("KafkaSensor.InfluxDBURI")
  val InfluxDBMeasurement = config.getString("KafkaSensor.InfluxDBMeasurement")
  val CheckpointPath = config.getString("KafkaSensor.CheckpointPath")

}