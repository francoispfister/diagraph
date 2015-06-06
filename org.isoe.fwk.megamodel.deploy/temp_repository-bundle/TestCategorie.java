
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import ecmfa_2013.lang.catier2.AffectationRolePersonne;
import ecmfa_2013.lang.catier2.Catier2Factory;
import ecmfa_2013.lang.catier2.Catier2Package;
import ecmfa_2013.lang.catier2.Entreprise;
import ecmfa_2013.lang.catier2.Personne;
import ecmfa_2013.lang.catier2.Role;
import ecmfa_2013.lang.catier2.RoleHierarchie;

/**
 *
 * @author pfister
 *
 */
public class TestCategorie {

	private static final DateFormat DF = new SimpleDateFormat("dd/mm/yyyy");
	private static final Random RAND = new Random();
	private static List<String> prenoms;
	private static boolean presidentok = false;
	private static int card_;

	private static String logs = "";
	private static boolean validate = false;

	public static void main(String[] args) {
		int test = 12;
		boolean init = false; // en deux temps: 1 true - 2 false
		int cardi = 1;
		boolean trace = true;
		test_(trace, init, cardi, test);
	}

	public static EObject load(String path) {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(Catier2Package.eNS_URI,
				Catier2Package.eINSTANCE);
		URI uri = URI.createFileURI(new File(path).getAbsolutePath());
		return resourceSet.getResource(uri, true).getContents().get(0);
	}

