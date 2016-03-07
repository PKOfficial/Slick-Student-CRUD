package com.knoldus.repo

import com.knoldus.connections.H2DBComponent
import com.knoldus.models.SubjectAllocation
import org.scalatest.FunSuite
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

class SubjectAllocationRepoTest extends FunSuite with SubjectAllocationRepo with H2DBComponent with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  test("Insert in Allocation"){
    val response = insert(SubjectAllocation(1,1))
    whenReady(response){subAllocId =>
    assert(subAllocId == 3)
    }
  }

  test("Delete in student allocation"){
    val response = delete(1)
    whenReady(response){result =>
    assert(result == 1)
    }
  }

  test("Update in Student Allocation"){
    val response = update(SubjectAllocation(1,2,1))
    whenReady(response){result =>
    assert(result == 1)
    }
  }


  test("List All") {
    val response = getAll
    whenReady(response) { stuId =>
      assert(stuId === Vector(("Scala","Prabhat"), ("Play","Prabhat")))
    }
  }

  test("List Student by Subject") {
    val response = getStudentBySubject(2)
    whenReady(response) { stuId =>
      assert(stuId === Vector(("Play","Prabhat")))
    }
  }

  test("List Subject by Student") {
    val response = getSubjectByStudent(1)
    whenReady(response) { stuId =>
      assert(stuId === Vector(("Prabhat","Scala"), ("Prabhat","Play")))
    }
  }

}
