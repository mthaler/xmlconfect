import ReleaseTransformations._

lazy val xmlConfectSettings = Seq(
  organization := "com.mthaler",
  scalaVersion := "2.11.7",
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "org.scala-lang.modules" %% "scala-xml" % "1.0.4",
    "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
  ),
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-feature"
  ),
  licenses += ("Apache License, Version 2.0", url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  homepage := Some(url("http://github.com/mthaler/xmlconfect")),

  // release stuff
  credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := Function.const(false),
  publishTo <<= version { v =>
    val nexus = "https://oss.sonatype.org/"
    if (v.trim.endsWith("SNAPSHOT"))
      Some("Snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("Releases" at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra :=
    <scm>
      <url>git@github.com:mthaler/xmlconfect.git</url>
      <connection>scm:git:git@github.com:mthaler/xmlconfect.git</connection>
    </scm>
    <developers>
      <developer>
        <id>m_t</id>
        <name>Michael Thaler</name>
        <url>http://github.com/mthaler/</url>
      </developer>
    </developers>
  ,
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    ReleaseStep(action = Command.process("package", _)),
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    ReleaseStep(action = Command.process("publishSigned", _)),
    setNextVersion,
    commitNextVersion,
    ReleaseStep(action = Command.process("sonatypeReleaseAll", _)),
    pushChanges))

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false)

lazy val root = project.in(file("."))
  .aggregate(core)
  .settings(name := "root")
  .settings(xmlConfectSettings: _*)
  .settings(noPublish: _*)

lazy val core = project.in(file("."))
  .settings(name := "xmlconfect")
  .settings(xmlConfectSettings: _*)
  .settings(Boilerplate.settings: _*) // generate boilerplate
