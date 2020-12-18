package org.bwbc.repositories;

import java.util.List;

import org.bwbc.beans.Film;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FilmRepository extends CrudRepository<Film, Long> {

	/** Recherche tous les films triés par ordre alphabétique
	 * @return List
	 */
	@Query("From Film f ORDER by f.nom")
	List<Film> findFilmsTriesParNom();

	/** Recherche tous les films triés par année ascendante
	 * @return
	 */
	@Query("From Film f ORDER BY f.annee")
	List<Film> findFilmsTriesParAnnee();

	/** Recherche un film à partir d'une partie de son nom
	 * @param nom nom du film
	 * @return List
	 */
	@Query("From Film f WHERE f.nom=:nom")
	List<Film> findFilmsParNom(String nom);

	/** Retourne tous les films parus l'année passée en paramètre et triés par nom
	 * @param annee année
	 * @return List
	 */
	@Query("From Film f WHERE f.annee=:annee")
	List<Film> findFilmsParAnnee(int annee);

	/** Retourne tous les films pour un genre donné
	 * @param genre genre recherché
	 * @return List
	 */
	@Query("From Film f JOIN f.genres g WHERE g.nom=:genre")
	List<Film> findFilmsParGenre(String genre);
	
	/** Retourne tous les films réalisés par un réalisateur donné entre 2 dates, triés par année
	 * @param identite identité du réalisateur
	 * @param minAnnee année min
	 * @param maxAnnee année max
	 * @return List
	 */
	@Query("From Film f JOIN f.realisateurs r WHERE r.identite=:identite and f.annee between :minAnnee and :maxAnnee ORDER BY f.annee")
	List<Film> findFilmsParRealisateurEtAnneesTriesParAnnee(String identite, int minAnnee, int maxAnnee);

	/** Recherche les films ayant un certain nombre de genres.
	 * @param nbGenres nombre de genres
	 * @return List
	 */
	@Query("From Film f WHERE size(f.genres)=:nbGenres")
	List<Film> findFilmsParNbGenres(int nbGenres);
	
	/** Recherche les films dans lesquels les 2 personnes passées en paramètre ont joué.
	 * Astuce: faire un CROSSJOIN: 
	 * - on met 2 fois la table Film dans la clause FROM avec 2 alias différents (f1 et f2)
	 * - on fait le chainage (avec des JOIN) de chaque Film (f1 et f2) jusqu'à acteur (a1 et a2)
	 * - puis on teste les identités de a1 et a1 
	 * - on ajoute également une condition sur f1 et f2 pour ne conserver que les films des 2 branches qui ont le même nom.
	 * - enfin, on récupère ensuite indifférement f1 ou f2 puisqu'on a vérifié f1.nom=f2.nom
	 * @param identite1 identité de la personne 1
	 * @param identite2 identité de la personne 2
	 * @return List
	 */
	@Query("SELECT f1 From Film f1, Film f2 JOIN f1.roles r1 JOIN r1.acteur a1 JOIN f2.roles r2 JOIN r2.acteur a2 WHERE a1.identite=:identite1 and a2.identite=:identite2 AND f1.nom=f2.nom")
	List<Film> findFilmsAvecPlusieursActeurs(String identite1, String identite2);

}