package com.exemplo3

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.common.{LeitorArquivo, Notificacao, TipoNotificacao}
import com.exemplo3.ColetorNotificacoesActor.{ContarNotificacoes, EnviarNotificacao}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

object ApplicationMain extends App {

  implicit val timeout = Timeout(5 seconds)

	val system: ActorSystem = ActorSystem("NotificacaoActorSystem")

  val coletorNotificacoesActor: ActorRef = system.actorOf(Props[ColetorNotificacoesActor], "ColetorNotificacaoActor")

  var id = 0l
  LeitorArquivo.getLinhas foreach{
    linha => {
      id += 1l
      val array = linha.split(";")
      coletorNotificacoesActor ! EnviarNotificacao(Notificacao(id, array(0), array(1), TipoNotificacao.valueOf(array(2))))
    }
  }

  val numeroNotificacoes: Future[Any] = coletorNotificacoesActor ? ContarNotificacoes

  numeroNotificacoes.map(x => println(s"Existem $x notificações no coletor"))

  Thread.sleep(10000)
  system.terminate()

}