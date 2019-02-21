
import com.hexagonkt.http.httpDate
import com.hexagonkt.http.server.Server
import com.hexagonkt.http.server.ServerPort
import com.hexagonkt.http.server.jetty.JettyServletAdapter
import com.hexagonkt.injection.InjectionManager.bindObject

/**
 * Service server. It is created lazily to allow ServerPort injection (set up in main).
 */
val server: Server by lazy {
    Server {
        before {
            response.setHeader("Server", "Servlet/3.1")
            response.setHeader("Transfer-Encoding", "chunked")
            response.setHeader("Date", httpDate())
        }

        get("/text") { ok("Hello, World!", "text/plain") }
    }
}

/**
 * Start the service from the command line.
 */
fun main() {
    bindObject<ServerPort>(JettyServletAdapter()) // Bind Jetty server to HTTP Server Port
    server.start()
}