	public static void save(String path, EObject toSave) {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(Catier2Package.eNS_URI,
				Catier2Package.eINSTANCE);
		URI uri = URI.createFileURI(new File(path).getAbsolutePath());
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(toSave);
		try {
			resource.save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Date parsed(String date) {
		try {
			return DF.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Role predecesseur(List<Role> roles, Role role) {
		int i = roles.indexOf(role);
		return i > 0 ? roles.get(i - 1) : null;
	}

	public static Role successeur(List<Role> roles, Role role) {
		int i = roles.indexOf(role);
		return (i > -1 && i < roles.size() - 1) ? roles.get(i + 1) : null;
	}

	public static Personne predecesseur(List<Personne> pers, Personne p) {
		int i = pers.indexOf(p);
		return i > 0 ? pers.get(i - 1) : null;
	}

	public static Personne successeur(List<Personne> pers, Personne p) {
		int i = pers.indexOf(p);
		return (i > -1 && i < pers.size() - 1) ? pers.get(i + 1) : null;
	}

	private static Personne predecesseurAffecte(Entreprise d,
			List<Personne> persons, Personne personne) {
		Personne pred = predecesseur(persons, personne);
		while (!estAffectatee(d, pred) && pred != null)
			pred = predecesseur(persons, pred);
		return pred;
	}

	private static Personne successeurAffecte(Entreprise d,
			List<Personne> persons, Personne personne) {
		Personne succ = successeur(persons, personne);
		while (!estAffectatee(d, succ) && succ != null)
			succ = successeur(persons, succ);
		return succ;
	}

	public static Personne creePersonne(String nom, String dateNaissance) {
		Personne p = Catier2Factory.eINSTANCE.createPersonne();
		p.setDateNaiss(parsed(dateNaissance));
		p.setNom(nom);
		return p;
	}

	public static String randomDate() {
		int year = 1950;
		return (RAND.nextInt(28) + 1) + "/" + (RAND.nextInt(12) + 1) + "/"
				+ (year + RAND.nextInt(50));
	}

	public static String randomNom1() {
		return getPrenoms1().get(RAND.nextInt(40));
	}

	public static Role getRandomRoleUnseulPresident(Entreprise d) {
		List<Role> roles = d.getRoles();
		Role result = roles.get(RAND.nextInt(4));
		if (result.getHierarchie() == RoleHierarchie.PRESIDENT) {
			if (presidentok)
				return null;
			presidentok = true;
		}
		return result;
	}

	private static Role getPresidentRole(Entreprise d) {
		List<Role> roles = d.getRoles();
		for (Role role : roles) {
			if (role.getHierarchie() == RoleHierarchie.PRESIDENT)
				return role;
		}
		return null;
	}

	public static Role getRandomRole(Entreprise d) {
		Role result = null;
		while (result == null)
			result = getRandomRoleUnseulPresident(d);
		return result;
	}

	public static String personneToString(Personne personne) {
		if (personne != null)
			return personne.getNom() + " - "
					+ DF.format(personne.getDateNaiss());
		else
			return "null";
	}

	public static String personneToDate(Personne personne) {
		return DF.format(personne.getDateNaiss());
	}

	static List<String> getPrenoms1() {
		if (prenoms == null) {
			prenoms = new ArrayList<String>();
			String[] filles = { "Emma", "Louise", "Marie", "Elise", "Léa",
					"Julie", "Charlotte", "Clara", "Manon", "Sarah", "Ella",
					"Lore", "Camille", "Laura", "Noor", "Lotte", "Lina",
					"Chloé", "Eva", "Juliette", };

			String[] gars = { "Noah", "Lucas", "Nathan", "Louis", "Arthur",
					"Mohamed", "Thomas", "Mathise", "Adam", "Liam", "Maxime",
					"Jules", "Hugo", "Victor", "Théo", "Tom", "Lars",
					"Gabriel", "Milan", "Matteo" };
			for (String fille : filles) {
				prenoms.add(fille);
			}
			for (String gar : gars) {
				prenoms.add(gar);
			}
		}
		return prenoms;
	}

	public static void test_(boolean trace, boolean init, int cardin, int testid) {
		String test = "test8";
		String domain = "catier2";
		String f = test + testid + "." + domain;
		card_ = cardin;
		if (init)
			init(f);
		else {
			Entreprise dd = read(f);
			ajoutePersonne(trace, dd, creePersonne("Elise", "24/06/1968"),
					RoleHierarchie.INTENDANT);
			ajoutePersonne(trace, dd, creePersonne("Thomas", "24/06/1969"),
					RoleHierarchie.INTENDANT);
			ajoutePersonne(trace, dd,
					creePersonne("Jean-Charles", "24/06/1972"),
					RoleHierarchie.INTENDANT);
			ajoutePersonne(trace, dd,
					creePersonne("Jean-Jacques", "24/06/1976"),
					RoleHierarchie.CAISSIER);
			ajoutePersonne(trace, dd, creePersonne("Jules", "25/08/1977"),
					RoleHierarchie.CAISSIER);
			ajoutePersonne(trace, dd, creePersonne("Clara", "11/10/1969"),
					RoleHierarchie.SECRETAIRE);
			ajoutePersonne(trace, dd, creePersonne("Lars", "24/09/1970"),
					RoleHierarchie.SECRETAIRE);
			ajoutePersonne(trace, dd, creePersonne("Emilie", "24/06/1972"),
					RoleHierarchie.SECRETAIRE);
			logAffectations(dd);
			System.out.println(logs);
			save(test + (testid + 1) + "." + domain, dd);
		}
	}

	public static void testAlea(boolean trace, boolean init, int cardin,
			int testid) {
		String test = "test";
		String f = test + testid + ".cat5";
		card_ = cardin;
		if (init)
			init(f);
		else {
			Entreprise dd = read(f);
			ajoutePersonneAlea(trace, dd,
					creePersonne("Jean-Jacques", "24/06/1980"));// intendant à
																// Jean-Jacques
			ajoutePersonneAlea(trace, dd,
					creePersonne("Jean-Charles", "11/10/1957"));// affectation:
																// president à
																// Jean-Charles
																// - 11/10/1957
			ajoutePersonneAlea(trace, dd,
					creePersonne("Jean-Pierre", "24/09/1963"));
			ajoutePersonneAlea(trace, dd,
					creePersonne("Jean-Paul", "24/06/1967"));
			logAffectations(dd);
			System.out.println(logs);
			save(test + (testid + 1) + ".cat5", dd);
		}
	}

	private static void ajoutePersonne(boolean trace, Entreprise entreprise,
			Personne personne, RoleHierarchie hierarchie) {
		if (trace)
			ajoutePersonneTrace(entreprise, personne, hierarchie);
		else
			ajoutePersonneSansTrace(entreprise, personne, hierarchie);
	}

	private static void ajoutePersonneAlea(boolean trace,
			Entreprise entreprise, Personne personne) {
		if (trace)
			ajoutePersonneTraceAlea(entreprise, personne);
		else
			ajoutePersonneSansTraceAlea(entreprise, personne);
	}

	public static List<Personne> getPersonnes(Entreprise d) {
		List<Personne> persons = new ArrayList<Personne>();
		for (int i = 0; i < card_; i++) {
			// String nom = pren == 1 ? randomNom1() : randomNom2_();
			String nom = randomNom1();
			persons.add(creePersonne(nom, randomDate()));// 24/06/1980
		}
		Collections.sort(persons, new Comparator<Personne>() {
			public int compare(Personne p1, Personne p2) {
				long result = p1.getDateNaiss().getTime()
						- p2.getDateNaiss().getTime();
				if (result == 0)
					return 0;
				else if (result > 0)
					return 1;
				else
					return -1;
			}
		});

		if (!persons.isEmpty()) {
			for (Personne personne : persons) {
				Personne prec_ = predecesseur(persons, personne);
				if (prec_ != null
						&& (prec_.getDateNaiss().getTime() >= personne
								.getDateNaiss().getTime()))
					return null;

				Personne suiv = successeur(persons, personne);
				if (suiv != null
						&& (suiv.getDateNaiss().getTime() <= personne
								.getDateNaiss().getTime()))
					return null;

			}

			d.getPersonnes().addAll(persons);
		}

		return d.getPersonnes();
	}

	public static AffectationRolePersonne ajouteRelation(Entreprise dd,
			Personne personne, Role role) {
		AffectationRolePersonne affectation = Catier2Factory.eINSTANCE
				.createAffectationRolePersonne();
		affectation.setPersonne(personne);
		affectation.setRole(role);
		dd.getAffectations().add(affectation);
		System.out.println("affectation: "
				+ roleToString(affectation.getRole()) + " à "
				+ personneToString(affectation.getPersonne()));
		return affectation;
	}

	public static void init(String path) {// 20,"test3.cat5"
		Entreprise dd = Catier2Factory.eINSTANCE.createEntreprise();
		List<Personne> personnes = null;
		while (personnes == null)
			personnes = getPersonnes(dd);
		for (Personne personne : personnes) {

			System.out.println(personneToString(personne));
			Personne prec = predecesseur(personnes, personne);

			System.out.println(personneToString(predecesseur(personnes,
					personne))
					+ " < "
					+ personneToString(personne)
					+ " < "
					+ personneToString(successeur(personnes, personne)));
		}

		List<Role> roles_ = getRoles_(dd);

		for (Role role : roles_) {
			// if (role.getHierarchie() == RoleHierarchie.PRESIDENT)
			// tb = true;
			Role pr = predecesseur(roles_, role);
			Role sv = successeur(roles_, role);
			System.out.println(roleToString(pr) + " < " + roleToString(role)
					+ " < " + roleToString(sv));
		}

		AffectationRolePersonne presidaffectation = ajouteRelation(dd, dd
				.getPersonnes().get(0), getPresidentRole(dd));
		Role prol = succ(roles_, presidaffectation.getRole());

		int count = 0;
		int mo = card_ / 4; // card hierarch=4
		for (Personne personne_ : personnes) {
			if (personne_ != presidaffectation.getPersonne()) {
				count++;
				if (count == mo + 1) {
					prol = succ(roles_, prol);
					count = 0;
				}
				if (!existAffectation(dd, personne_, prol))
					ajouteRelation(dd, personne_, prol);
			}
		}

		System.out.println("--------------------------------");
		List<AffectationRolePersonne> affectations = dd.getAffectations();
		for (AffectationRolePersonne affectation : affectations) {
			System.out.println(affectation.getPersonne().getNom() + " - "
					+ DF.format(affectation.getPersonne().getDateNaiss())
					+ " role: "
					+ affectation.getRole().getHierarchie().getLiteral());
		}
		save(path, dd);
	}

	private static Role succ(List<Role> roles, Role role) {
		Role suc = successeur(roles, role);
		return suc == null ? role : suc;
	}

	public static List<Role> rolesPossibles_(Entreprise dd, Personne personne) {
	
		List<Personne> sortd = new ArrayList<Personne>();

		sortd.addAll(dd.getPersonnes());
		Collections.sort(sortd, new Comparator<Personne>() {
			public int compare(Personne p1, Personne p2) {
				long result = p1.getDateNaiss().getTime()
						- p2.getDateNaiss().getTime();
				if (result == 0)
					return 0;
				else if (result > 0)
					return 1;
				else
					return -1;
			}
		});

		Personne preced_ = predecesseurAffecte(dd, sortd, personne);
		if (preced_ == null)
			throw new RuntimeException("erreur: " + personne.getNom()
					+ " est plus vieux que le président ...");

		Role rprec_ = trouveRole(dd, preced_);
		if (rprec_ == null)
			System.out.println("pp###### !!");

		if (rprec_.getHierarchie() == RoleHierarchie.PRESIDENT) // un seul
																// président
			rprec_ = successeur(dd.getRoles(), rprec_);

		Personne suiv = successeurAffecte(dd, sortd, personne);
		if (suiv == null)
			suiv = preced_;
		Role rsuiv = trouveRole(dd, suiv);

		if (rsuiv == null)
			System.out.println("ss###### !!");

		String log = rprec_.getHierarchie().getLiteral() + " - "
				+ rsuiv.getHierarchie().getLiteral();
		System.out.println(log);

		List<Role> rolesPossibles = intervalle(dd, rprec_, rsuiv);
		System.out.println("siz=" + rolesPossibles.size());
		System.out.println("roles possibles");
		if (rolesPossibles.size() > 0) {
			for (Role role : rolesPossibles) {
				System.out.println(roleToString(role));
			}
		}
		return rolesPossibles;
	}

	public static AffectationRolePersonne affecteRoleAPersonne(
			Entreprise domaine, Personne personne, RoleHierarchie hierarchie) {
		// if (personne.getNom().equals("Jean-Charles"))
		// tb = true;
		System.out.println("affectation " + hierarchie.getLiteral() + " à "
				+ personneToString(personne));
		AffectationRolePersonne resultat = null;
		List<Role> roles = rolesPossibles_(domaine, personne);
		for (Role role : roles) {
			RoleHierarchie rh = role.getHierarchie();
			if (rh == hierarchie) {
				resultat = ajouteRelation(domaine, personne, role);
				System.out.println("ok");
				break;
			}
		}
		if (resultat == null) {
			if (!validate)
				for (Role role : domaine.getRoles()) {
					if (role.getHierarchie() == hierarchie) {
						resultat = ajouteRelation(domaine, personne, role);
						System.out.println("ok");
						break;
					}
				}
			else
				System.out.println("arp - impossible d'affecter le role "
						+ hierarchie.getLiteral() + " à "
						+ personneToString(personne));
		}
		return resultat;
	}

	public static AffectationRolePersonne affecteRoleAPersonneAlea(
			Entreprise domaine, Personne personne) {
	
		System.out.println("affectation ?? " + " à "
				+ personneToString(personne));
		AffectationRolePersonne resultat = null;
		List<Role> roles = rolesPossibles_(domaine, personne);
		Role role = null;
		if (!roles.isEmpty()) {
			role = roles.get(RAND.nextInt(roles.size()));
			resultat = ajouteRelation(domaine, personne, role);
		}
		if (role == null)
			System.out.println("arp - pas de role role pour"
					+ personneToString(personne));
		if (resultat == null && role != null)
			System.out.println("arp - impossible d'affecter le role "
					+ role.getHierarchie().getLiteral() + " à "
					+ personneToString(personne));
		return resultat;
	}

	public static AffectationRolePersonne ajoutePersonneSansTrace(
			Entreprise domaine, Personne personne, RoleHierarchie hierarchie) {
		domaine.getPersonnes().add(personne);
		AffectationRolePersonne resultat = affecteRoleAPersonne(domaine,
				personne, hierarchie);
		if (resultat == null)
			logs += "impossible d'affecter le role " + hierarchie.getLiteral()
					+ " à " + personneToString(personne) + "\n";
		return resultat;
	}

	public static AffectationRolePersonne ajoutePersonneSansTraceAlea(
			Entreprise domaine, Personne personne) {
		domaine.getPersonnes().add(personne);
		AffectationRolePersonne resultat = affecteRoleAPersonneAlea(domaine,
				personne);
		if (resultat == null)
			logs += "impossible d'affecter le role ?? " + " à "
					+ personneToString(personne) + "\n";
		return resultat;
	}

	public static AffectationRolePersonne ajoutePersonneTraceAlea(
			Entreprise entreprise, Personne personne) {
		AffectationRolePersonne resultat = null;
		entreprise.getPersonnes().add(personne);
		List<Personne> tr = new ArrayList<Personne>();
		tr.addAll(entreprise.getPersonnes());
		Collections.sort(tr, new Comparator<Personne>() {
			public int compare(Personne p1, Personne p2) {
				long result = p1.getDateNaiss().getTime()
						- p2.getDateNaiss().getTime();
				if (result == 0)
					return 0;
				else if (result > 0)
					return 1;
				else
					return -1;
			}
		});
		System.out.println("(ajouté)" + personneToString(personne));
		System.out.println("(trié)");
		for (Personne p : tr) {
			if (p == personne)
				System.out.println("[-");
			System.out.println(personneToString(predecesseurAffecte(entreprise,
					tr, p))
					+ " < "
					+ personneToString(p)
					+ " < "
					+ personneToString(successeurAffecte(entreprise, tr, p)));
			if (p == personne) {
				resultat = affecteRoleAPersonneAlea(entreprise, personne);

			}
			if (p == personne)
				System.out.println("-]");
		}
		if (resultat == null)
			logs += "impossible d'affecter le role ?? " + " à "
					+ personneToString(personne) + "\n";
		return resultat;
	}

	public static AffectationRolePersonne ajoutePersonneTrace(
			Entreprise entreprise, Personne personne, RoleHierarchie hierarchie_) {
		AffectationRolePersonne resultat = null;
		entreprise.getPersonnes().add(personne);
		List<Personne> tr = new ArrayList<Personne>();
		tr.addAll(entreprise.getPersonnes());
		Collections.sort(tr, new Comparator<Personne>() {
			public int compare(Personne p1, Personne p2) {
				long result = p1.getDateNaiss().getTime()
						- p2.getDateNaiss().getTime();
				if (result == 0)
					return 0;
				else if (result > 0)
					return 1;
				else
					return -1;
			}
		});
		System.out.println("(ajouté)" + personneToString(personne));
		System.out.println("(trié)");
		for (Personne p : tr) {
			if (p == personne)
				System.out.println("[-");
			System.out.println(personneToString(predecesseurAffecte(entreprise,
					tr, p))
					+ " < "
					+ personneToString(p)
					+ " < "
					+ personneToString(successeurAffecte(entreprise, tr, p)));
			if (p == personne) {

				resultat = affecteRoleAPersonne(entreprise, personne,
						hierarchie_);
			}
			if (p == personne)
				System.out.println("-]");
		}
		if (resultat == null)
			logs += "impossible d'affecter le role " + hierarchie_.getLiteral()
					+ " à " + personneToString(personne) + "\n";
		return resultat;
	}

	static void logAffectations(Entreprise dd) {
		System.out.println("--------------------------------");
		List<AffectationRolePersonne> affectations = dd.getAffectations();
		for (AffectationRolePersonne affectation : affectations) {
			System.out.println(affectation.getPersonne().getNom() + " - "
					+ DF.format(affectation.getPersonne().getDateNaiss())
					+ " role: "
					+ affectation.getRole().getHierarchie().getLiteral());
		}
	}

	private static List<Role> intervalle(Entreprise d, Role premier,
			Role dernier) {
		List<Role> result = new ArrayList<Role>();
		if (premier == dernier) {
			result.add(premier);
			return result;
		}
		for (Role role : d.getRoles()) {
			System.out.println(roleToValue(premier) + " - " + roleToValue(role)
					+ " - " + roleToValue(dernier));
			if (role.getHierarchie().getValue() >= premier.getHierarchie()
					.getValue())
				if (role.getHierarchie().getValue() <= dernier.getHierarchie()
						.getValue())
					if (!result.contains(role))
						result.add(role);
		}
		return result;
	}

	private static Role trouveRole(Entreprise dd, Personne pr) {
		// dd.getAffectations();
		List<AffectationRolePersonne> affectations = dd.getAffectations();
		for (AffectationRolePersonne affectation : affectations)
			if (affectation.getPersonne() == pr)
				return affectation.getRole();
		return null;
	}

	public static Entreprise read(String path) {

		Entreprise dd = (Entreprise) load(path);
		List<Personne> persons = dd.getPersonnes();
		for (Personne personne : persons) {
			System.out.println(personne.getNom() + " - "
					+ DF.format(personne.getDateNaiss()));
		}
		List<Role> rolez = dd.getRoles();
		for (Role role : rolez) {
			System.out.println(role.getHierarchie().getLiteral());
		}

		System.out.println("--------------------------------");
		List<AffectationRolePersonne> affectations = dd.getAffectations();
		for (AffectationRolePersonne affectation : affectations) {
			System.out.println(affectation.getPersonne().getNom() + " - "
					+ DF.format(affectation.getPersonne().getDateNaiss())
					+ " role: "
					+ affectation.getRole().getHierarchie().getLiteral());
		}

		return dd;

	}

	private static boolean existAffectation(Entreprise d, Personne pers,
			Role rol) {
		List<AffectationRolePersonne> rs = d.getAffectations();
		for (AffectationRolePersonne affectationRolePersonne : rs) {
			if (affectationRolePersonne.getPersonne() == pers
					&& affectationRolePersonne.getRole() == rol)
				return true;
		}
		return false;
	}

	private static boolean estAffectatee(Entreprise d, Personne pers) {
		List<AffectationRolePersonne> rs = d.getAffectations();
		for (AffectationRolePersonne affectationRolePersonne : rs) {
			if (affectationRolePersonne.getPersonne() == pers)
				return true;
		}
		return false;
	}

	private static String roleToString(Role role) {
		if (role == null)
			return "null";
		else
			return role.getHierarchie().getLiteral();
	}

	private static int roleToValue(Role role) {
		if (role == null)
			return -1;
		else
			return role.getHierarchie().getValue();
	}

	private static List<Role> getRoles_(Entreprise d) {
		List<Role> roles = new ArrayList<Role>();
		Role president = null;
		Role intendant = null;
		Role secretaire = null;
		Role caissier = null;

		president = creeRole(null, RoleHierarchie.PRESIDENT);
		roles.add(president);

		intendant = creeRole(null, RoleHierarchie.INTENDANT);
		roles.add(intendant);
		intendant.setParent(president);

		secretaire = creeRole(null, RoleHierarchie.SECRETAIRE);
		roles.add(secretaire);
		secretaire.setParent(president);

		// if (!existeRole(d, RoleHierarchie.CAISSIER)){
		caissier = creeRole(null, RoleHierarchie.CAISSIER);
		roles.add(caissier);
		caissier.setParent(intendant);

		if (!roles.isEmpty()) {
			Collections.sort(roles, new Comparator<Role>() {
				public int compare(Role r1, Role r2) {
					long result = r1.getHierarchie().getValue()
							- r2.getHierarchie().getValue();
					if (result == 0)
						return 0;
					else if (result > 0)
						return 1;
					else
						return -1;
				}
			});
			d.getRoles().addAll(roles);
		}
		return d.getRoles();
	}

	private static boolean existeRole(Entreprise d, RoleHierarchie r) {
		for (Role role : d.getRoles())
			if (role.getHierarchie() == r)
				return true;
		return false;
	}

	private static long compare(Personne p1, Personne p2) {
		return p1.getDateNaiss().getTime() - p2.getDateNaiss().getTime();
	}

	private static Role creeRole(Entreprise dd_, RoleHierarchie rh) {
		Role r = Catier2Factory.eINSTANCE.createRole();
		r.setHierarchie(rh);
		if (dd_ != null)
			dd_.getRoles().add(r);
		return r;
	}

} // Cat5Example
