package com.geeksville.mavlink

import org.mavlink.messages.MAVLinkMessage
import com.geeksville.akka.EventStream
import com.geeksville.akka.InstrumentedActor
import com.geeksville.logback.Logging

object MavlinkEventBus extends EventStream with Logging {

  def subscribe(a: InstrumentedActor, sysId: Int) = {
    def interested(ev: Any) = {
      val r = ev match {
        case m: MAVLinkMessage => m.sysId == sysId
        case _ => false
      }

      //logger.debug("%s: interest in %s (myId=%d) is %s".format(a, ev, sysId, r))
      r
    }

    super.subscribe(a, interested _)
  }
}

