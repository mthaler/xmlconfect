package com.mthaler.xmlstream

object Classes {

  /**
    * Creates an instance of a case class using default arguments
    *
    * For the case class
    *
    * case class Person(name: String = "Albert Einstein", age: Int = 42)
    *
    * this would return Person("Albert Einstein", 42)
    *
    * @tparam A case class type
    * @return List of default arguments
    */
  def newDefault[A](implicit t: reflect.ClassTag[A]): A = {
    val c = t.runtimeClass.asInstanceOf[Class[A]]
    newDefault(c)
  }

  /**
    * Creates an instance of a case class using default arguments
    *
    * For the case class
    *
    * case class Person(name: String = "Albert Einstein", age: Int = 42)
    *
    * this would return Person("Albert Einstein", 42)
    *
    * @param c case class
    * @return List of default arguments
    */
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

  /**
    * Gets the default values of a case class
    *
    * For the case class
    *
    * case class Person(name: String = "Albert Einstein", age: Int = 42)
    *
    * this would return List("Albert Einstein", 42)
    *
    * @param c case class
    * @return List of default arguments
    */
  def defaultArgs(c: Class[_]): List[Any] = {
    import reflect.runtime.{universe => ru, currentMirror => cm}
    val clazz  = cm.classSymbol(c)
    val mod    = clazz.companion.asModule
    val im     = cm.reflect(cm.reflectModule(mod).instance)
    val ts     = im.symbol.typeSignature
    val mApply = ts.member(ru.TermName("apply")).asMethod
    val syms   = mApply.paramLists.flatten
    syms.zipWithIndex.map { case (p, i) =>
      val mDef = ts.member(ru.TermName(s"apply$$default$$${i+1}")).asMethod
      im.reflectMethod(mDef)()
    }
  }
}
