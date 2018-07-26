package original.repositories

import javax.inject.Inject

import anorm._
import original.models.UserData

import scala.util.Try

class UserDataRepository @Inject()(database: play.api.db.Database) {

  def save(userData: UserData): Try[Boolean] = {
    val userName = s"${userData.firstName} ${userData.lastName}"
    val statementAsString = s"INSERT INTO users VALUE '$userName';"
    executeSqlQuery(statementAsString)
  }

  private def executeSqlQuery(statementAsString: String): Try[Boolean] = Try {
    database.withConnection { implicit c =>
      SQL(statementAsString).execute()
    }
  }
}
