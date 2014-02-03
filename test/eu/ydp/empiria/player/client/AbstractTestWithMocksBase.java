package eu.ydp.empiria.player.client;

import eu.ydp.gwtutil.test.AbstractMockingTestBase;

public abstract class AbstractTestWithMocksBase extends AbstractMockingTestBase<TestWithMocksGuiceModule> {

	public AbstractTestWithMocksBase() {
		super(TestWithMocksGuiceModule.class);
	}

}
