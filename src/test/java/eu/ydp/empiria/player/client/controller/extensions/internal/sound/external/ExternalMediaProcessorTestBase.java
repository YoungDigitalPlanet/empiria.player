package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import com.google.common.collect.ObjectArrays;
import eu.ydp.empiria.player.client.AbstractTestWithMocksBase;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.SingleMediaPlayback;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.MediaConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.MediaConnectorListener;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.executor.ExternalMediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.wrapper.ExternalMediaProxy;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.wrapper.ExternalMediaWrapper;
import eu.ydp.empiria.player.client.util.UniqueIdGenerator;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.gwtutil.client.timer.Timer;
import eu.ydp.gwtutil.client.timer.TimerAccessibleMock;
import eu.ydp.gwtutil.client.util.BrowserNativeInterface;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.junit.mock.UserAgentCheckerNativeInterfaceMock;

public abstract class ExternalMediaProcessorTestBase extends AbstractTestWithMocksBase {

    protected ExternalMediaProcessorTestContainer container = new ExternalMediaProcessorTestContainer();
    protected MediaConnector connector;
    protected MediaConnectorListener listener;
    protected EventsBus eventsBus;

    private Class<?>[] ignoredClasses = {ExternalMediaProcessor.class, EventsBus.class, ExternalMediaWrapper.class, ExternalMediaExecutor.class,
            ExternalMediaEngine.class, UniqueIdGenerator.class, MediaConnectorListener.class, ExternalMediaProxy.class, SingleMediaPlayback.class,
            ExternalMediaUpdateTimerEmulator.class, ExternalMediaUpdateTimerEmulatorState.class};

    @Override
    public void setUp() {
        doSetUp(ignoredClasses);
    }

    protected void setUpWithAccessibleTimer() {
        doSetUp(ObjectArrays.concat(Timer.class, ignoredClasses));

        TimerAccessibleMock.reset();
    }

    private void doSetUp(Class<?>... classes) {
        super.setUp(classes);

        BrowserNativeInterface nativeInterfaceMock = UserAgentCheckerNativeInterfaceMock
                .getNativeInterfaceMock(UserAgentCheckerNativeInterfaceMock.FIREFOX_WINDOWS);
        UserAgentChecker.setNativeInterface(nativeInterfaceMock);

        container.init(injector);

        connector = container.getConnector();
        listener = container.getMediaConnectorListener();
        eventsBus = container.getEventsBus();
    }

}
