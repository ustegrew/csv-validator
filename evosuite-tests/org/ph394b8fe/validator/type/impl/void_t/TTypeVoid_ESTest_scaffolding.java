/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Sun Jul 01 20:37:39 CEST 2018
 */

package org.ph394b8fe.validator.type.impl.void_t;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;

@EvoSuiteClassExclude
public class TTypeVoid_ESTest_scaffolding {

  @org.junit.Rule 
  public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeClass 
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "org.ph394b8fe.validator.type.impl.void_t.TTypeVoid"; 
    org.evosuite.runtime.GuiSupport.initialize(); 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfIterationsPerLoop = 10000; 
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
  } 

  @Before 
  public void initTestCase(){ 
    threadStopper.storeCurrentThreads();
    threadStopper.startRecordingTime();
    org.evosuite.runtime.GuiSupport.setHeadless(); 
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
    org.evosuite.runtime.agent.InstrumentingAgent.activate(); 
  } 

  @After 
  public void doneWithTestCase(){ 
    threadStopper.killAndJoinClientThreads();
    org.evosuite.runtime.agent.InstrumentingAgent.deactivate(); 
    org.evosuite.runtime.GuiSupport.restoreHeadlessMode(); 
  } 


  private static void initializeClasses() {
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(TTypeVoid_ESTest_scaffolding.class.getClassLoader() ,
      "org.ph394b8fe.validator.result.TIssue",
      "org.ph394b8fe.validator.type.VType",
      "org.ph394b8fe.validator.type.impl.email_t.TTypeEMail",
      "org.ph394b8fe.validator.type.impl.date_t.TTypeDate",
      "org.ph394b8fe.validator.type.impl.int_t.TTypeInt",
      "org.ph394b8fe.validator.result.TResult",
      "org.ph394b8fe.validator.result.TIssue$EScope",
      "org.ph394b8fe.validator.type.impl.void_t.TTypeVoid",
      "org.ph394b8fe.validator.type.impl.regex_t.TTypeRx",
      "org.ph394b8fe.validator.result.TIssue$ESeverity"
    );
  } 
}
