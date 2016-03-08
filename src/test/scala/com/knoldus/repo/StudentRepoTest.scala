package com.knoldus.repo

import com.knoldus.connections.H2DBComponent
import com.knoldus.models.Student
import org.scalatest.FunSuite
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

class StudentRepoTest extends FunSuite with StudentRepo with H2DBComponent with ScalaFutures{

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  test("Add new Student ") {
    val response = insert(Student("Prabhat","pk@knoldus.in"))
    whenReady(response) { stuId =>
      assert(stuId === 2)
    }
  }

  test("Delete a Student ") {
    val response = delete(1)
    whenReady(response) { stuId =>
      assert(stuId === 1)
    }
  }

  test("Delete a Invalid Student ") {
    val response = delete(11)
    whenReady(response) { stuId =>
      assert(stuId === 0)
    }
  }

  test("List All Students ") {
    val response = getAll
    whenReady(response) { list =>
      assert(list === List(Student("Prabhat","pk@gmail.com",1)))
    }
  }

  test("Get a Students ") {
    val response = getById(1)
    whenReady(response) { list =>
      assert(list.get === Student("Prabhat","pk@gmail.com",1))
    }
  }

  test("Get a Invalid Students ") {
    val response = getById(11)
    whenReady(response) { list =>
      assert(list === None)
    }
  }

  test("Update a Students ") {
    val response = update(Student("PrabhatKashyap","pk@gmail.com",1))
    whenReady(response) { result =>
      assert(result === 1)
    }
  }

  test("Update a Invalid Students ") {
    val response = update(Student("PrabhatKashyap","pk@gmail.com",12))
    whenReady(response) { result =>
      assert(result === 0)
    }
  }

}
