package com.geeksville.util

object ThreadTools {
  /**
   * Generate runnables
   * (Note if you findyourself tempted to change handler to a => Any you are probably making a mistake that won't work well with reference arguments
   */
  implicit def toRunable(handler: () => Unit) = new Runnable {
    override def run() = handler()
  }

  /// Run a bit of code in a background thread - the caller will need to call start
  def createDaemon(name: String)(block: () => Unit): Thread = {
    val t = new Thread(block, name)

    t.setDaemon(true)
    t
  }

  /// Ignore exceptions (with a warning).  Usage: catchIgnore { some code }
  def catchIgnore[ResType](block: => ResType) =
    {
      try {
        block
      } catch {
        case ex: Exception => println("Ignoring " + ex)
      }
    }

  /// Ignore exceptions silently.  Usage: catchSilently { some code }
  def catchSilently[ResType](block: => ResType) =
    {
      try {
        block
      } catch {
        case ex: Exception =>
      }
    }

  /// Install an uncaught exception handler
  def setUncaughtExceptionHandler(handler: (Thread, Throwable) => Unit) {
    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
      def uncaughtException(t: Thread, e: Throwable) = handler(t, e)
      //System.out.println("*****Yeah, Caught the Exception*****");
      // e.printStackTrace(); // you can use e.printStackTrace ( printstream ps )
    })
  }
}