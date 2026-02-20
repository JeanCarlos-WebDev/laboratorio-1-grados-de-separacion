package lab1


class ListasAdyacenciaGrafo<T> : Grafo<T>{ 
    private val vertexMap = mutableMapOf< T, Int >()
    private val indexToVertex = mutableListOf< T >()
    private val adj = mutableListOf<MutableSet<Int>>() 
    

    var size: Int = 0
    private set


    /* agregarVertice: 
        Recibe un objeto de tipo T y lo agrega al conjunto de vértices del grafo. 
        Retorna true si el vértice fue agregado con éxito y false si el vértice ya existía. */
    override fun agregarVertice(v: T): Boolean{
        if (v in vertexMap) return false
        vertexMap[v] = size
        indexToVertex.add(v)
        adj.add(mutableSetOf())
        size++
        return true    
    }

    /* conectar
        Crea un arco dirigido desde el vértice desde hacia el vértice hasta. 
        Retorna true si la conexión se realizó con  ́exito. 
        Si alguno de los vértices no existe, retorna false.*/
    override fun conectar(desde: T, hasta: T): Boolean{
        val desdeId = vertexMap[desde]?: return false
        val hastaId = vertexMap[hasta]?: return false
        return adj[desdeId].add(hastaId) && adj[hastaId].add(desdeId)
    }

    /* Contiene
        Retorna true si el vértice v pertenece al conjunto de vértices del grafo. */
    override fun contiene(v: T): Boolean{
        if(v in vertexMap) return true
        return false
    }

    /* obtenerArcosSalida
        Retorna una lista con todos los vértices u tales que existe un arco (v, u). 
        Si el vértice no existe, retorna una lista vacía.*/
    override fun obtenerArcosSalida(v: T): List<T> {
        val i = vertexMap[v]?: return emptyList()
        return adj[i].map{indexToVertex[it]} 
    }

    /* obtenerArcosEntrada
        Retorna una lista con todos los vértices u tales que existe un arco (u, v).*/
    override fun obtenerArcosEntrada(v: T): List<T>{
        val list: MutableList<T> = mutableListOf()
        val id = vertexMap[v] ?: return emptyList()
        for (i in 0 until size){ 
            if (id in adj[i]){
                list.add(indexToVertex[i])
            }
        }
        return list
    }

    /* eliminarVertice:
        Elimina el vértice v y todos los arcos asociados (tanto de entrada como de salida). 
        Retorna true si la eliminación fue exitosa.*/
    override fun eliminarVertice(v: T): Boolean{
        val id = vertexMap[v] ?: return false
            val lastIndex = size - 1
            val lastVertex = indexToVertex[lastIndex]

            for (set in adj) {
                set.remove(id)
            }

            if (id != lastIndex) {
                indexToVertex[id] = lastVertex
                adj[id] = adj[lastIndex]

                vertexMap[lastVertex] = id

                for (set in adj) {
                    if (lastIndex in set) {
                        set.remove(lastIndex)
                        set.add(id)
                    }
                }
            }

            vertexMap.remove(v)
            indexToVertex.removeAt(lastIndex)
            adj.removeAt(lastIndex)
            size--

            return true
        }

    /* tamano;
        Retorna la cantidad de vértices en el grafo (∣V∣). 
    */
    override fun tamano(): Int{
        return vertexMap.size
    }

    //subgrafo
    override fun subgrafo(vertices: Collection<T>): Grafo<T>{
        val subGr = ListasAdyacenciaGrafo<T>()
        val setVer = mutableSetOf<Int>()
        for (v in vertices){
            if (v in vertexMap){
                val id = vertexMap[v]?: continue
                subGr.agregarVertice(v)
                setVer.add(id)
            }
        }
        for (i in setVer){
            for (j in adj[i]){
                if (j in setVer){
                    val desde = indexToVertex[i]
                    val hasta = indexToVertex[j]
                    subGr.conectar(desde, hasta)
                }
            }
        }
        return subGr
        }
       
    }
