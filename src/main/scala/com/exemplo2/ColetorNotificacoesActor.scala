package com.exemplo2

import akka.actor.Actor
import com.common.Notificacao
import com.exemplo2.ColetorNotificacoesActor.{ContarNotificacoes, EnviarNotificacao}

import scala.collection.mutable.ListBuffer

/**
  * Created by otavio on 16/05/16.
  */
class ColetorNotificacoesActor extends Actor {

  var notificacoes = new ListBuffer[Notificacao]()

  def receive = {
    case EnviarNotificacao(notificacao) => notificacoes += notificacao
    case ContarNotificacoes => sender ! notificacoes.size
  }

}

object ColetorNotificacoesActor {

  case object ContarNotificacoes
  case class EnviarNotificacao(notificacao: Notificacao)

}
