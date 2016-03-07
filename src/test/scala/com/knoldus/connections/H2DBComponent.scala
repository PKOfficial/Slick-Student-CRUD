package com.knoldus.connections

trait H2DBComponent extends DBComponent {

  val driver = slick.driver.H2Driver

  import driver.api._

  val h2Url = "jdbc:h2:mem:test;MODE=MySql;DATABASE_TO_UPPER=false;INIT=runscript from 'src/test/resources/schema.sql'\\;runscript from 'src/test/resources/schemadata.sql'"

  val db: Database = Database.forURL(url = h2Url, driver = "org.h2.Driver")

}