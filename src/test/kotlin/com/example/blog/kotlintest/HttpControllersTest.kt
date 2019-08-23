package com.example.blog.kotlintest

import com.example.blog.Article
import com.example.blog.ArticleRepository
import com.example.blog.User
import com.example.blog.UserRepository
import com.ninjasquad.springmockk.MockkBean
import io.kotlintest.specs.StringSpec
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class HttpControllersTest(@Autowired val mockMvc: MockMvc,
                          @MockkBean val userRepository: UserRepository,
                          @MockkBean val articleRepository: ArticleRepository) : StringSpec() {

    init {

        "List articles" {
            val juergen = User("springjuergen", "Juergen", "Hoeller")
            val spring5Article = Article("Spring Framework 5.0 goes GA", "Dear Spring community ...", "Lorem ipsum", juergen)
            val spring43Article = Article("Spring Framework 4.3 goes GA", "Dear Spring community ...", "Lorem ipsum", juergen)
            every { articleRepository.findAllByOrderByAddedAtDesc() } returns listOf(spring5Article, spring43Article)
            mockMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("\$.[0].author.login").value(juergen.login))
                    .andExpect(jsonPath("\$.[0].slug").value(spring5Article.slug))
                    .andExpect(jsonPath("\$.[1].author.login").value(juergen.login))
                    .andExpect(jsonPath("\$.[1].slug").value(spring43Article.slug))
        }

        "List users" {
            val juergen = User("springjuergen", "Juergen", "Hoeller")
            val smaldini = User("smaldini", "Stéphane", "Maldini")
            every { userRepository.findAll() } returns listOf(juergen, smaldini)
            mockMvc.perform(get("/api/user/").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("\$.[0].login").value(juergen.login))
                    .andExpect(jsonPath("\$.[1].login").value(smaldini.login))
        }
    }
}