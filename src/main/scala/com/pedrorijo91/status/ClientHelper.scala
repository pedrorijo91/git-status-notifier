package com.pedrorijo91.status

import com.ning.http.client.AsyncHttpClient
import play.api.libs.ws.WSResponse
import play.api.libs.ws.ning.NingWSClient
import scala.util.Try

object ClientHelper {

  def withClient(block: NingWSClient => WSResponse): Try[WSResponse] = {

    val client = new NingWSClient(new AsyncHttpClient().getConfig)

    val result = Try(block(client))
    client.close()
    result
  }
}
