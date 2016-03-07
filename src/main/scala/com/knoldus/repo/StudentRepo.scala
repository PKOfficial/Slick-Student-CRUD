package com.knoldus.repo

import com.knoldus.connections.DBComponent
import com.knoldus.models.Student
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait StudentRepo extends StudentTable {

  this: DBComponent =>

  import driver.api._

  def insert(student: Student): Future[Int] = db.run {
    studentTable.returning(studentTable.map(_.id)) += student
  }

  def delete(id: Int): Future[Int] = {
    val deleteStatement = studentTable.filter(_.id === id).delete
    db.run(deleteStatement)
  }

  def getAll: Future[List[Student]] = {
    db.run {
      studentTable.to[List].result
    }
  }

  def getById(id: Int): Future[Option[Student]] = {
    db.run {
      studentTable.filter(_.id === id).result.headOption
    }
  }

  def update(student: Student): Future[Int] = {
    db.run {studentTable.filter(_.id === student.id).update(student)}
  }

  def drop = db.run{studentTable.schema.drop}

}

trait StudentTable {

  this: DBComponent =>

  import driver.api._

  class StudentTable(tag: Tag) extends Table[Student](tag, "students") {
    val id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    val name = column[String]("name")
    val email = column[String]("email")
    def * = (name,email, id) <>(Student.tupled, Student.unapply)
  }

  val studentTable = TableQuery[StudentTable]

}
