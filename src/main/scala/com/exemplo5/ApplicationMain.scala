package com.exemplo5

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.common.{LeitorArquivo, Notificacao, TipoNotificacao}
import com.exemplo5.ColetorNotificacoesActor.{ContarNotificacoes, EnviarNotificacao}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

object ApplicationMain extends App {

  implicit val timeout = Timeout(5 seconds)

	val system: ActorSystem = ActorSystem("NotificacaoActorSystem")

  val coletorNotificacoesActor: ActorRef = system.actorOf(Props[ColetorNotificacoesActor], "ColetorNotificacaoActor")

  LeitorArquivo.getLinhas.zipWithIndex.foreach {
    case(linha, index) => {

      val array = linha.split(";")
      val email = array(0)
      val msg = array(1)
      val tipo = TipoNotificacao.valueOf(array(2))

      coletorNotificacoesActor ! EnviarNotificacao(Notificacao(index, email, msg, tipo))
    }
  }

  val numeroNotificacoes: Future[Any] = coletorNotificacoesActor ? ContarNotificacoes

  numeroNotificacoes.map(x => println(s"Existem $x notificações no coletor"))

  Thread.sleep(10000)
  system.terminate()

}