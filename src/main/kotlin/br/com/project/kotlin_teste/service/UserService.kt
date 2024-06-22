package br.com.project.kotlin_teste.service

import br.com.project.kotlin_teste.entity.User
import br.com.project.kotlin_teste.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun findAll(): List<User> = userRepository.findAll()

    fun findById(id: Long): User? = userRepository.findById(id).orElse(null)

    fun save(user: User): User = userRepository.save(user)

    fun update(id: Long, user: User): User? {
        return if (userRepository.existsById(id)) {
            userRepository.save(user)
        } else {
            null
        }
    }

    fun deleteById(id: Long) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
        }
    }
}
