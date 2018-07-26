package incorrect3.repositories

import javax.inject.Inject

import anorm.SQL
import incorrect3.models.UserData

import scala.util.Try

class UserDataRepository @Inject()(database: play.api.db.Database) {

  def save(userData: UserData): Try[Boolean] = {
    executeSqlQuery(sanitizeUserData(userData))
  }

  private def sanitizeUserData(userData: UserData): String = {
    val escapedUserName = escapeSingleQuotes(userData)
    s"INSERT INTO users VALUE '$escapedUserName';"
  }

  private def escapeSingleQuotes(userData: UserData): String = {
    val userName = s"${userData.firstName} ${userData.lastName}"
    userName.replace("'", "\\'")
  }

  private def executeSqlQuery(statementAsString: String): Try[Boolean] = Try {
    database.withConnection { implicit c =>
      SQL(statementAsString).execute()
    }
  }
}
