import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) {

  val slf4s = "com.weiglewilczek.slf4s" %% "slf4s" % "1.0.0"
  val slf4j = "org.slf4j" % "slf4j-simple" % "1.6.1"
  
  ///// Vaadin \\\\\
  val vaadin = "com.vaadin" % "vaadin" % "6.4.3"

  }
