package org.bwbc;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.bwbc.beans.Acteur;
import org.bwbc.repositories.ActeurRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ActeurRepositoryTest {

	
	@Autowired
	private ActeurRepository acteurRepository;

	@Test
	public void testExtraireActeursTriesParIdentite() {
		List<Acteur> acteurs = acteurRepository.findActeursTriesParIdentite();
		assertEquals(1137, acteurs.size());
	}
	
	@Test
	public void testExtraireActeursParIdentite() {
		List<Acteur> acteurs = acteurRepository.findActeursParIdentite("Marion Cotillard");
		assertEquals(1, acteurs.size());
		assertEquals("Marion Cotillard", acteurs.get(0).getIdentite());
	}
	
	@Test
	public void testExtraireActeursParAnneeNaissance() {
		List<Acteur> acteurs = acteurRepository.findActeursParAnneeNaissance(1985);
		assertEquals(10, acteurs.size());
	}
	

	@Test
	public void testExtraireActeursParRole() {
		List<Acteur> acteurs = acteurRepository.findActeursParRole("Harley Quinn");
		assertEquals(2, acteurs.size());
		assertEquals("Margot Robbie", acteurs.get(0).getIdentite());
		assertEquals("Margot Robbie", acteurs.get(1).getIdentite());
	}
	
	@Test
	public void testExtraireActeursParFilmParuAnnee() {
		List<Acteur> acteurs = acteurRepository.findActeursParFilmParuAnnee(2015);
		assertEquals(140, acteurs.size());
	}
	
	@Test
	public void testExtraireActeursParPays() {
		List<Acteur> acteurs = acteurRepository.findActeursParPays("France");
		assertEquals(158, acteurs.size());
	}
	
	@Test
	public void testExtraireActeursParListePaysEtAnnee() {
		List<Acteur> acteurs = acteurRepository.findActeursParListePaysEtAnnee(Arrays.asList("France"), 2017);
		assertEquals(24, acteurs.size());
	}
	
	@Test
	public void testExtraireParRealisateurEntreAnnee() {
		List<Acteur> acteurs = acteurRepository.findActeursParRealisateurEntreAnnees("Ridley Scott", 2010, 2020);
		assertEquals(27, acteurs.size());
	}
}
