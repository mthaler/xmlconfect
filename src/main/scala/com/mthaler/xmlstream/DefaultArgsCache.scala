package com.mthaler.xmlstream

import scala.util.Try

/**
  * Cache for default args
  */
object DefaultArgsCache {

  @volatile
  private var cache = Map.empty[Class[_], IndexedSeq[Any]]

  /**
    * Gets default args for a given class
    *
    * If default args for the given class are not found, default args of the given class are determined using reflection
    * and the result will be added to the cache
    *
    * @param clazz
    * @return
    */
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
