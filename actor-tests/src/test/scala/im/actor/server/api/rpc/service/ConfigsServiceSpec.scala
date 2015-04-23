package im.actor.server.api.rpc.service

import im.actor.api.rpc._
import im.actor.api.rpc.configs._
import im.actor.api.rpc.misc.ResponseSeq
import im.actor.server.api.rpc.RpcApiService
import im.actor.server.api.rpc.service.configs.ConfigsServiceImpl
import im.actor.server.presences.PresenceManager
import im.actor.server.push.{ SeqUpdatesManager, WeakUpdatesManager }
import im.actor.server.session.Session
import im.actor.server.social.SocialManager

class ConfigsServiceSpec extends BaseServiceSuite {
  behavior of "Configs Service"

  it should "save parameter even if it already exists" in e1()

  it should "get parameters" in e2()

  val rpcApiService = system.actorOf(RpcApiService.props())
  implicit val seqUpdManagerRegion = SeqUpdatesManager.startRegion()
  implicit val weakUpdManagerRegion = WeakUpdatesManager.startRegion()
  implicit val presenceManagerRegion = PresenceManager.startRegion()
  implicit val socialManagerRegion = SocialManager.startRegion()
  implicit val sessionRegion = Session.startRegion(Some(Session.props(rpcApiService)))
  implicit val authService = buildAuthService()

  val service = new ConfigsServiceImpl

  val (user, _, _) = createUser()
  val authId = createAuthId()
  val sessionId = createSessionId()

  implicit val clientData = ClientData(authId, sessionId, Some(user.id))

  def e1() = {
    whenReady(service.handleEditParameter("par1", "val1")) { resp =>
      resp should matchPattern {
        case Ok(ResponseSeq(_, _)) =>
      }
    }

    whenReady(service.handleEditParameter("par1", "val2")) { resp =>
      resp should matchPattern {
        case Ok(ResponseSeq(_, _)) =>
      }
    }
  }

  def e2() = {
    whenReady(service.handleGetParameters()) { resp =>
      resp should matchPattern {
        case Ok(ResponseGetParameters(Vector(Parameter("par1", "val2")))) =>
      }
    }
  }
}
