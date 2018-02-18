package com.robxx.droolscours;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;

import util.KnowledgeSessionHelper;
import util.OutputDisplay;

public class TestLession1 {
	
	StatelessKieSession sessionStateless = null;
	KieSession sessionStatefull = null;
	static KieContainer kieContainer;
	
	@BeforeClass
	public static void beforeClass() {
		kieContainer = KnowledgeSessionHelper.createRuleBase();
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("-----Before-----");
	}
	
	@After
	public void tearDown() throws Exception {
		System.out.println("-----After-----");
	}
	
	
	@Test
	public void testFirstOne() {
		sessionStatefull = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "ksession-rules");
		OutputDisplay outputDisplay = new OutputDisplay();
		sessionStatefull.setGlobal("showResults", outputDisplay);
		Account a = new Account();
		sessionStatefull.insert(a);
		sessionStatefull.fireAllRules();
	}
	
	@Test
	public void testRuleOneFactWithFactAndUsageOfGlobalAndCallBack() {
		sessionStatefull = KnowledgeSessionHelper.getStatefulKnowledgeSession(kieContainer, "ksession-rules");
		sessionStatefull.addEventListener(new RuleRuntimeEventListener() {
			
			@Override
			public void objectUpdated(ObjectUpdatedEvent event) {
				System.out.println("Object was updated \n" + "new Content \n" + event.getObject().toString());
			}
			
			@Override
			public void objectInserted(ObjectInsertedEvent event) {
				System.out.println("Object Inserted \n" + event.getObject().toString());
			}
			
			@Override
			public void objectDeleted(ObjectDeletedEvent event) {
				System.out.println("Object retracted \n" + event.getOldObject().toString());
			}
		});
		
		Account a = new Account();
		a.setAccountno(10);
		FactHandle handlea = sessionStatefull.insert(a);
		a.setBalance(12.0);
		sessionStatefull.update(handlea, a);
	//	sessionStatefull.fireAllRules();

		sessionStatefull.delete(handlea);
		sessionStatefull.fireAllRules();
		System.out.println("So you saw something ;)");
		
	}

}
