package br.com.project.kotlin_teste.repository

import br.com.project.kotlin_teste.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>{

    fun findByEmail(email: String): User?


    @Query("SELECT COUNT(u.id) > 0 FROM User u WHERE u.email = :email")
    fun emailExists(@Param("email") email: String?): Boolean?

}
