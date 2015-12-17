package com.mthaler.xmlstream

object Classes {

  def newDefault[A](implicit t: reflect.ClassTag[A]): A = {
    import reflect.runtime.{universe => ru, currentMirror => cm}

    val c = t.runtimeClass.asInstanceOf[Class[A]]
    newDefault(c)
  }

  def newDefault[A](c: Class[A]): A = {
    import reflect.runtime.{universe => ru, currentMirror => cm}

    val clazz  = cm.classSymbol(c)
    val mod    = clazz.companion.asModule
    val im     = cm.reflect(cm.reflectModule(mod).instance)
    val ts     = im.symbol.typeSignature
    val mApply = ts.member(ru.TermName("apply")).asMethod
    val syms   = mApply.paramLists.flatten
    val args   = syms.zipWithIndex.map { case (p, i) =>
      val mDef = ts.member(ru.TermName(s"apply$$default$$${i+1}")).asMethod
      im.reflectMethod(mDef)()
    }
    im.reflectMethod(mApply)(args: _*).asInstanceOf[A]
  }
}
