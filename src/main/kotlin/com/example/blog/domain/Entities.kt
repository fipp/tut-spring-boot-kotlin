package com.example.blog.domain

import com.example.blog.toSlug
import java.sql.Timestamp
import javax.persistence.*

@Entity
class Article(
		var title: String,
		var headline: String,
		var content: String,
		@ManyToOne var author: User,
		var slug: String = title.toSlug(),
		var addedAt: Timestamp = Timestamp(System.currentTimeMillis()),
		@Id @GeneratedValue var id: Long? = null)

@Entity
@Table(name = "blog_user")
class User(
		var login: String,
		var firstname: String,
		var lastname: String,
		var description: String? = null,
		@Id @GeneratedValue var id: Long? = null)
