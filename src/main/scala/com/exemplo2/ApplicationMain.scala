package com.exemplo2

import akka.actor.{ActorRef, ActorSystem, Props}
import com.common.{Notificacao, TipoNotificacao}
import com.exemplo2.ColetorNotificacoesActor.{ContarNotificacoes, EnviarNotificacao}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object ApplicationMain extends App {

  implicit val timeout = Timeout(5 seconds)

	val system: ActorSystem = ActorSystem("NotificacaoActorSystem")

  val coletorNotificacoesActor: ActorRef = system.actorOf(Props[ColetorNotificacoesActor], "ColetorNotificacaoActor")

  coletorNotificacoesActor ! EnviarNotificacao(Notificacao(1, "teste@gmail.com", "Pagamento realizado no valor de R$ 100,00", TipoNotificacao.PAGAMENTO))
  coletorNotificacoesActor ! EnviarNotificacao(Notificacao(2, "teste@gmail.com", "Saque realizado no valor de R$ 50,00", TipoNotificacao.SAQUE))
  coletorNotificacoesActor ! EnviarNotificacao(Notificacao(3, "teste@gmail.com", "Transferencia realizada no valor de R$ 9.000,00", TipoNotificacao.TRANSFERENCIA))

  val future = coletorNotificacoesActor ? ContarNotificacoes

  future.map(x => println(s"Existem $x notificações no coletor"))

  system.terminate()
}