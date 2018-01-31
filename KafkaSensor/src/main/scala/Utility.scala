package com.andyburgin.sparkstreaming

import org.apache.log4j.Level

object Utility {

  def setupLogging() = {
    import org.apache.log4j.{Level, Logger}   
    val logger = Logger.getRootLogger()
    logger.setLevel(Level.ERROR)   
  }
  
}