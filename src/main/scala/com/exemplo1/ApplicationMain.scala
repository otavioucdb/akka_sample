package com.exemplo1

import akka.actor.{ActorRef, ActorSystem, Props}
import com.exemplo1.ColetorNotificacoesActor.IMPRIMIR

object ApplicationMain extends App {

	val system: ActorSystem = ActorSystem("NotificacaoActorSystem")

  val coletorNotificacoesActor: ActorRef = system.actorOf(Props[ColetorNotificacoesActor], "ColetorNotificacaoActor")

  coletorNotificacoesActor ! IMPRIMIR

  system.terminate()
}