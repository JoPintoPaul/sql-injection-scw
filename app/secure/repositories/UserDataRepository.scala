package secure.repositories

import javax.inject.Inject

import anorm.{Row, SQL, SimpleSql}
import secure.models.UserData

import scala.util.Try

class UserDataRepository @Inject()(database: play.api.db.Database) {

  def save(userData: UserData): Try[Boolean] = {
    val parametrizedQuery = createParametrizedQuery(userData)
    executeSqlQuery(parametrizedQuery)
  }

  private def createParametrizedQuery(userData: UserData): SimpleSql[Row] = {
    val userName = s"${userData.firstName} ${userData.lastName}"
    SQL("INSERT INTO users VALUE {userName}")
      .on('userName -> userName)
  }

  private def executeSqlQuery(parametrizedQuery: SimpleSql[Row]): Try[Boolean] =
    Try {
      database.withConnection { implicit c =>
        parametrizedQuery.execute()
      }
    }
}
