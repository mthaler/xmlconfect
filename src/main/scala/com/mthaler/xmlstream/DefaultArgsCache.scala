package com.mthaler.xmlstream

import scala.util.Try

object DefaultArgsCache {

  @volatile
  private var cache = Map.empty[Class[_], IndexedSeq[Any]]

  def get(clazz: Class[_]): IndexedSeq[Any] = {
    cache.get(clazz) match {
      case Some(args) => args
      case None =>
        val defaultArgs = Try { Classes.defaultArgs(clazz).toIndexedSeq } getOrElse(IndexedSeq.empty)
        cache += clazz -> defaultArgs
        defaultArgs
    }
  }
}
