import verizon.build._
import com.typesafe.sbt.SbtSite.SiteKeys._
import com.typesafe.sbt.SbtGhPages.GhPagesKeys._

common.settings

docs.settings("git@github.oncue.verizon.net:stew/consul.git")
