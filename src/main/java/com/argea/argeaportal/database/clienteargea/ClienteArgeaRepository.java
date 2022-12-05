package com.argea.argeaportal.database.clienteargea;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClienteArgeaRepository extends CrudRepository<ClienteArgea, Integer> {

    /**
     * esempio di metodo in cui la libreria inferisce dal nome metodo la query(intellisense funziona)
     * @param descrizione
     * @return lista clienti argea che matchano
     */
    List<ClienteArgea> findAllByDescrizioneLikeIgnoreCase(String descrizione);
    List<ClienteArgea> findAllByDescrizioneIgnoreCase(String descrizione);
    List<ClienteArgea> findAllByDescrizioneIgnoreCaseAndIdNot(String descrizione,Integer id);

    /***
     * esempio di query HQL, i nome campo sono i nomi dei campi della classe entity (e non del db) e garantisce database independence
     * @param idMaggioreDi
     * @return
     */
    @Query("SELECT c FROM ClienteArgea c " +
            " WHERE c.descrizione ='pippo' and c.id>?1 " )
    List<ClienteArgea> findClientiCheSiChiamanoPippoConIdMaggioreDi(Integer idMaggioreDi);

    /***
     * esempio di query nativa, i nome campo sono i nomi dei campi del db e NON garantisce database independence
     * DA EVITARE
     * @param idMaggioreDi
     * @return
     */
   /* @Query(value = "SELECT * FROM ClienteArgea c " +
            " WHERE c.descrizione ='pippo' and c.id>?1 " ,nativeQuery = true)
    List<ClienteArgea> findClientiCheSiChiamanoPippoConIdMaggioreDiConQueryNativaDAEVITARE(Integer idMaggioreDi);
*/
}
