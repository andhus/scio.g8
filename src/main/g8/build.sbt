import sbt._
import Keys._
import com.typesafe.sbt.SbtGit.GitKeys._

val scioVersion = "0.3.1"
val scalaMacrosVersion = "2.1.0"

lazy val commonSettings = Defaults.coreDefaultSettings ++ packAutoSettings ++ Seq(
  organization          := "example",
  // Semantic versioning http://semver.org/
  version               := "0.1.0-SNAPSHOT",
  scalaVersion          := "2.11.11",
  scalacOptions         ++= Seq("-target:jvm-1.8",
                                "-deprecation",
                                "-feature",
                                "-unchecked"),
  javacOptions          ++= Seq("-source", "1.8",
                                "-target", "1.8")
)

lazy val paradiseDependency =
  "org.scalamacros" % "paradise" % scalaMacrosVersion cross CrossVersion.full
lazy val macroSettings = Seq(
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  addCompilerPlugin(paradiseDependency)
)

lazy val noPublishSettings = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false
)

lazy val root: Project = Project(
  """$name;format="lower,hyphen"$""",
  file("."),
  settings = commonSettings ++ macroSettings ++ noPublishSettings ++ Seq(
    description := "$name$",
    libraryDependencies ++= Seq(
      "com.spotify" %% "scio-core" % scioVersion,
      "com.spotify" %% "scio-test" % scioVersion % "test"
    )
  )
)
