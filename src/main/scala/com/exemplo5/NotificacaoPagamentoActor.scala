package com.exemplo5

import akka.actor.{Actor, ActorLogging, Props}
import com.common.Notificacao
import com.exemplo5.DepositoNotificacoesActor.ConfirmarEnvio
import com.exemplo5.NotificacaoPagamentoActor.NotificarPagamento

/**
  * Created by otavio on 18/05/16.
  */
class NotificacaoPagamentoActor extends Actor with ActorLogging {

  override def preStart() = log.debug(s"++++++++++++++ Iniciando Notificação de Actor: ${self.path} ++++++++++++++++++")

  val depositoNotificacoesActor = context.actorOf(Props[DepositoNotificacoesActor], "DepositoNotificacoesActor")

  def receive = {
    case NotificarPagamento(notificacao) => {
      Thread.sleep(100)

      if(notificacao.email.isEmpty) throw new IllegalArgumentException
      println(s"DIM DOM: Ator ${sender.path} enviou uma notificação: ${notificacao.mensagem}")

      depositoNotificacoesActor ! ConfirmarEnvio(notificacao)
    }
  }

}

object NotificacaoPagamentoActor {
  case class NotificarPagamento(notificacaoPagamento: Notificacao)
}
