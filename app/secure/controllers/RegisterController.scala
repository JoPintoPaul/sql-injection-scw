package secure.controllers

import javax.inject.Inject

import secure.models.UserData
import secure.repositories.UserDataRepository
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}

import scala.util.{Failure, Success}

class RegisterController @Inject()(userDataRepository: UserDataRepository,
                                   val messagesApi: MessagesApi)
    extends Controller
    with I18nSupport {

  val userForm = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText
    )(UserData.apply)(UserData.unapply)
  )

  def index = Action {
    Ok(secure.views.html.register(userForm))
  }

  def register = Action { implicit request =>
    userForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(secure.views.html.register(formWithErrors))
      },
      contact => {
        userDataRepository.save(contact) match {
          case Success(_) => Ok(secure.views.html.success())
          case Failure(error) =>
            Logger.error(s"UserData could not be saved: ${error.getMessage}")
            val formWithSaveError =
              userForm.withError(
                "lastName",
                "Sorry, your details could not be registered, please try again")
            BadRequest(secure.views.html.register(formWithSaveError))
        }
      }
    )
  }
}
