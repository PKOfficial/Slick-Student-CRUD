package com.knoldus.repo

import com.knoldus.connections.H2DBComponent
import com.knoldus.models.Subject
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSuite}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

class SubjectRepoTest extends FunSuite with SubjectRepo with H2DBComponent with ScalaFutures{

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  test("Add new Subject ") {
    val response = insert(Subject("Scala"))
    whenReady(response) { subId =>
      assert(subId === 3)
    }
  }

  test("Delete a Subject ") {
    val response = delete(2)
    whenReady(response) { subId =>
      assert(subId === 1)
    }
  }

  test("List All Subjects ") {
    val response = getAll
    whenReady(response) { list =>
      assert(list === List(Subject("Scala",1), Subject("Play",2)))
    }
  }

  test("Get a Subjects ") {
    val response = getById(1)
    whenReady(response) { list =>
      assert(list.get === Subject("Scala",1))
    }
  }

  test("Update a Subjects ") {
    val response = updateSubject(Subject("Akka",1))
    whenReady(response) { result =>
      assert(result === 1)
    }
  }

}
