package com.example.blog.kotlintest

import com.example.blog.toSlug
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests @Autowired constructor(
        private val restTemplate: TestRestTemplate
) : StringSpec() {

    init {
        "Assert blog page title, content and status code" {
            val entity = restTemplate.getForEntity<String>("/")
            entity.statusCode shouldBe HttpStatus.OK
            entity.body shouldContain "<h1>Blog</h1>"
            entity.body shouldContain "Reactor"
        }

        "Assert article page title, content and status code" {
            val title = "Reactor Aluminium has landed"
            val entity = restTemplate.getForEntity<String>("/article/${title.toSlug()}")
            entity.statusCode shouldBe HttpStatus.OK
            entity.body shouldContain title
            entity.body shouldContain "Lorem ipsum"
            entity.body shouldContain "dolor sit amet"
        }

    }

}