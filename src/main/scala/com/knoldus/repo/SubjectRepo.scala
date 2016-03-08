package com.knoldus.repo

import com.knoldus.connections.DBComponent
import com.knoldus.models.Subject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait SubjectRepo extends SubjectTable with SubjectAllocationTable{

  this: DBComponent =>

  import driver.api._

  def insert(subject: Subject): Future[Int] = db.run {
    subjectTable.returning(subjectTable.map(_.id)) += subject
  }

  def delete(id: Int): Future[Int] = {
    db.run(subjectAllocationTable.filter(_.sub_id === id).delete)
    db.run(subjectTable.filter(_.id === id).delete)
  }

  def getAll: Future[List[Subject]] = db.run {
    subjectTable.to[List].result
  }

  def getById(id: Int): Future[Option[Subject]] = db.run {
    subjectTable.filter(_.id === id).result.headOption
  }

  def updateSubject(subject: Subject): Future[Int] = {
    db.run{subjectTable.filter(_.id === subject.id).update(subject)}
  }

}

trait SubjectTable {

  this: DBComponent =>

  import driver.api._

  class SubjectTable(tag: Tag) extends Table[Subject](tag, "subjects") {
    val id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    val name = column[String]("name")
String
    def * = (name, id) <>(Subject.tupled, Subject.unapply)
  }

  val subjectTable = TableQuery[SubjectTable]

}
