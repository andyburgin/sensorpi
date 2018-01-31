name := "KafkaSensor"

version := "1.0"

organization := "com.andyburgin.sparkstreaming"

scalaVersion := "2.10.6"

resolvers += Resolver.typesafeRepo("releases")

libraryDependencies ++= Seq(
"org.apache.spark" %% "spark-core" % "1.6.2" % "provided",
"org.apache.spark" %% "spark-streaming" % "1.6.2" % "provided"
)

libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka" % "1.6.2" 
libraryDependencies += "org.apache.kafka" %% "kafka" % "0.8.2.1"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.6.2"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.8"
libraryDependencies += "com.pygmalios" %% "reactiveinflux" % "0.10.0.5"
libraryDependencies += "com.typesafe" % "config" % "1.2.1"


assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.first
}
