package com.common

import java.io.InputStream

/**
  * Created by otavio on 19/05/16.
  */
object LeitorArquivo {


  def getLinhas = {
    val stream : InputStream = getClass.getResourceAsStream("/notificacoes.txt")
    scala.io.Source.fromInputStream( stream ).getLines.seq
  }

}
