package com.exemplo4

import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, ActorLogging, OneForOneStrategy, PoisonPill, Props, Terminated}
import akka.routing.RoundRobinPool
import com.common.Notificacao
import com.common.TipoNotificacao._
import com.exemplo4.ColetorNotificacoesActor.{ContarNotificacoes, EnviarNotificacao}
import com.exemplo4.NotificacaoPagamentoActor.NotificarPagamento

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration._

/**
  * Created by otavio on 16/05/16.
  */
class ColetorNotificacoesActor extends Actor {

  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case _: ArithmeticException => Resume
    case _: NullPointerException => Restart
    case _: IllegalArgumentException => Stop
    case _: Exception => Escalate
  }

  var notificacoes = new ListBuffer[Notificacao]()

  val notificacaoPagamentoActor = context.actorOf(Props[NotificacaoPagamentoActor], "NotificacaoPagamentoActor")

  context.watch(notificacaoPagamentoActor)

  def receive = {
    case EnviarNotificacao(notificacao) => {
      notificacoes += notificacao

      notificacao.tipo match {
        case PAGAMENTO => notificacaoPagamentoActor ! NotificarPagamento(notificacao)
        case SAQUE => notificacaoPagamentoActor ! NotificarPagamento(notificacao)
        case TRANSFERENCIA => notificacaoPagamentoActor ! NotificarPagamento(notificacao)
      }
    }
    case ContarNotificacoes => sender ! notificacoes.size
    case Terminated(ator) => println(s"############### Ator ${ator.path} finalizado ##############")
  }

}

object ColetorNotificacoesActor {

  case object ContarNotificacoes
  case class EnviarNotificacao(notificacao: Notificacao)

}
