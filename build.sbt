lazy val commonSettings = Seq(
  scalaVersion := "2.12.13",
  organization := "ch.epfl.lara",
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.5.21",
    "com.typesafe.akka" %% "akka-slf4j" % "2.5.21",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
  ),
  fork := true,
  licenses := Seq(
    "Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.html")
  ),
)

lazy val publishSettings = Seq(
  bintrayOrganization := Some("epfl-lara"),
  bintrayRepository   := "maven",
)

lazy val noPublishSettings = Seq(
  skip in publish := true,
  publish         := {},
  publishM2       := {},
  publishLocal    := {},
)

lazy val `actors` = project
  .in(file("."))
  .enablePlugins(StainlessPlugin, GitVersioning)
  .settings(
    name := "stainless-actors",
    git.useGitDescribe := true,
    commonSettings,
    publishSettings,
  )

lazy val actorsProjectSettings = Seq(
  Compile / unmanagedSources ++= (actors / Compile / unmanagedSources).value,
) ++ noPublishSettings

lazy val `counter` = project
  .in(file("examples/counter"))
  .enablePlugins(StainlessPlugin)
  .settings(commonSettings, actorsProjectSettings)
  .settings(
    name := "stainless-actors-counter",
    Compile / mainClass := Some("Counter"),
  )

lazy val `leader-election` = project
  .in(file("examples/leader-election"))
  .enablePlugins(StainlessPlugin)
  .settings(commonSettings, actorsProjectSettings)
  .settings(
    name := "stainless-actors-leader-election",
    Compile / mainClass := Some("LeaderElection"),
  )

lazy val `kvs` = project
  .in(file("examples/kvs"))
  .enablePlugins(StainlessPlugin)
  .settings(commonSettings, actorsProjectSettings)
  .settings(
    name := "stainless-actors-kvs",
    Compile / mainClass := Some("KVS"),
  )

