package lab1
import java.io.File
import java.io.BufferedReader




fun gradoSeparacion(amigo1: String, amigo2: String, grafo: ListasAdyacenciaGrafo<String>): Int {
    if (amigo1 == amigo2) return 0
    val queue = ArrayDeque<Pair<String, Int>>()
    queue.addLast(Pair(amigo1, 0))
    val visitados = mutableSetOf<String>()
    visitados.add(amigo1)

    while (queue.isNotEmpty()) {
        val primerElemento = queue.removeFirst()
        val actual = primerElemento.first
        val distancia = primerElemento.second

        for (vecino in grafo.obtenerArcosSalida(actual)) {
            
            if (vecino == amigo2) {
                return distancia + 1 
            }

            if (!visitados.contains(vecino)){
                visitados.add(vecino) 
                queue.addLast(Pair(vecino, distancia + 1))
            }
        }
    }

    return -1 
}








fun generarGrafo(nombreArchivo: String): ListasAdyacenciaGrafo<String> {
  val grafo = ListasAdyacenciaGrafo<String>()
  val file = File(nombreArchivo)
  val reader = BufferedReader(file.reader())
  var line: String? = reader.readLine()
  while (line != null) {
    val amigos = line.split(" ")
    val amigo1 = amigos[0].trim()
    val amigo2 = amigos[1].trim()
    grafo.agregarVertice(amigo1)
    grafo.agregarVertice(amigo2)
    grafo.conectar(amigo1, amigo2)
    line = reader.readLine()
  }
  reader.close()
  return grafo
}


fun main(args: Array<String>) {
  val grafo = generarGrafo("input.txt")
  print(gradoSeparacion(args[0], args[1], grafo))


}