name := "Slick_Student_CRUD"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % "3.0",
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc4",
  "com.typesafe.slick"  %%     "slick-hikaricp"           %      "3.1.1",
  "ch.qos.logback"       %     "logback-classic"          %      "1.1.3",
  "com.typesafe.slick"   %%    "slick"            	      %      "3.1.1",
  "org.scalatest"        %%    "scalatest"    	      %      "2.2.5"     %    "test",
  "com.h2database"       % 	   "h2"                       %      "1.4.187"    %   "test"

)