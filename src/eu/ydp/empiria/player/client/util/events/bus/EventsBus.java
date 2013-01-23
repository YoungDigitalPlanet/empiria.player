package eu.ydp.empiria.player.client.util.events.bus;

import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.util.events.Event;
import eu.ydp.empiria.player.client.util.events.Event.Type;
import eu.ydp.empiria.player.client.util.events.EventHandler;
import eu.ydp.empiria.player.client.util.events.scope.EventScope;

/**
 * Wewnetrzna szyna zdarzen playera
 *
 */
public interface EventsBus {

	/**
	 * Dodaje handlera do typu zdarzenia type. Handler rejestrowany jest jako
	 * handler globalny
	 * {@link EventsBus#addAsyncHandler(Type, EventHandler, EventScope)}
	 *
	 * @param type
	 *            typ eventu
	 * @param handler
	 *            handler
	 * @return
	 */
	public abstract <H extends EventHandler, T extends Enum<T>> HandlerRegistration addHandler(Type<H, T> type, H handler);

	public abstract <H extends EventHandler, T extends Enum<T>> HandlerRegistration[] addHandler(Type<H, T>[] type, H handler);
	/**
	 * Dodaje handlera do typu zdarzenia type. Handler jest uruchamiany jedynie
	 * w obrebie wskazanym przez eventScope. Aby zarejestrowac handler globalny
	 * parametr eventScope powinien miec wartosc null lub nalezy uzyc
	 * {@link EventsBus#addHandler(Type, EventHandler)}
	 *
	 * @param type
	 *            typ zdarzenia
	 * @param handler
	 * @param eventScope
	 *            zakres widocznosci handlera
	 * @return
	 */
	public abstract <H extends EventHandler, T extends Enum<T>> HandlerRegistration addHandler(Type<H, T> type, H handler, EventScope<?> eventScope);
	public abstract <H extends EventHandler, T extends Enum<T>> HandlerRegistration[] addHandler(Type<H, T>[] type, H handler, EventScope<?> eventScope);
	/**
	 * Dodaje handlera dla zdarzen typu type dla ktorych producenetem jest
	 * objekt source.
	 *
	 * @param type
	 *            typ zdarzenia
	 * @param source
	 *            obiekt producenta
	 * @param handler
	 * @return
	 */
	public abstract <H extends EventHandler, T extends Enum<T>> HandlerRegistration addHandlerToSource(Type<H, T> type, Object source, H handler);
	public abstract <H extends EventHandler, T extends Enum<T>> HandlerRegistration[] addHandlerToSource(Type<H, T>[] type, Object source, H handler);

	/**
	 * Dodaje handlera dla zdarzen typu type dla ktorych producenetem jest
	 * objekt source.
	 *
	 * @param type
	 *            typ zdarzenia
	 * @param source
	 *            obiekt producenta
	 * @param handler
	 * @param eventScope
	 *            zakres widocznosci handlera
	 * @return
	 */

	public abstract <H extends EventHandler, T extends Enum<T>> HandlerRegistration addHandlerToSource(Type<H, T> type, Object source, H handler, EventScope<?> eventScope);

	/**
	 * Dodaje handlera dla zdarzen typu type. Handlery sa wykonywane
	 * asynchronicznie. Handler rejestrowany jest jako handler globalny
	 *
	 * @param type
	 *            typ zdarzenia
	 * @param handler
	 * @return
	 */
	public abstract <H extends EventHandler, T extends Enum<T>> HandlerRegistration addAsyncHandler(Type<H, T> type, H handler);

	/**
	 * Dodaje handlera dla zdarzen typu type. Handler jest uruchamiany jedynie w
	 * obrebie wskazanym przez eventScope. Aby zarejestrowac handler globalny
	 * parametr eventScope powinien miec wartosc null lub nalezy uzyc
	 * {@link EventsBus#addHandler(Type, EventHandler)}. Handlery sa wykonywane
	 * asynchronicznie.
	 *
	 * @param type
	 *            typ zdarzenia
	 * @param handler
	  * @param eventScope
	 *            zakres widocznosci handlera
	 * @return
	 */
	public abstract <H extends EventHandler, T extends Enum<T>> HandlerRegistration addAsyncHandler(Type<H, T> type, H handler, EventScope<?> eventScope);

