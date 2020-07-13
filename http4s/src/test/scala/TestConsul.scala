import java.util.concurrent.Executors

import cats.effect.{Blocker, ContextShift, IO}
import helm.http4s.Http4sConsulClient
import helm.{ConsulOp, KVGetResult, QueryResponse}
import org.http4s.Uri
import org.http4s.client.{Client, JavaNetClientBuilder}

class TestConsul() {

  def saveString(id: String, input: String) = {
    val saveConfig = helm.run(interpreter,
      ConsulOp.kvSet("saved/" + id, input.getBytes))
    saveConfig.unsafeRunSync()
    println("String " + input + " saved")
  }

  def getStringByID(id: String): String = {
    val g: IO[QueryResponse[List[KVGetResult]]] = helm.run(
      interpreter,
      ConsulOp.kvGet("saved/" + id, Some(true), None, None, None, None))
    val attemptedRead = g.unsafeRunSync()
    val kvGetResult: KVGetResult = attemptedRead.value.head
    val value = new String(java.util.Base64.getDecoder.decode(kvGetResult.value.get))
    println("String " + value + " found")
    value

  }

  private def interpreter: Http4sConsulClient[IO] = new Http4sConsulClient(baseUrl, client)

  private def client: Client[IO] = {
    val blockingPool = Executors.newFixedThreadPool(5)
    val blocker = Blocker.liftExecutorService(blockingPool)
    implicit val evidence: ContextShift[IO] = IO.contextShift(scala.concurrent.ExecutionContext.global)
    JavaNetClientBuilder[IO](blocker).create
  }

  private def baseUrl: Uri = Uri.uri("http://localhost:8500")


}
