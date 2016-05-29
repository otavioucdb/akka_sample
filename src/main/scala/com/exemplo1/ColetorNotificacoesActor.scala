package com.exemplo1

import akka.actor.Actor
import com.exemplo1.ColetorNotificacoesActor.IMPRIMIR

/**
  * Created by otavio on 16/05/16.
  */
class ColetorNotificacoesActor extends Actor {

  def receive = {
    case IMPRIMIR => {
      println("Primeira chamada Ator")
    }
  }

}

object ColetorNotificacoesActor {

  case object IMPRIMIR

}
