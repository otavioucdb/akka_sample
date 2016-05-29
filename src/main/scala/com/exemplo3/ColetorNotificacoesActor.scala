package com.exemplo3

import akka.actor.{Actor, ActorLogging, Props}
import com.common.Notificacao
import com.common.TipoNotificacao._
import com.exemplo3.ColetorNotificacoesActor.{ContarNotificacoes, EnviarNotificacao}
import com.exemplo3.NotificacaoPagamentoActor.NotificarPagamento

import scala.collection.mutable.ListBuffer

/**
  * Created by otavio on 16/05/16.
  */
class ColetorNotificacoesActor extends Actor with ActorLogging {

  override def preStart() = log.debug("Iniciando Coletor Actor")

  override def preRestart(reason: Throwable, message: Option[Any]) = log.error(reason, "Restart [{}] processando [{}]", reason.getMessage, message.getOrElse(""))

  var notificacoes = new ListBuffer[Notificacao]()

  val notificacaoPagamentoActor = context.actorOf(Props[NotificacaoPagamentoActor], "NotificacaoPagamentoActor")

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
  }

}

object ColetorNotificacoesActor {

  case object ContarNotificacoes
  case class EnviarNotificacao(notificacao: Notificacao)

}
