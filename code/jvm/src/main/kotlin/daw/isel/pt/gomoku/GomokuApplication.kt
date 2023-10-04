package daw.isel.pt.gomoku

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class GomokuApplication{
	@Bean
	fun jdbi() : Jdbi {
		val jdbcDatabaseURL =
			System.getenv("JDBC_DATABASE_URL")
				?: "jdbc:postgresql://localhost/postgres?user=postgres&password=postgres"
		val dataSource = PGSimpleDataSource()
		dataSource.setURL(jdbcDatabaseURL)

		return Jdbi.create(dataSource)
			.installPlugin(KotlinPlugin())
			//.registerColumnMapper()
	}
}

fun main(args: Array<String>) {
	runApplication<GomokuApplication>(*args)
}
