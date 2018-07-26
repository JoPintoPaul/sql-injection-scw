package incorrect1.repositories

import javax.inject.Inject

import anorm.SQL
import incorrect1.models.UserData

import scala.util.{Failure, Success, Try}

class UserDataRepository @Inject()(database: play.api.db.Database) {

  def save(userData: UserData): Try[Boolean] = {
    validateUserData(userData) flatMap executeSqlQuery
  }

  private def validateUserData(userData: UserData): Try[String] = {
    val userName = s"${userData.firstName} ${userData.lastName}"
    val statementAsString = s"INSERT INTO users VALUE '$userName';"
    if (statementAsString.contains("DELETE"))
      Failure(new Exception("User attempted to enter DELETE statement!"))
    else Success(statementAsString)
  }

  private def executeSqlQuery(statementAsString: String): Try[Boolean] = Try {
    database.withConnection { implicit c =>
      SQL(statementAsString).execute()
    }
  }
}
