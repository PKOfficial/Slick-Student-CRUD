package com.knoldus.repo

import com.knoldus.connections.DBComponent
import com.knoldus.models.{Subject, Student, SubjectAllocation}
import scala.collection.concurrent.RDCSS_Descriptor
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait SubjectAllocationRepo extends SubjectAllocationTable with SubjectTable with StudentTable {

  this: DBComponent =>

  import driver.api._

  def insert(subjectAllocation: SubjectAllocation): Future[Int] = db.run {
    subjectAllocationTable.returning(subjectAllocationTable.map(_.id)) += subjectAllocation
  }

  def delete(id: Int): Future[Int] = {
    val deleteStatement = subjectAllocationTable.filter(_.id === id).delete
    db.run(deleteStatement)
  }

  def getAll: Future[Seq[(String, String)]] = {
    val findStudent = for {
      ((sub, subAlloc), stu) <- subjectTable.join(subjectAllocationTable).on(_.id === _.sub_id).
        join(studentTable).on (_._2.stu_id === _.id)
    } yield(sub.name,stu.name)
    db.run(findStudent.result)
  }

  def getStudentBySubject(id:Int): Future[Seq[(String, String)]] = {
    val findStudent = for {
      ((sub,subAlloc),stu)<- subjectTable.filter(_.id === id).join(subjectAllocationTable).on(_.id === _.sub_id).
      join(studentTable).on(_._2.stu_id === _.id)
      }yield(sub.name, stu.name)
    db.run(findStudent.result)
  }

  def getSubjectByStudent(id:Int): Future[Seq[(String, String)]] = {
    val findStudent = for {
      ((sub,subAlloc),stu)<- studentTable.filter(_.id === id).join(subjectAllocationTable).on(_.id === _.stu_id).
        join(subjectTable).on(_._2.sub_id === _.id)
    }yield(sub.name, stu.name)
    db.run(findStudent.result)
  }

  def update(subjectAllocation: SubjectAllocation): Future[Int] = {
    db.run {subjectAllocationTable.filter(_.id === subjectAllocation.id).update(subjectAllocation)}
  }

}

trait SubjectAllocationTable {

  this: DBComponent =>

  import driver.api._

  class SubjectAllocationTable(tag: Tag) extends Table[SubjectAllocation](tag, "subject_allocation") {
    val stu_id = column[Int]("stu_id")
    val sub_id = column[Int]("sub_id")
    val id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def * = (stu_id,sub_id, id) <>(SubjectAllocation.tupled, SubjectAllocation.unapply)
  }

  val subjectAllocationTable = TableQuery[SubjectAllocationTable]

}
