package com.exemplo3

import akka.actor.{Actor, Props}
import com.common.Notificacao
import com.exemplo3.DepositoNotificacoesActor.ConfirmarEnvio
import com.exemplo3.NotificacaoPagamentoActor.NotificarPagamento

/**
  * Created by otavio on 18/05/16.
  */
class NotificacaoPagamentoActor extends Actor {

  val depositoNotificacoesActor = context.actorOf(Props[DepositoNotificacoesActor], "DepositoNotificacoesActor")

  def receive = {
    case NotificarPagamento(notificacao) => {
      Thread.sleep(100)
      println(s"DIM DOM: Você tem uma notificação: ${notificacao.mensagem}")

      depositoNotificacoesActor ! ConfirmarEnvio(notificacao)
    }
  }

}

object NotificacaoPagamentoActor {
  case class NotificarPagamento(notificacaoPagamento: Notificacao)
}
