package daw.isel.pt.gomoku.services


import daw.isel.pt.gomoku.domain.User
import daw.isel.pt.gomoku.repository.interfaces.UserRepository
import daw.isel.pt.gomoku.services.exceptions.NotFoundException
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserServices(val userRepo: UserRepository) {
    fun getUser(id: Int) = userRepo.getUser(id)?:throw NotFoundException()
    fun createUser(username: String): User {
        if (username.isEmpty()) throw IllegalArgumentException()
        val newToken = UUID.randomUUID().toString()
        return userRepo.createUser(username, newToken)
    }
}