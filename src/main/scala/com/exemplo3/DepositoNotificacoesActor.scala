package com.exemplo3

import akka.actor.{Actor, ActorLogging}
import com.common.Notificacao
import com.exemplo3.DepositoNotificacoesActor.ConfirmarEnvio

import scala.collection.mutable.ListBuffer

/**
  * Created by otavio on 19/05/16.
  */
class DepositoNotificacoesActor extends Actor with ActorLogging {

  var notificacoesConfirmadas = new ListBuffer[Notificacao]()

  def receive = {
    case ConfirmarEnvio(notificacao) => {
      notificacoesConfirmadas += notificacao
      log.debug("Opa blz, confirmou a notificacao: " + notificacao)
    }

  }
}

object DepositoNotificacoesActor {
  case class ConfirmarEnvio(notificacao: Notificacao)
}