	/**
	 * Dodaje handlera dla zdarzen typu type dla ktorych producenetem jest
	 * objekt source. Handlery rejestrowane sa jako globalne. Handlery sa wykonywane asynchronicznie.
	 *
	 * @param type
	 *            typ zdarzenia
	 * @param source
	 *            obiekt producenta
	 * @param handler
	 * @return
	 */
	public abstract <H extends EventHandler, T extends Enum<T>> HandlerRegistration addAsyncHandlerToSource(Type<H, T> type, Object source, H handler);
	/**
	 * Dodaje handlera dla zdarzen typu type dla ktorych producenetem jest
	 * objekt source. Handler jest uruchamiany jedynie w
	 * obrebie wskazanym przez eventScope. Aby zarejestrowac handler globalny
	 * parametr eventScope powinien miec wartosc null lub nalezy uzyc
	 * {@link EventsBus#addAsyncHandlerToSource(Type, Object, EventHandler)} Handlery sa wykonywane asynchronicznie.
	 *
	 * @param type
	 *            typ zdarzenia
	 * @param source
	 *            obiekt producenta
	 * @param handler
	  * @param eventScope
	 *            zakres widocznosci handlera
	 * @return
	 */
	public abstract <H extends EventHandler, T extends Enum<T>> HandlerRegistration addAsyncHandlerToSource(Type<H, T> type, Object source, H handler, EventScope<?> eventScope);

	/**
	 * wyzwala zdarzenie. Zostaj� uruchomione handlery globalne lub
	 * zarejestrowwane na zakres Page
	 */
	public abstract <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEvent(E event);

	/**
	 * wyzwala zdarzenie. Zostaj� uruchomione handlery zarejestrowwane na zakres
	 * eventScope
	 *
	 * @param event
	 *            typ zdarzenia
	 * @param eventScope
	 *            zakres widocznosci
	 */
	public abstract <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEvent(E event, EventScope<?> eventScope);

	/**
	 * Wyzwala zdarzenie. Zostaj� uruchomione handlery globalne lub
	 * zarejestrowwane na zakres Page
	 *
	 * @param event
	 */
	public abstract <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEventWithCallback(E event);

	/**
	 * Wyzwala zdarzenie. Zostaj� uruchomione handlery zarejestrowwane na zakres
	 * eventScope
	 *
	 * @param event
	 * @param eventScope
	 */
	public abstract <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEventWithCallback(E event, EventScope<?> eventScope);

	/**
	 * Wyzwala zdarzenie. Zostaj� uruchomione handlery globalne lub
	 * zarejestrowwane na zakres Page kt�re zostaly zarejestrowane dla
	 * producenta source
	 *
	 * @param event
	 * @param source
	 */
	public abstract <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEventFromSource(E event, Object source);

	/**
	 * Wyzwala zdarzenie. Zostaj� uruchomione handlery zarejestrowwane na zakres
	 * eventScope, kt�re zostaly zarejestrowane dla producenta source
	 *
	 * @param event
	 * @param source
	 * @param eventScope
	 */
	public abstract <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireEventFromSource(E event, Object source, EventScope<?> eventScope);

	/**
	 * wyzwala zdarzenie. Zostaj� uruchomione handlery globalne lub
	 * zarejestrowwane na zakres page.
	 */
	public abstract <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireAsyncEvent(E event);

	/**
	 * Wyzwala zdarzenie. Zostaj� uruchomione handlery zarejestrowwane na zakres
	 * eventScope
	 *
	 * @param event
	 * @param eventScope
	 */
	public abstract <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireAsyncEvent(E event, EventScope<?> eventScope);

	/**
	 * wyzwala zdarzenie. Zostaj� uruchomione handlery globalne lub
	 * zarejestrowwane na zakres page
	 *
	 * @param event
	 * @param source
	 */
	public abstract <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireAsyncEventFromSource(E event, Object source);

	/**
	 * Wyzwala zdarzenie. Zostaj� uruchomione handlery zarejestrowwane na zakres
	 * eventScope, kt�re zostaly zarejestrowane dla producenta source
	 *
	 * @param event
	 * @param source
	 * @param eventScope
	 */
	public abstract <H extends EventHandler, T extends Enum<T>, E extends Event<H, T>> void fireAsyncEventFromSource(E event, Object source, EventScope<?> eventScope);

}