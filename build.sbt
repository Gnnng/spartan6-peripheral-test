organization := "cn.edu.zju.cs"

version := "0.1"

name := "demo"

scalaVersion := "2.10.4"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-language:reflectiveCalls")

val chiselVersion_h = System.getProperty("chiselVersion", "latest.release")

libraryDependencies ++= ( if (chiselVersion_h != "None" ) ("edu.berkeley.cs" %% "chisel" % chiselVersion_h) :: Nil; else Nil)