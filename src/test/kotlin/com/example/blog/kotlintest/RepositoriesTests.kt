package com.example.blog.kotlintest

import com.example.blog.ArticleRepository
import com.example.blog.TestcontainersConfig
import com.example.blog.UserRepository
import com.example.blog.domain.Article
import com.example.blog.domain.User
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfig::class)
class RepositoriesTests @Autowired constructor(
        val entityManager: TestEntityManager,
        val userRepository: UserRepository,
        val articleRepository: ArticleRepository) : StringSpec() {

    init {
        "When findByIdOrNull then return article" {
            val juergen = User(login = "springjuergen", firstname = "Juergen", lastname = "Hoeller")
            entityManager.persist(juergen)
            val article = Article("Spring Framework 5.0 goes GA", "Dear Spring community ...", "Lorem ipsum", juergen)
            entityManager.persist(article)
            entityManager.flush()
            val found = articleRepository.findByIdOrNull(article.id!!)
            found shouldBe article
        }

        "When findByLogin then return User" {
            val juergen = User("springjuergen", "Juergen", "Hoeller")
            entityManager.persist(juergen)
            entityManager.flush()
            val user = userRepository.findByLogin(juergen.login)
            user shouldBe juergen
        }
    }
}