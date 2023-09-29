package daw.isel.pt.gomoku.services

import daw.isel.pt.gomoku.repository.interfaces.GameRepository
import org.springframework.stereotype.Component

@Component
class GameServices(val gameRepository: GameRepository) {
}