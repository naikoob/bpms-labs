package org.acme.insurance.eligibility;

import static org.junit.Assert.assertEquals;

import org.acme.insurance.Driver;
import org.acme.insurance.Policy;
import org.acme.insurance.Vehicle;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class EligibilityRulesTest {

	static KieBase kbase;
	static KieSession ksession;
	static KieRuntimeLogger klogger;

	@BeforeClass
	public static void setupKsession() {
		try {
			// load up the knowledge base and create session
			ksession = readKnowledgeBase();
			System.out.println("setupKsession() ksession  = " + ksession);
			klogger = KieServices.Factory.get().getLoggers().newFileLogger(ksession,
					"src/test/java/org/acme/insurance/policyquote/eligibility");
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Test
	public void minAgeTest() {
		// now create some test data
		Driver driver = new Driver();
		driver.setAge(16);

		Vehicle vehicle = new Vehicle();
		
		Policy policy = new Policy();
		policy.setDriver(driver);
		policy.setVehicle(vehicle);
		
		// insert objects into working memory
		FactHandle policyFH = ksession.insert(policy);
		ksession.fireAllRules();
		ksession.delete(policyFH);

		System.out.println(policy);
		assertEquals(policy.getRejections().size(), 1);
	}

	@Test
	public void maxAgeTest() {
		// now create some test data
		Driver driver = new Driver();
		driver.setAge(99);
		Policy policy = new Policy();
		policy.setDriver(driver);
		Vehicle vehicle = new Vehicle();
		policy.setVehicle(vehicle);	
		// insert objects into working memory
		FactHandle policyFH = ksession.insert(policy);
		ksession.fireAllRules();
		ksession.delete(policyFH);

		System.out.println(policy);
		assertEquals(policy.getRejections().size(), 1);
	}

	@Test
	public void maxAccidentTest() {
		// now create some test data
		Driver driver = new Driver();
		driver.setNumberOfAccidents(6);
		Policy policy = new Policy();
		policy.setDriver(driver);
		Vehicle vehicle = new Vehicle();
		policy.setVehicle(vehicle);	
		// insert objects into working memory
		FactHandle policyFH = ksession.insert(policy);
		ksession.fireAllRules();
		ksession.delete(policyFH);

		System.out.println(policy);
		assertEquals(policy.getRejections().size(), 1);
	}

	@Test
	public void maxMileageTest() {
		// now create some test data
		Driver driver = new Driver();
		Policy policy = new Policy();
		policy.setDriver(driver);
		Vehicle vehicle = new Vehicle();
		vehicle.setYearlyMileage(170000);
		policy.setVehicle(vehicle);
		// insert objects into working memory
		FactHandle policyFH = ksession.insert(policy);
		ksession.fireAllRules();
		ksession.delete(policyFH);

		System.out.println(policy);
		assertEquals(policy.getRejections().size(), 1);
	}

	@AfterClass
	public static void closeKsession() {
		try {
			// closing resources
			klogger.close();
			ksession.dispose();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static KieSession readKnowledgeBase() throws Exception {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession();
		return kSession;
	}
}
