package br.com.project.kotlin_teste.service

import br.com.project.kotlin_teste.dto.user.UserDetailsDTO
import br.com.project.kotlin_teste.dto.user.UserRegisterDTO
import br.com.project.kotlin_teste.entity.User
import br.com.project.kotlin_teste.exception.RequestFieldException
import br.com.project.kotlin_teste.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val repository: UserRepository,
    private val passwordService: PasswordService
) {

    @Throws(RequestFieldException::class)
    fun create(userRegisterDTO: UserRegisterDTO): UserDetailsDTO {
        if (validateUniqueConstraint(userRegisterDTO.email) == true) {
            throw RequestFieldException("O email enviado já está registrado na nossa base de dados", "email")
        }

        val user = User(
            name = userRegisterDTO.name,
            email = userRegisterDTO.email,
            cpf = userRegisterDTO.cpf,
            senha = passwordService.encodePassword(userRegisterDTO.senha),
            status = true // Definindo o status como ativo por padrão
        )


        repository.save(user)

        return UserDetailsDTO(user.name,user.email,user.cpf);
    }

    fun validateUniqueConstraint(email: String): Boolean? {
        return repository.emailExists(email)
    }

    fun findAll(): List<User> = repository.findAll()

    fun findById(id: Long): User? = repository.findById(id).orElse(null)

    fun save(user: User): User = repository.save(user)

    fun update(id: Long, user: User): User? {
        return if (repository.existsById(id)) {
            repository.save(user)
        } else {
            null
        }
    }

    fun deleteById(id: Long) {
        if (repository.existsById(id)) {
            repository.deleteById(id)
        }
    }
}
