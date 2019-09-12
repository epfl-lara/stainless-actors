ThisBuild / version      := "0.1.0"
ThisBuild / scalaVersion := "2.12.9"
ThisBuild / organization := "ch.epfl.lara"

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.5.21",
  ),
)

lazy val noPublishSettings = Seq(
  skip in publish := true,
  publish         := {},
  publishM2       := {},
  publishLocal    := {},
)

lazy val `actors` = project
  .in(file("."))
  .enablePlugins(StainlessPlugin)
  .settings(
    name := "stainless-actors",
    commonSettings,
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

// lazy val `replicated-counter` = project
//   .in(file("examples/replicated-counter"))
//   .enablePlugins(StainlessPlugin)
//   .settings(commonSettings, actorsProjectSettings)
//   .settings(
//     name := "stainless-actors-counter3",
//     Compile / mainClass := Some("ReplicatedCounter"),
//   )

lazy val `kvs` = project
  .in(file("examples/kvs"))
  .enablePlugins(StainlessPlugin)
  .settings(commonSettings, actorsProjectSettings)
  .settings(
    name := "stainless-actors-kvs",
    Compile / mainClass := Some("KVS"),
  )

