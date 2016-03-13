name := "cpuapi-spark"

version := "1.0"

scalaVersion := "2.10.4"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com"
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.5.0" % "provided",
  "org.apache.spark" %% "spark-sql" % "1.5.0" % "provided",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.typesafe" % "config" % "1.3.0",
  "com.github.nscala-time" %% "nscala-time" % "2.10.0"
)