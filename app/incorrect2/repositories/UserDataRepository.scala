package incorrect2.repositories

import javax.inject.Inject

import anorm.SQL
import incorrect2.models.UserData

import scala.util.{Failure, Success, Try}

class UserDataRepository @Inject()(database: play.api.db.Database) {

  private val sqlInjectionStrings = List(
    "DROP TABLE",
    "INSERT INTO",
    "DELETE FROM",
    "UPDATE"
  )

  def save(userData: UserData): Try[Boolean] = {
    validateUserData(userData) flatMap executeSqlQuery
  }

  private def validateUserData(userData: UserData): Try[String] = {
    val userName = s"${userData.firstName} ${userData.lastName}"
    val statementAsString = s"INSERT INTO users VALUE '$userName';"
    if (sqlInjectionStrings exists statementAsString.contains)
      Failure(new Exception(
        s"User attempted to enter SQL within their input, statement would have been $statementAsString!"))
    else Success(statementAsString)
  }

  private def executeSqlQuery(statementAsString: String): Try[Boolean] = Try {
    database.withConnection { implicit c =>
      SQL(statementAsString).execute()
    }
  }
}
