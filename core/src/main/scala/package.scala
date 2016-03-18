import argonaut._, Argonaut._
import scalaz.concurrent.Task
import scalaz.syntax.std.option._

package object consul {
  type Err = String // YOLO
  type Key = String

  private val base64Decoder = java.util.Base64.getDecoder
  private val base64StringDecoder: DecodeJson[String] =
    DecodeJson.optionDecoder(json =>
      json.string.flatMap(s => DecodeJson.tryTo(new String(base64Decoder.decode(s), "utf-8")))
    , "base 64 string")

  private[consul] implicit val KvResponseDecoder: DecodeJson[KvResponse] =
    DecodeJson.jdecode1L(KvResponse.apply)("Value")(base64StringDecoder)

  private[consul] implicit val KvResponsesDecoder: DecodeJson[KvResponses] =
    implicitly[DecodeJson[List[KvResponse]]].map(KvResponses)

  private[consul] def keyValue(key: Key, responses: KvResponses): Task[KvResponse] =
    responses.values.headOption.cata(Task.now, Task.fail(new RuntimeException(s"no consul value for key $key")))

}