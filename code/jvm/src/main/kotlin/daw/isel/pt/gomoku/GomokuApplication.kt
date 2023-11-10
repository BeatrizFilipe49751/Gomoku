package daw.isel.pt.gomoku

import daw.isel.pt.gomoku.domain.Sha256TokenEncoder
import daw.isel.pt.gomoku.domain.UsersDomainConfig
import daw.isel.pt.gomoku.repository.dataJDBI.mappers.PasswordValidationInfoMapper
import kotlinx.datetime.Clock
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import kotlin.time.Duration.Companion.hours

@SpringBootApplication
class GomokuApplication{
	@Bean
	fun jdbi() : Jdbi {
		val jdbcDatabaseURL =
			System.getenv("JDBC_DATABASE_URL")
		val dataSource = PGSimpleDataSource()
		dataSource.setURL(jdbcDatabaseURL)

		return Jdbi.create(dataSource)
			.installPlugin(KotlinPlugin())
			.registerColumnMapper(PasswordValidationInfoMapper())
	}

	@Bean
	fun passwordEncoder() = BCryptPasswordEncoder()

	@Bean
	fun tokenEncoder() = Sha256TokenEncoder()

	@Bean
	fun clock() = Clock.System

	@Bean
	fun usersDomainConfig() = UsersDomainConfig(
		tokenSizeInBytes = 256 / 8,
		tokenTtl = 24.hours,
		tokenRollingTtl = 1.hours,
		maxTokensPerUser = 3
	)

}

fun main(args: Array<String>) {
	runApplication<GomokuApplication>(*args)
}
