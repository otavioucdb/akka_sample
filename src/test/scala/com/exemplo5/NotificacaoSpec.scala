package com.exemplo5

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import com.common.{Notificacao, TipoNotificacao}
import com.exemplo5.ColetorNotificacoesActor.{ContarNotificacoes, EnviarNotificacao}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class NotificacaoSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {
 
  def this() = this(ActorSystem("NotificacaoActorSystem"))
 
  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }
 
  "Como coletor notificacoes " must {
    "receber uma mensagem e armazenar na lista de notificacoes" in {
      val coletorAtor = TestActorRef[ColetorNotificacoesActor]
      coletorAtor ! EnviarNotificacao(Notificacao(1, "teste@gmail.com", "Pagamento realizado no valor de R$ 100,00", TipoNotificacao.PAGAMENTO))
      coletorAtor ! EnviarNotificacao(Notificacao(2, "teste@gmail.com", "Saque realizado no valor de R$ 50,00", TipoNotificacao.SAQUE))
      coletorAtor ! ContarNotificacoes

      coletorAtor.underlyingActor.notificacoes.size shouldEqual 2
    }
  }

  "Como coletor notificacoes " must {
    "contar a quantidade de notificacoes e responder" in {
      val coletorAtor = TestActorRef[ColetorNotificacoesActor]
      coletorAtor ! ContarNotificacoes

      expectMsg(2)
    }
  }

}
